package dev.robocode.tankroyale.bridge.conformance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Runs one robot on one engine by driving the compatibility harness, and parses what comes
 * back.
 *
 * The beds deliberately do not stage battles themselves. Staging the bridge side means
 * running the robots-wrapper, patching the generated boot script so the bot's output
 * reaches a file, and duplicating the bot directory per participant so two instances
 * cannot interleave into one log. That already exists in the harness, and a second
 * implementation of it here is the duplication most likely to drift out of agreement --
 * at which point the two tiers would be measuring subtly different things while appearing
 * to agree.
 */
final class ConformanceHarness {

    private static final Path REPO_ROOT = locateRepoRoot();
    private static final Path HARNESS = REPO_ROOT.resolve("compat-test").resolve("compat_test.py");

    private final String python;
    private final Path robocodeHome;
    private final Path testRobotClasses;
    private final int rounds;

    private ConformanceHarness(String python, Path robocodeHome, Path testRobotClasses, int rounds) {
        this.python = python;
        this.robocodeHome = robocodeHome;
        this.testRobotClasses = testRobotClasses;
        this.rounds = rounds;
    }

    /** Resolves the environment, or returns null when this machine cannot run the tier. */
    static ConformanceHarness resolveOrNull() {
        if (!Files.isRegularFile(HARNESS)) {
            return null;
        }
        Path home = existingDirectory(System.getProperty("robocode.home", "C:/robocode"));
        Path source = existingDirectory(System.getProperty("robocode.source", "C:/Code/robocode"));
        if (home == null || source == null) {
            return null;
        }
        Path classes = source.resolve("robocode.tests.robots/build/classes/java/main");
        if (!Files.isDirectory(classes)) {
            return null;
        }
        String python = System.getProperty("conformance.python", "python");
        // Five rather than one. Tank Royale has no seed (AN-002), so a battle starts from a
        // random placement and an expectation about an event that needs a particular
        // situation -- hitting a wall, being scanned -- may simply not occur in one round.
        // Measured: at one round the marker is sometimes absent; at five it has always
        // appeared. This is the cost of the missing seed, paid in wall time.
        int rounds = Integer.getInteger("conformance.rounds", 5);
        return new ConformanceHarness(python, home, classes, rounds);
    }

    /** Why the tier cannot run here, for a skip message that tells the reader what to fix. */
    static String missingEnvironment() {
        if (!Files.isRegularFile(HARNESS)) {
            return "the compatibility harness was not found at " + HARNESS;
        }
        if (existingDirectory(System.getProperty("robocode.home", "C:/robocode")) == null) {
            return "no classic Robocode installation; set -Probocode.home=<install>";
        }
        Path source = existingDirectory(System.getProperty("robocode.source", "C:/Code/robocode"));
        if (source == null) {
            return "no classic Robocode source repository; set -Probocode.source=<checkout>";
        }
        return "classic's test robots are not built; run `gradlew :robocode.tests.robots:build` in "
                + source;
    }

    /**
     * Runs one of classic's test robots on one engine.
     *
     * @param robotClass fully qualified, e.g. {@code tested.robots.InteruptibleEvent}
     */
    BattleOutcome run(Engine engine, String robotClass) {
        List<String> command = new ArrayList<>(List.of(
                python,
                HARNESS.toString(),
                "--conformance", testRobotClasses.toString(),
                "--robot-class", robotClass,
                "--engine", engine.harnessName(),
                "--rounds", String.valueOf(rounds),
                "--robocode-home", robocodeHome.toString()));

        try {
            Process process = new ProcessBuilder(command)
                    .directory(HARNESS.getParent().toFile())
                    .redirectErrorStream(false)
                    .start();

            // Both pipes are drained concurrently, and the wait comes before either is
            // read. Draining one to EOF first deadlocks as soon as the harness writes more
            // to the other than the OS pipe buffer holds -- a bot's stack trace is enough --
            // and a wait placed after the reads can never bound a hang it is already stuck
            // behind.
            AtomicReference<String> out = new AtomicReference<>("");
            AtomicReference<String> err = new AtomicReference<>("");
            Thread pumpOut = pump(process.getInputStream(), out);
            Thread pumpErr = pump(process.getErrorStream(), err);

            if (!process.waitFor(20, TimeUnit.MINUTES)) {
                process.destroyForcibly();
                join(pumpOut);
                join(pumpErr);
                return failed("the harness did not finish within 20 minutes. stderr: "
                        + trim(err.get()));
            }
            join(pumpOut);
            join(pumpErr);
            String stdout = out.get();
            String stderr = err.get();
            String json = lastJsonLine(stdout);
            if (json == null) {
                return failed("the harness printed no result. stderr: " + trim(stderr));
            }
            return parse(json);
        } catch (IOException e) {
            return failed("could not start the harness: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return failed("interrupted while waiting for the harness");
        }
    }

    private static BattleOutcome failed(String detail) {
        return new BattleOutcome(false, List.of(), List.of(), null, detail);
    }

    /**
     * The harness prints its result as a single JSON object on the last non-blank line;
     * anything before it is progress output.
     */
    private static String lastJsonLine(String stdout) {
        String[] lines = stdout.split("\\R");
        for (int i = lines.length - 1; i >= 0; i--) {
            String line = lines[i].trim();
            if (line.startsWith("{") && line.endsWith("}")) {
                return line;
            }
        }
        return null;
    }

    /**
     * Reads the handful of fields the beds care about.
     *
     * A dependency-free reader rather than a JSON library: the shape is fixed, it is
     * produced by a script in this same repository, and the tier is meant to add no
     * dependency that could collide with an engine's own classpath.
     */
    private static BattleOutcome parse(String json) {
        boolean completed = Boolean.parseBoolean(Json.scalar(json, "ok"));
        String scoreText = Json.scalar(json, "score");
        Double score = null;
        try {
            score = (scoreText == null || "null".equals(scoreText)) ? null : Double.valueOf(scoreText);
        } catch (NumberFormatException ignored) {
            // Leave the score absent; the assertion the test makes is about the console.
        }
        return new BattleOutcome(completed,
                Json.stringArray(json, "consoles"),
                Json.stringArray(json, "errors"),
                score,
                Json.scalar(json, "fatal"));
    }

    /** Starts a daemon thread that drains a pipe to EOF into {@code sink}. */
    private static Thread pump(java.io.InputStream stream, AtomicReference<String> sink) {
        Thread thread = new Thread(() -> {
            try (java.io.InputStream open = stream) {
                sink.set(new String(open.readAllBytes(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                // The process was killed, or the pipe broke. Whatever arrived before that
                // is still the most useful thing to report, so leave the sink alone.
            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    /** Waits briefly for a pump to finish; its result is read only after this returns. */
    private static void join(Thread pump) throws InterruptedException {
        pump.join(TimeUnit.SECONDS.toMillis(30));
    }

    private static String trim(String text) {
        String collapsed = text == null ? "" : text.strip();
        return collapsed.length() > 600 ? collapsed.substring(collapsed.length() - 600) : collapsed;
    }

    private static Path existingDirectory(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }
        Path resolved = Paths.get(path);
        return Files.isDirectory(resolved) ? resolved : null;
    }

    private static Path locateRepoRoot() {
        Path here = Paths.get("").toAbsolutePath();
        for (Path candidate = here; candidate != null; candidate = candidate.getParent()) {
            if (Files.isRegularFile(candidate.resolve("settings.gradle.kts"))) {
                return candidate;
            }
        }
        return here;
    }
}

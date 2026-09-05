import dev.robocode.tankroyale.runner.BattleResults;
import dev.robocode.tankroyale.runner.BattleRunner;
import dev.robocode.tankroyale.runner.BattleSetup;
import dev.robocode.tankroyale.runner.BotEntry;
import dev.robocode.tankroyale.runner.BotResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs a single Robocode Tank Royale battle (bot vs. itself, wrapped legacy robot) via the
 * Battle Runner API with an embedded server, and writes the outcome as JSON to a file.
 *
 * Intended to be run uncompiled (Java single-file source mode):
 *
 *   java -cp &lt;robocode-tankroyale-runner-fat-jar&gt; TrBattleWorker.java
 *        --bot &lt;bot-dir&gt; --bot2 &lt;bot-dir-copy&gt; --rounds 10 --out result.json --timeout 300
 *
 * Exit code 0 = battle completed (bot errors are reported separately by the orchestrator,
 * which scans the bot dirs' stdout/stderr log files), non-zero = harness-level failure.
 */
public class TrBattleWorker {

    private static final int LOG_CAP = 2000; // max captured runner/booter/server log lines
    private static final Pattern TEAM_MEMBERS = Pattern.compile(
            "\\\"teamMembers\\\"\\s*:\\s*\\[((?:\\s*\\\"(?:\\\\.|[^\\\"\\\\])*\\\"\\s*,?)*)\\]",
            Pattern.DOTALL);
    private static final Pattern JSON_STRING = Pattern.compile("\\\"(?:\\\\.|[^\\\"\\\\])*\\\"");

    public static void main(String[] args) throws Exception {
        Map<String, String> opt = parseArgs(args);
        // Bot directories: --bots takes a File.pathSeparator-joined list (melee needs more
        // than two); --bot/--bot2 remain for the 1-vs-1 case.
        List<Path> botDirs = botDirs(opt);
        int rounds = Integer.parseInt(opt.getOrDefault("rounds", "10"));
        // Arena and participant count come from the caller so each rumble division runs at
        // its own official parameters (C-003); the defaults are the 1-vs-1 ones.
        int width = Integer.parseInt(opt.getOrDefault("width", "800"));
        int height = Integer.parseInt(opt.getOrDefault("height", "600"));
        Path outFile = Path.of(require(opt, "out"));
        int timeoutSecs = Integer.parseInt(opt.getOrDefault("timeout", "0"));
        int turnTimeoutMicros = Integer.parseInt(opt.getOrDefault("turn-timeout", "0")); // 0 = preset default

        List<String> capturedLog = Collections.synchronizedList(new ArrayList<>());
        installLogCapture(capturedLog);

        if (timeoutSecs > 0) {
            startWatchdog(timeoutSecs, outFile, capturedLog);
        }

        Map<String, Object> result = new HashMap<>();
        int exitCode;
        try {
            runBattle(botDirs, rounds, width, height, turnTimeoutMicros, result);
            exitCode = Boolean.TRUE.equals(result.get("ok")) ? 0 : 1;
        } catch (Throwable t) {
            result.put("ok", false);
            result.put("fatal", stackTraceOf(t));
            exitCode = 1;
        }
        result.put("runnerLog", tail(capturedLog, 300));
        Files.writeString(outFile, Json.write(result), StandardCharsets.UTF_8);
        // BattleRunner registers a shutdown hook that stops server/booter/bot processes;
        // System.exit runs it (unlike halt). The hook can hang if a bot process refuses to
        // die, so force the JVM down if the hook has not finished within 30 seconds.
        final int finalExitCode = exitCode;
        Thread exitWatchdog = new Thread(() -> {
            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
                return;
            }
            Runtime.getRuntime().halt(finalExitCode);
        }, "exit-watchdog");
        exitWatchdog.setDaemon(true);
        exitWatchdog.start();
        System.exit(exitCode);
    }

    /**
     * Resolves the bot directories to run. Prefers --bots (a path-separator-joined list, which
     * is how melee gets more than two participants), and falls back to --bot/--bot2.
     */
    private static List<Path> botDirs(Map<String, String> opt) {
        List<Path> dirs = new ArrayList<>();
        String joined = opt.get("bots");
        if (joined != null && !joined.isBlank()) {
            for (String part : joined.split(java.util.regex.Pattern.quote(java.io.File.pathSeparator))) {
                if (!part.isBlank()) {
                    dirs.add(Path.of(part.trim()));
                }
            }
        }
        if (dirs.isEmpty()) {
            dirs.add(Path.of(require(opt, "bot")));
            dirs.add(Path.of(opt.getOrDefault("bot2", opt.get("bot"))));
        }
        return dirs;
    }

    private static void runBattle(List<Path> botDirs, int rounds, int width, int height, int turnTimeoutMicros,
                                  Map<String, Object> result) {
        try (BattleRunner runner = BattleRunner.create(b -> b.embeddedServer())) {
            BattleSetup setup = BattleSetup.classic(s -> {
                s.setNumberOfRounds(rounds);
                s.setArenaWidth(width);
                s.setArenaHeight(height);
                // The preset caps participants for its own game type; melee runs more bots
                // than the classic preset expects, and a team entry expands into several
                // member identities, so raise the ceiling to the number actually staged.
                s.setMaxNumberOfParticipants(expandedParticipantCount(botDirs));
                if (turnTimeoutMicros > 0) {
                    s.setTurnTimeoutMicros(turnTimeoutMicros);
                }
            });
            List<BotEntry> bots = new ArrayList<>();
            for (Path dir : botDirs) {
                bots.add(BotEntry.of(dir));
            }

            BattleResults results = runner.runBattle(setup, bots);

            result.put("ok", true);
            result.put("completed", true);
            result.put("rounds", results.getNumberOfRounds());

            List<Object> participants = new ArrayList<>();
            for (BotResult r : results.getResults()) {
                Map<String, Object> p = new HashMap<>();
                p.put("name", r.getName() + " " + r.getVersion());
                p.put("rank", r.getRank());
                p.put("score", r.getTotalScore());
                p.put("survival", r.getSurvival());
                p.put("lastSurvivorBonus", r.getLastSurvivorBonus());
                p.put("bulletDamage", r.getBulletDamage());
                p.put("bulletDamageBonus", r.getBulletKillBonus());
                p.put("ramDamage", r.getRamDamage());
                p.put("ramDamageBonus", r.getRamKillBonus());
                p.put("firsts", r.getFirstPlaces());
                p.put("seconds", r.getSecondPlaces());
                p.put("thirds", r.getThirdPlaces());
                participants.add(p);
            }
            result.put("participants", participants);
        }
    }

    /** Counts team members after the runner expands each team entry into bot identities. */
    private static int expandedParticipantCount(List<Path> botDirs) {
        int count = 0;
        for (Path botDir : botDirs) {
            int members = 0;
            Path config = botDir.resolve(botDir.getFileName() + ".json");
            try {
                String json = Files.readString(config, StandardCharsets.UTF_8);
                Matcher team = TEAM_MEMBERS.matcher(json);
                if (team.find()) {
                    Matcher member = JSON_STRING.matcher(team.group(1));
                    while (member.find()) {
                        members++;
                    }
                }
            } catch (Exception ignored) {
                // A config-less entry still counts as one, matching the runner's validation.
            }
            count += Math.max(1, members);
        }
        return Math.max(1, count);
    }

    /** Captures runner/booter/server log output (JUL) in memory instead of the console. */
    private static void installLogCapture(List<String> capturedLog) {
        Logger trLogger = Logger.getLogger("dev.robocode.tankroyale");
        trLogger.setLevel(Level.INFO);
        trLogger.setUseParentHandlers(false);
        trLogger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (capturedLog.size() < LOG_CAP) {
                    capturedLog.add(record.getLevel() + " " + record.getMessage());
                }
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() {
            }
        });
    }

    private static void startWatchdog(int timeoutSecs, Path outFile, List<String> capturedLog) {
        Thread watchdog = new Thread(() -> {
            try {
                Thread.sleep(timeoutSecs * 1000L);
            } catch (InterruptedException e) {
                return;
            }
            Map<String, Object> result = new HashMap<>();
            result.put("ok", false);
            result.put("fatal", "Internal watchdog timeout after " + timeoutSecs + "s");
            result.put("runnerLog", tail(capturedLog, 300));
            try {
                Files.writeString(outFile, Json.write(result), StandardCharsets.UTF_8);
            } catch (Exception ignored) {
            }
            System.exit(3); // run shutdown hooks so bot/server processes are cleaned up
        }, "battle-watchdog");
        watchdog.setDaemon(true);
        watchdog.start();
    }

    private static List<String> tail(List<String> lines, int max) {
        synchronized (lines) {
            int from = Math.max(0, lines.size() - max);
            return new ArrayList<>(lines.subList(from, lines.size()));
        }
    }

    private static String stackTraceOf(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private static String require(Map<String, String> opt, String key) {
        String value = opt.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing required argument: --" + key);
        }
        return value;
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2) {
            if (!args[i].startsWith("--")) {
                throw new IllegalArgumentException("Unexpected argument: " + args[i]);
            }
            map.put(args[i].substring(2), args[i + 1]);
        }
        return map;
    }

    /** Minimal JSON writer for maps/lists/strings/numbers/booleans. */
    static class Json {
        static String write(Object value) {
            StringBuilder sb = new StringBuilder();
            writeValue(sb, value);
            return sb.toString();
        }

        private static void writeValue(StringBuilder sb, Object value) {
            if (value == null) {
                sb.append("null");
            } else if (value instanceof String s) {
                sb.append('"').append(escape(s)).append('"');
            } else if (value instanceof Number || value instanceof Boolean) {
                sb.append(value);
            } else if (value instanceof Map<?, ?> map) {
                sb.append('{');
                boolean first = true;
                for (Map.Entry<?, ?> e : map.entrySet()) {
                    if (!first) sb.append(',');
                    first = false;
                    sb.append('"').append(escape(String.valueOf(e.getKey()))).append("\":");
                    writeValue(sb, e.getValue());
                }
                sb.append('}');
            } else if (value instanceof Iterable<?> it) {
                sb.append('[');
                boolean first = true;
                for (Object o : it) {
                    if (!first) sb.append(',');
                    first = false;
                    writeValue(sb, o);
                }
                sb.append(']');
            } else {
                sb.append('"').append(escape(String.valueOf(value))).append('"');
            }
        }

        private static String escape(String s) {
            StringBuilder sb = new StringBuilder(s.length() + 16);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '"' -> sb.append("\\\"");
                    case '\\' -> sb.append("\\\\");
                    case '\n' -> sb.append("\\n");
                    case '\r' -> sb.append("\\r");
                    case '\t' -> sb.append("\\t");
                    case '\b' -> sb.append("\\b");
                    case '\f' -> sb.append("\\f");
                    default -> {
                        if (c < 0x20) {
                            sb.append(String.format("\\u%04x", (int) c));
                        } else {
                            sb.append(c);
                        }
                    }
                }
            }
            return sb.toString();
        }
    }
}

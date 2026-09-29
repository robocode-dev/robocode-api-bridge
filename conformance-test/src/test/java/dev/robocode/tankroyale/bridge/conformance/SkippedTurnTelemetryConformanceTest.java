package dev.robocode.tankroyale.bridge.conformance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Supported JVM integration evidence for HARN-008's skipped-turn observation contract. */
class SkippedTurnTelemetryConformanceTest extends ConformanceTestBase {

    private static final String SKIPPING_ROBOT = "conformance.probes.SkippedTurnProbe";
    private static final String QUIET_ROBOT = "conformance.probes.NoSkippedTurnProbe";
    private static final Path SKIPPING_SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "SkippedTurnProbe.java"));
    private static final Path QUIET_SOURCE = ConformanceHarness.repoRoot().resolve(Path.of(
            "compat-test", "conformance-robots", "conformance", "probes", "NoSkippedTurnProbe.java"));
    private static final Pattern READY = Pattern.compile(
            "BRIDGE_SKIPPED_TURN_TELEMETRY_READY botId=(\\d+)");
    private static final Pattern CALLBACK_EVENT = Pattern.compile("SkippedTurnReported:(\\d+):(\\d+)");
    private static final Pattern TELEMETRY_EVENT = Pattern.compile(
            "\\{\\s*\"bot_id\"\\s*:\\s*(\\d+)\\s*,\\s*"
                    + "\"round\"\\s*:\\s*(\\d+)\\s*,\\s*"
                    + "\"turn\"\\s*:\\s*(\\d+)\\s*}");
    private static final Pattern EMPTY_EVENTS = Pattern.compile("\"events\"\\s*:\\s*\\[\\s*]");
    private static final Pattern NULL_EVENTS = Pattern.compile("\"events\"\\s*:\\s*null");

    @Test
    @DisplayName("HARN-008 positive: the observation preserves callbacks and a completed zero capture")
    void testHARN008_IntegrationPositive_PreservesCallbacksAndCompletedZeroCapture(
            @TempDir Path temporaryDirectory) throws IOException {
        Path collectionDir = temporaryDirectory.resolve("collection");
        Path skippedWork = temporaryDirectory.resolve("skipped-work");
        Path skippedData = temporaryDirectory.resolve("skipped-data");
        Path skippedJar = prepareFixtureJar(
                SKIPPING_ROBOT, SKIPPING_SOURCE, collectionDir);
        assertNull(runCompatibilityMeasurement(collectionDir, skippedData, skippedWork,
                "SkippedTurnProbe", true, null));

        String skippedTelemetry = persistedTelemetry(skippedData, skippedJar);
        assertEquals("captured", Json.scalar(skippedTelemetry, "status"));
        Set<String> recorded = telemetryEvents(skippedTelemetry);
        assertFalse(recorded.isEmpty(), "the deliberately slow probe must produce skipped turns");
        assertTrue(recorded.stream().anyMatch(SkippedTurnTelemetryConformanceTest::isWarmupTurn),
                "events from the first round and its initial turns must be retained: " + recorded);
        assertEquals(callbackEventsByBot(readBotConsoles(skippedWork)), recorded,
                "each bridge tuple must match the callback that reached that bot");

        Path quietWork = temporaryDirectory.resolve("quiet-work");
        Path quietData = temporaryDirectory.resolve("quiet-data");
        Path quietJar = prepareFixtureJar(QUIET_ROBOT, QUIET_SOURCE, collectionDir);
        assertNull(runCompatibilityMeasurement(collectionDir, quietData, quietWork,
                "NoSkippedTurnProbe", true, 10_000_000));

        String quietTelemetry = persistedTelemetry(quietData, quietJar);
        assertEquals("captured", Json.scalar(quietTelemetry, "status"));
        assertTrue(EMPTY_EVENTS.matcher(quietTelemetry).find(),
                "a completed enabled run with no events must persist an empty list: " + quietTelemetry);
    }

    @Test
    @DisplayName("HARN-008 negative: disabled, unavailable, and incomplete capture are not zero")
    void testHARN008_IntegrationNegative_DistinguishesMissingCaptureStates() {
        BattleOutcome disabled = telemetryOutcome(
                Engine.BRIDGE, SKIPPING_ROBOT, SKIPPING_SOURCE, false);
        assertTrue(disabled.completed(), disabled.summary());
        assertTelemetryState(disabled, "disabled");

        BattleOutcome unavailable = telemetryOutcome(
                Engine.CLASSIC, SKIPPING_ROBOT, SKIPPING_SOURCE, true);
        assertTelemetryState(unavailable, "unavailable");

        BattleOutcome incomplete = telemetryOutcome(
                Engine.BRIDGE, SKIPPING_ROBOT, SKIPPING_SOURCE, true, 1);
        assertTelemetryState(incomplete, "incomplete");
    }

    private static void assertTelemetryState(BattleOutcome outcome, String status) {
        String telemetry = skippedTurnTelemetry(outcome);
        assertEquals(status, Json.scalar(telemetry, "status"), outcome.summary());
        assertTrue(NULL_EVENTS.matcher(telemetry).find(),
                () -> status + " capture must not claim an empty event list: " + telemetry);
    }

    private static String skippedTurnTelemetry(BattleOutcome outcome) {
        String telemetry = outcome.skippedTurnTelemetry();
        assertNotNull(telemetry, "the compatibility result omitted skipped-turn telemetry: "
                + outcome.summary());
        return telemetry;
    }

    private Path prepareFixtureJar(String robotClass, Path source, Path collectionDir)
            throws IOException {
        BattleOutcome packaged = telemetryOutcome(Engine.CLASSIC, robotClass, source, false);
        assertTrue(packaged.completed(), packaged.summary());

        Path generatedJar = ConformanceHarness.conformanceRobotJar(robotClass);
        assertTrue(Files.isRegularFile(generatedJar), "the compatibility harness did not package " + robotClass);
        Path destinationDir = collectionDir.resolve("roborumble");
        Files.createDirectories(destinationDir);
        Path destination = destinationDir.resolve(generatedJar.getFileName());
        Files.copy(generatedJar, destination, StandardCopyOption.REPLACE_EXISTING);
        return destination;
    }

    private String runCompatibilityMeasurement(Path collectionDir, Path dataDir, Path workDir,
                                               String selectedRobot, boolean captureSkippedTurns,
                                               Integer turnTimeoutMicros) {
        try {
            Files.createDirectories(dataDir);
            Files.createDirectories(workDir);
        } catch (IOException e) {
            return "could not create isolated measurement directories: " + e.getMessage();
        }
        return runRecordedMeasurement(collectionDir, dataDir, workDir, selectedRobot,
                captureSkippedTurns, turnTimeoutMicros);
    }

    private static String persistedTelemetry(Path dataDir, Path jar) throws IOException {
        String subject = "roborumble/" + jar.getFileName();
        String registry = Files.readString(dataDir.resolve("parity-registry.json"));
        assertTrue(registry.contains("\"" + subject + "\""),
                "the parity registry omitted the measured subject " + subject);
        String state = Files.readString(dataDir.resolve("test_progress.json"));
        String stateTelemetry = Json.object(state, "skipped_turn_telemetry");
        String registryTelemetry = Json.object(registry, "skipped_turn_telemetry");
        assertNotNull(stateTelemetry, "the checkpoint omitted skipped-turn telemetry");
        assertNotNull(registryTelemetry, "the parity observation omitted skipped-turn telemetry");
        assertEquals(Json.scalar(stateTelemetry, "status"), Json.scalar(registryTelemetry, "status"),
                "the registry changed the checkpoint telemetry status");
        assertEquals(telemetryEvents(stateTelemetry), telemetryEvents(registryTelemetry),
                "the registry changed the checkpoint event tuples");
        return registryTelemetry;
    }

    private static List<String> readBotConsoles(Path workDir) throws IOException {
        Path botsDirectory = workDir.resolve("tr-bots");
        List<String> consoles = new ArrayList<>();
        try (var paths = Files.walk(botsDirectory)) {
            paths.filter(path -> path.getFileName().toString().equals("stdout.log"))
                    .forEach(path -> {
                        try {
                            consoles.add(Files.readString(path));
                        } catch (IOException e) {
                            throw new IllegalStateException("could not read bot console " + path, e);
                        }
                    });
        }
        return consoles;
    }

    private static Set<String> telemetryEvents(String telemetry) {
        Set<String> events = new HashSet<>();
        Matcher matcher = TELEMETRY_EVENT.matcher(telemetry);
        while (matcher.find()) {
            String event = matcher.group(1) + ":" + matcher.group(2) + ":" + matcher.group(3);
            assertTrue(events.add(event), "duplicate telemetry tuple: " + event);
        }
        return events;
    }

    private static Set<String> callbackEventsByBot(List<String> consoles) {
        Set<String> events = new HashSet<>();
        Set<Integer> botIds = new HashSet<>();
        for (String console : consoles) {
            Matcher ready = READY.matcher(console);
            assertTrue(ready.find(), "bot telemetry readiness marker is missing from " + console);
            int botId = Integer.parseInt(ready.group(1));
            assertTrue(botIds.add(botId), "duplicate bot readiness marker for bot " + botId);
            Matcher callback = CALLBACK_EVENT.matcher(console);
            while (callback.find()) {
                // Classic StatusEvent rounds are zero-based; Tank Royale telemetry rounds are one-based.
                int round = Integer.parseInt(callback.group(1)) + 1;
                String event = botId + ":" + round + ":" + callback.group(2);
                assertTrue(events.add(event), "duplicate callback tuple: " + event);
            }
        }
        assertEquals(2, botIds.size(), "the persisted measurement must run two bridge bots");
        return events;
    }

    private static boolean isWarmupTurn(String event) {
        String[] fields = event.split(":");
        return Integer.parseInt(fields[1]) == 1 && Integer.parseInt(fields[2]) <= 10;
    }
}

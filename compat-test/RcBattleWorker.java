import robocode.BattleResults;
import robocode.control.BattlefieldSpecification;
import robocode.control.BattleSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Runs a single classic Robocode battle (robot vs. itself) via the Robocode Control API
 * and writes the outcome as JSON to a file.
 *
 * Intended to be run uncompiled (Java single-file source mode):
 *
 *   java -DROBOTPATH=&lt;robots-dir&gt; -cp "&lt;robocode-home&gt;/libs/*" RcBattleWorker.java
 *        --home &lt;working-home-dir&gt; --select "&lt;classname&gt; &lt;version&gt;"
 *        --rounds 10 --out result.json --timeout 300
 *
 * The robots directory (system property ROBOTPATH) is expected to contain exactly the
 * robot jar under test. Exit code 0 = battle completed (robot errors are still reported
 * in the JSON), non-zero = harness-level failure.
 */
public class RcBattleWorker {

    private static final int CONSOLE_CAP = 200_000; // max captured console chars per robot

    public static void main(String[] args) throws Exception {
        Map<String, String> opt = parseArgs(args);
        File home = new File(require(opt, "home"));
        int rounds = Integer.parseInt(opt.getOrDefault("rounds", "10"));
        String select = opt.get("select");
        Path outFile = Path.of(require(opt, "out"));
        int timeoutSecs = Integer.parseInt(opt.getOrDefault("timeout", "0"));

        if (timeoutSecs > 0) {
            startWatchdog(timeoutSecs, outFile);
        }

        Map<String, Object> result = new HashMap<>();
        int exitCode;
        try {
            runBattle(home, rounds, select, result);
            exitCode = Boolean.TRUE.equals(result.get("ok")) ? 0 : 1;
        } catch (Throwable t) {
            result.put("ok", false);
            result.put("fatal", stackTraceOf(t));
            exitCode = 1;
        }
        Files.writeString(outFile, Json.write(result), StandardCharsets.UTF_8);
        // The engine leaves non-daemon threads behind; force the JVM down.
        Runtime.getRuntime().halt(exitCode);
    }

    private static void runBattle(File home, int rounds, String select, Map<String, Object> result) {
        Collector collector = new Collector();
        RobocodeEngine engine = new RobocodeEngine(home);
        try {
            engine.addBattleListener(collector);
            engine.setVisible(false);

            RobotSpecification[] participants = selectParticipants(engine, select);
            if (participants == null || participants.length == 0) {
                result.put("ok", false);
                result.put("fatal", "No robot found in repository matching: " + select);
                return;
            }
            result.put("selected", participants[0].getNameAndVersion());

            BattleSpecification battle = new BattleSpecification(
                    rounds,
                    new BattlefieldSpecification(800, 600),
                    participants);

            engine.runBattle(battle, true); // blocks until the battle is over
        } finally {
            try {
                engine.close();
            } catch (Throwable ignored) {
            }
        }

        result.put("ok", collector.completed);
        result.put("completed", collector.completed);
        result.put("aborted", collector.aborted);
        result.put("rounds", rounds);
        result.put("battleErrors", collector.battleErrors);
        if (!collector.completed && result.get("fatal") == null) {
            result.put("fatal", "Battle did not complete (aborted=" + collector.aborted + ")");
        }

        List<Object> participants = new ArrayList<>();
        if (collector.results != null) {
            for (BattleResults r : collector.results) {
                Map<String, Object> p = new HashMap<>();
                p.put("name", r.getTeamLeaderName());
                p.put("rank", r.getRank());
                p.put("score", r.getScore());
                p.put("survival", r.getSurvival());
                p.put("lastSurvivorBonus", r.getLastSurvivorBonus());
                p.put("bulletDamage", r.getBulletDamage());
                p.put("bulletDamageBonus", r.getBulletDamageBonus());
                p.put("ramDamage", r.getRamDamage());
                p.put("ramDamageBonus", r.getRamDamageBonus());
                p.put("firsts", r.getFirsts());
                p.put("seconds", r.getSeconds());
                p.put("thirds", r.getThirds());
                participants.add(p);
            }
        }
        result.put("participants", participants);

        List<String> consoles = new ArrayList<>();
        for (StringBuilder sb : collector.consoles.values()) {
            consoles.add(sb.toString());
        }
        result.put("consoles", consoles);
        result.put("messages", collector.messages);
    }

    /**
     * Builds the participant array for a "robot (or team) vs. itself" battle.
     * Selecting "X, X" makes the repository return two independent entries; for a team
     * this expands into all member robots per team instance, which is required for the
     * battle to actually contain two teams.
     */
    private static RobotSpecification[] selectParticipants(RobocodeEngine engine, String select) {
        if (select != null && !select.isBlank()) {
            RobotSpecification[] specs = engine.getLocalRepository(select + ", " + select);
            if (specs != null && specs.length > 0) {
                return specs;
            }
        }
        RobotSpecification[] all = engine.getLocalRepository();
        if (all == null || all.length == 0) {
            return null;
        }
        RobotSpecification chosen = all[0];
        if (select != null && !select.isBlank()) {
            String className = select.split(" ")[0];
            for (RobotSpecification s : all) {
                if (className.equals(s.getClassName())) {
                    chosen = s;
                    break;
                }
            }
        }
        return new RobotSpecification[] { chosen, chosen };
    }

    /** Collects results, errors and per-robot console output from battle events. */
    private static class Collector extends BattleAdaptor {
        final List<String> battleErrors = new ArrayList<>();
        final List<String> messages = new ArrayList<>();
        final Map<Integer, StringBuilder> consoles = new HashMap<>();
        BattleResults[] results;
        boolean completed;
        boolean aborted;

        @Override
        public void onBattleStarted(BattleStartedEvent event) {
            for (int i = 0; i < event.getRobotsCount(); i++) {
                consoles.put(i, new StringBuilder());
            }
        }

        @Override
        public void onTurnEnded(TurnEndedEvent event) {
            if (event.getTurnSnapshot() == null) {
                return;
            }
            for (IRobotSnapshot robot : event.getTurnSnapshot().getRobots()) {
                String out = robot.getOutputStreamSnapshot();
                if (out == null || out.isEmpty()) {
                    continue;
                }
                StringBuilder sb = consoles.computeIfAbsent(robot.getRobotIndex(), k -> new StringBuilder());
                if (sb.length() < CONSOLE_CAP) {
                    sb.append(out);
                }
            }
        }

        @Override
        public void onBattleError(BattleErrorEvent event) {
            StringBuilder sb = new StringBuilder(String.valueOf(event.getError()));
            if (event.getErrorInstance() != null) {
                sb.append('\n').append(stackTraceOf(event.getErrorInstance()));
            }
            battleErrors.add(sb.toString());
        }

        @Override
        public void onBattleMessage(BattleMessageEvent event) {
            if (messages.size() < 200) {
                messages.add(event.getMessage());
            }
        }

        @Override
        public void onBattleCompleted(BattleCompletedEvent event) {
            results = event.getIndexedResults();
            completed = true;
        }

        @Override
        public void onBattleFinished(BattleFinishedEvent event) {
            aborted = event.isAborted();
        }
    }

    private static void startWatchdog(int timeoutSecs, Path outFile) {
        Thread watchdog = new Thread(() -> {
            try {
                Thread.sleep(timeoutSecs * 1000L);
            } catch (InterruptedException e) {
                return;
            }
            Map<String, Object> result = new HashMap<>();
            result.put("ok", false);
            result.put("fatal", "Internal watchdog timeout after " + timeoutSecs + "s");
            try {
                Files.writeString(outFile, Json.write(result), StandardCharsets.UTF_8);
            } catch (Exception ignored) {
            }
            Runtime.getRuntime().halt(3);
        }, "battle-watchdog");
        watchdog.setDaemon(true);
        watchdog.start();
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
        @SuppressWarnings("unchecked")
        static String write(Object value) {
            StringBuilder sb = new StringBuilder();
            writeValue(sb, value);
            return sb.toString();
        }

        @SuppressWarnings("unchecked")
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

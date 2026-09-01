package dev.robocode.tankroyale.bridge.conformance;

import java.util.ArrayList;
import java.util.List;

/**
 * What one engine reported for one battle: whether it completed, what each participant
 * printed to its console, and the error signatures that were seen.
 *
 * The console output is the load-bearing part. Classic Robocode's own conformance suite
 * asserts on markers a test robot prints -- "Scanned!!!" and the like -- rather than on
 * coordinates, and that style is what makes an expectation portable between two engines
 * whose physics cannot be compared exactly (AN-002).
 */
final class BattleOutcome {

    private final boolean completed;
    private final List<String> consoles;
    private final List<String> errors;
    private final Double score;
    private final String detail;

    BattleOutcome(boolean completed, List<String> consoles, List<String> errors,
                  Double score, String detail) {
        this.completed = completed;
        this.consoles = List.copyOf(consoles);
        this.errors = List.copyOf(errors);
        this.score = score;
        this.detail = detail;
    }

    boolean completed() {
        return completed;
    }

    List<String> consoles() {
        return consoles;
    }

    List<String> errors() {
        return errors;
    }

    Double score() {
        return score;
    }

    String detail() {
        return detail;
    }

    /** True when any participant printed the marker. */
    boolean anyConsoleContains(String marker) {
        return consoles.stream().anyMatch(c -> c.contains(marker));
    }

    /** True when every participant printed the marker. */
    boolean everyConsoleContains(String marker) {
        return !consoles.isEmpty() && consoles.stream().allMatch(c -> c.contains(marker));
    }

    /** How many times the marker appears across all participants. */
    int countOf(String marker) {
        int total = 0;
        for (String console : consoles) {
            total += countIn(console, marker);
        }
        return total;
    }

    /** How many times the marker appears in each participant's console. */
    List<Integer> countsOf(String marker) {
        List<Integer> counts = new ArrayList<>(consoles.size());
        for (String console : consoles) {
            counts.add(countIn(console, marker));
        }
        return counts;
    }

    private static int countIn(String text, String marker) {
        int total = 0;
        for (int from = 0; (from = text.indexOf(marker, from)) >= 0; from += marker.length()) {
            total++;
        }
        return total;
    }

    /** A compact description for an assertion message. */
    String summary() {
        List<String> parts = new ArrayList<>();
        parts.add("completed=" + completed);
        parts.add("score=" + score);
        parts.add("participants=" + consoles.size());
        if (!errors.isEmpty()) {
            parts.add("errors=" + errors);
        }
        if (detail != null && !detail.isBlank()) {
            parts.add("detail=" + detail);
        }
        return String.join(", ", parts);
    }
}

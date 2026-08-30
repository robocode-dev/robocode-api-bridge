package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.robotinterfaces.peer.IJuniorRobotPeer;
import robocode.robotinterfaces.peer.ITeamRobotPeer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Acceptance evidence for ROUTE-011 — every method on the peer surface is exercised by a
 * routing test, or is exempt for a stated reason.
 *
 * This is the criterion that makes the rest mean something. A suite covering most of a
 * surface says nothing about the part it misses, and the part it misses is where the next
 * defect is: {@code AN-005} and {@code AN-007} were both in call sites nobody had looked at.
 *
 * It enumerates the peer hierarchy by reflection rather than from a list, so a method added
 * to any of the five interfaces — or inherited from a new one — fails the build until it is
 * covered or exempted. That is the difference between claiming a one-to-one mapping and
 * having one.
 *
 * Coverage is judged by reading the routing tests' own source and looking for a call through
 * the peer. That is deliberately literal: a registry mapping methods to test names would
 * prove a name exists, not that anything calls the method.
 */
class PeerSurfaceCoverageTest {

    /**
     * Methods with no routing to test, each with the reason it has none.
     *
     * An exemption is a claim that there is nothing to get wrong, so each is stated
     * individually rather than by pattern — a pattern would silently absorb the next method
     * that happens to match it.
     */
    private static final Map<String, String> EXEMPT = Map.of(
            "getCall", "a classic engine internal the bridge deliberately ignores; it has no Bot API counterpart",
            "setCall", "the same, for the setter side",
            "stopThread", "sets a local flag the peer reads; it issues no Bot API call",
            "getName", "reads the robot name the wrapper recorded, not any Bot API state",
            "getNumSentries", "border sentries are a classic concept Tank Royale has no equivalent for; the peer reports none",
            "getSentryBorderSize", "the same; reporting zero is the honest answer rather than a routed one"
    );

    private static final List<String> TEST_SOURCES = List.of(
            "BasicPeerRoutingTest.java",
            "StandardPeerRoutingTest.java",
            "AdvancedPeerRoutingTest.java",
            "TeamAndJuniorPeerRoutingTest.java",
            "EventListRoutingTest.java",
            "StatusAndPeerAgreementTest.java");

    @Test
    @DisplayName("ROUTE-011 positive: every peer method is exercised by a routing test")
    void testROUTE011_UnitPositive_EveryPeerMethodIsExercisedByARoutingTest() {
        String sources = readTestSources();
        Set<String> uncovered = new TreeSet<>();

        for (String method : peerSurface()) {
            if (EXEMPT.containsKey(method)) {
                continue;
            }
            if (!sources.contains("peer." + method + "(")
                    && !sources.contains("Peer." + method + "(")) {
                uncovered.add(method);
            }
        }

        if (!uncovered.isEmpty()) {
            fail("These peer methods have no routing test, and none is exempt:\n  "
                    + String.join("\n  ", uncovered)
                    + "\n\nAdd a test that calls each through the peer and asserts the Bot API "
                    + "call it makes, or add it to EXEMPT with the reason it routes nothing. "
                    + "A method reaching a robot untested is how AN-005 and AN-007 happened.");
        }
    }

    @Test
    @DisplayName("ROUTE-011 negative: an exemption naming no real method is itself a failure")
    void testROUTE011_UnitNegative_EveryExemptionNamesAMethodThatStillExists() {
        Set<String> surface = peerSurface();

        Set<String> stale = EXEMPT.keySet().stream()
                .filter(method -> !surface.contains(method))
                .collect(Collectors.toCollection(TreeSet::new));

        // An exemption for a method that no longer exists is a claim about nothing, and it
        // would keep excusing a future method that happens to take the same name.
        assertTrue(stale.isEmpty(), "these exemptions name methods not on the peer surface: " + stale);
    }

    @Test
    @DisplayName("ROUTE-011 negative: the check fails when a covered method loses its test")
    void testROUTE011_UnitNegative_TheCheckWouldNoticeAMissingTest() {
        // Guards the guard. If the source scan silently found nothing — a moved directory, a
        // renamed file — every method would look covered and the check would pass while
        // proving nothing.
        String sources = readTestSources();

        assertTrue(sources.contains("peer.setMove("),
                "the source scan found no known call, so it is not reading the tests");
        assertTrue(!sources.contains("peer.thisMethodDoesNotExist("),
                "the scan reports calls that are not there");
    }

    /** Every method reachable on the peer surface a robot is given. */
    private static Set<String> peerSurface() {
        Set<String> names = new LinkedHashSet<>();
        for (Class<?> iface : List.of(ITeamRobotPeer.class, IJuniorRobotPeer.class)) {
            collect(iface, names);
        }
        return names;
    }

    private static void collect(Class<?> iface, Set<String> names) {
        for (Method method : iface.getDeclaredMethods()) {
            names.add(method.getName());
        }
        for (Class<?> parent : iface.getInterfaces()) {
            collect(parent, names);
        }
    }

    private static String readTestSources() {
        Path dir = testSourceDirectory();
        StringBuilder all = new StringBuilder();
        for (String name : TEST_SOURCES) {
            Path file = dir.resolve(name);
            if (!Files.isRegularFile(file)) {
                fail("routing test source not found: " + file
                        + " — the coverage check cannot read what it is meant to check");
            }
            try {
                all.append(Files.readString(file)).append('\n');
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return all.toString();
    }

    /** Locates this package's test sources, from wherever the tests were started. */
    private static Path testSourceDirectory() {
        Path relative = Paths.get("robocode-api", "src", "test", "java", "dev", "robocode",
                "tankroyale", "bridge");
        Path here = Paths.get("").toAbsolutePath();
        for (Path candidate = here; candidate != null; candidate = candidate.getParent()) {
            Path resolved = candidate.resolve(relative);
            if (Files.isDirectory(resolved)) {
                return resolved;
            }
            Path inModule = candidate.resolve(Paths.get("src", "test", "java", "dev", "robocode",
                    "tankroyale", "bridge"));
            if (Files.isDirectory(inModule)) {
                return inModule;
            }
        }
        throw new AssertionError("could not locate the routing test sources from " + here);
    }

}

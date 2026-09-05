package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.AccessControlException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Sanity coverage for {@link RobotData}'s resolver, guarding the order asterisk-stripping and
 * traversal-rejection run in.
 * <p>
 * FIO-001's own machine evidence is the conformance tier (Integration, per
 * {@code CAP-004/criteria.md}); this class covers a defect a review pass on CH-009 found in
 * that resolver before it shipped: checking {@code ".."} against the raw name, before
 * asterisks are stripped, let a name like {@code ".*."} pass the check and then collapse into
 * {@code ".."} once stripped, escaping the data directory.
 */
class RobotDataResolveTest {

    private static final File DIRECTORY = new File("data-root");

    @BeforeAll
    static void ensureRobotNameIsSet() {
        // RobotData.resolve is static but RobotData still carries a class-level singleton (the data
        // directory path) that initializes from RobotName on first use, elsewhere in the same JVM.
        RobotName.setName("RobotDataResolveTest");
    }

    @Test
    @DisplayName("Sanity positive: a plain name resolves inside the directory")
    void testSanity_UnitPositive_PlainNameResolvesInsideDirectory() {
        File resolved = RobotData.resolve(DIRECTORY, "plain.txt");
        assertEquals(new File(DIRECTORY, "plain.txt"), resolved);
    }

    @Test
    @DisplayName("Sanity negative: a literal \"..\" is rejected")
    void testSanity_UnitNegative_LiteralTraversalIsRejected() {
        assertThrows(AccessControlException.class, () -> RobotData.resolve(DIRECTORY, "../escape.txt"));
    }

    @Test
    @DisplayName("Sanity negative: an asterisk-adjacent traversal is rejected once stripped")
    void testSanity_UnitNegative_AsteriskAdjacentTraversalIsRejectedOnceStripped() {
        // No literal ".." until the "*" is gone, so a check against the raw name alone would miss this.
        assertThrows(AccessControlException.class, () -> RobotData.resolve(DIRECTORY, ".*./escape.txt"));
        assertThrows(AccessControlException.class, () -> RobotData.resolve(DIRECTORY, "sub/.*./.*./.*./escape.txt"));
    }
}

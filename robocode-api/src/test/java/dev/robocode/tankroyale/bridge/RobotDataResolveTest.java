package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessControlException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit coverage for {@link RobotData}'s resolver, the mechanism behind FIO-001's redirection.
 * <p>
 * FIO-001's declared evidence class is Integration (per {@code CAP-004/criteria.md}), carried by
 * {@code FileRedirectionConformanceTest}; this class adds focused unit coverage of the resolver
 * itself, including a defect a review pass on CH-009 found before it shipped: checking
 * {@code ".."} against the raw name, before asterisks are stripped, let a name like
 * {@code ".*."} pass the check and then collapse into {@code ".."} once stripped, escaping the
 * data directory.
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
    @DisplayName("FIO-001 unit: a plain name resolves inside the directory")
    void testFIO001_UnitPositive_PlainNameResolvesInsideDirectory() {
        File resolved = RobotData.resolve(DIRECTORY, "plain.txt");
        assertEquals(new File(DIRECTORY, "plain.txt"), resolved);
    }

    @Test
    @DisplayName("FIO-001 unit positive: a missing data file is created in the robot data directory")
    void testFIO001_UnitPositive_CreatesMissingDataFile(@TempDir Path tempDirectory) {
        File dataDirectory = tempDirectory.resolve("Robot.data").toFile();
        File resolved = RobotData.resolveAndCreate(dataDirectory, "state.dat");

        assertTrue(resolved.isFile());
        assertEquals(0L, resolved.length());
        assertEquals(dataDirectory.getAbsoluteFile(), resolved.getParentFile().getAbsoluteFile());
    }

    @Test
    @DisplayName("FIO-001 unit positive: a bundled data file is copied into the robot data directory")
    void testFIO001_UnitPositive_CopiesBundledDataFile(@TempDir Path tempDirectory) throws Exception {
        File dataDirectory = tempDirectory.resolve("Robot.data").toFile();
        File resolved = RobotData.resolveAndCreate(dataDirectory, "bundled.txt", RobotDataResolveTest.class);

        assertTrue(resolved.isFile());
        assertEquals("bundled-state", Files.readString(resolved.toPath()).trim());
    }

    @Test
    @DisplayName("FIO-001 unit negative: a missing external absolute path is not created")
    void testFIO001_UnitNegative_DoesNotCreateExternalAbsoluteFile(@TempDir Path tempDirectory) {
        File dataDirectory = tempDirectory.resolve("Robot.data").toFile();
        File externalFile = tempDirectory.resolve("outside.dat").toFile();

        File resolved = RobotData.resolveAndCreate(dataDirectory, externalFile.getAbsolutePath());

        assertFalse(externalFile.exists());
        assertFalse(resolved.exists());
    }

    @Test
    @DisplayName("FIO-001 unit negative: a literal \"..\" is rejected")
    void testFIO001_UnitNegative_LiteralTraversalIsRejected() {
        assertThrows(AccessControlException.class, () -> RobotData.resolve(DIRECTORY, "../escape.txt"));
    }

    @Test
    @DisplayName("FIO-001 unit negative: an asterisk-adjacent traversal is rejected once stripped")
    void testFIO001_UnitNegative_AsteriskAdjacentTraversalIsRejectedOnceStripped() {
        // No literal ".." until the "*" is gone, so a check against the raw name alone would miss this.
        assertThrows(AccessControlException.class, () -> RobotData.resolve(DIRECTORY, ".*./escape.txt"));
        assertThrows(AccessControlException.class, () -> RobotData.resolve(DIRECTORY, "sub/.*./.*./.*./escape.txt"));
    }
}

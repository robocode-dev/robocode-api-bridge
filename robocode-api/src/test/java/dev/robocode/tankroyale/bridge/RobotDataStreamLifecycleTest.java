package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import robocode.RobocodeFileOutputStream;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RobotDataStreamLifecycleTest {

    @TempDir
    Path tempDir;

    @BeforeAll
    static void ensureRobotNameIsSet() {
        RobotName.setName("RobotDataStreamLifecycleTest");
    }

    @AfterEach
    void closeOpenedStreams() {
        RobotData.closeOpenStreams();
    }

    @Test
    @DisplayName("FIO-005 unit: round-end cleanup releases abandoned stream slots")
    void testFIO005_UnitPositive_RoundEndClosesAbandonedStreams() throws IOException {
        List<RobocodeFileOutputStream> firstRound = openStreams("first-round");
        assertThrows(SecurityException.class, () -> open("first-round-overflow"));

        RobotData.closeOpenStreams();

        for (RobocodeFileOutputStream stream : firstRound) {
            assertThrows(IOException.class, () -> stream.write(1));
        }
        assertDoesNotThrow(() -> openStreams("second-round"));
        assertThrows(SecurityException.class, () -> open("second-round-overflow"));
    }

    private List<RobocodeFileOutputStream> openStreams(String prefix) throws IOException {
        List<RobocodeFileOutputStream> streams = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            streams.add(open(prefix + "-" + i));
        }
        return streams;
    }

    private RobocodeFileOutputStream open(String name) throws IOException {
        return new RobocodeFileOutputStream(tempDir.resolve(name + ".dat").toFile());
    }
}

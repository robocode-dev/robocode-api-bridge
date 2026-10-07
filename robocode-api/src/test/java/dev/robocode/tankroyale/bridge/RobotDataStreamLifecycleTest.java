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
    private final List<RobocodeFileOutputStream> openedStreams = new ArrayList<>();

    @BeforeAll
    static void ensureRobotNameIsSet() {
        RobotName.setName("RobotDataStreamLifecycleTest");
    }

    @AfterEach
    void closeOpenedStreams() {
        for (RobocodeFileOutputStream stream : openedStreams) {
            try {
                stream.close();
            } catch (IOException ignored) {
                // The test has already made its assertion about the stream state.
            }
        }
    }

    @Test
    @DisplayName("FIO-005 unit: an unclosed stream keeps its slot until the robot closes it")
    void testFIO005_UnitPositive_UnclosedStreamKeepsItsSlot() throws IOException {
        List<RobocodeFileOutputStream> streams = openStreams("open");
        assertThrows(SecurityException.class, () -> open("overflow"));

        streams.get(0).close();
        assertDoesNotThrow(() -> open("after-close"));
    }

    private List<RobocodeFileOutputStream> openStreams(String prefix) throws IOException {
        List<RobocodeFileOutputStream> streams = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            streams.add(open(prefix + "-" + i));
        }
        return streams;
    }

    private RobocodeFileOutputStream open(String name) throws IOException {
        RobocodeFileOutputStream stream = new RobocodeFileOutputStream(tempDir.resolve(name + ".dat").toFile());
        openedStreams.add(stream);
        return stream;
    }
}

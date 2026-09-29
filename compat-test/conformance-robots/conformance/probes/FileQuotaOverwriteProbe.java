package conformance.probes;

import robocode.AdvancedRobot;
import robocode.RobocodeFileOutputStream;

import java.io.File;
import java.io.IOException;

/** Replaces the same data file twice; replacement must reuse the file's existing quota. */
public class FileQuotaOverwriteProbe extends AdvancedRobot {

    @Override
    public void run() {
        byte[] contents = new byte[100_000];
        File file = getDataFile("quota-overwrite-test");

        for (int i = 0; i < 2; i++) {
            try (RobocodeFileOutputStream stream = new RobocodeFileOutputStream(file)) {
                stream.write(contents);
                out.println("OverwriteSucceeded:" + i);
            } catch (IOException e) {
                out.println("OverwriteFailed:" + e.getMessage());
                break;
            }
        }

        while (true) {
            turnLeft(1);
        }
    }
}

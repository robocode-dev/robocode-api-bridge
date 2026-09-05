package conformance.probes;

import robocode.AdvancedRobot;
import robocode.RobocodeFileOutputStream;

import java.io.File;
import java.io.IOException;

/**
 * Ported from classic's {@code tested.robots.FileWriteSize}: writes three 100000-byte
 * chunks against the documented 200000-byte quota, so the third write must be refused at
 * exactly the point classic refuses it.
 */
public class FileQuotaProbe extends AdvancedRobot {

    @Override
    public void run() {
        out.println("DataQuota:" + getDataQuotaAvailable());

        byte[] chunk = new byte[100_000];
        File file = getDataFile("quota-test");
        file.delete();

        RobocodeFileOutputStream stream = null;
        try {
            stream = new RobocodeFileOutputStream(file);
            for (int i = 0; i < 3; i++) {
                stream.write(chunk);
                out.println("WroteChunk:" + i);
            }
        } catch (IOException e) {
            out.println("QuotaExceeded:" + e.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                    // already reported above
                }
            }
            file.delete();
        }

        while (true) {
            turnLeft(1);
        }
    }
}

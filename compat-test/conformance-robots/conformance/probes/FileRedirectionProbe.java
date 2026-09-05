package conformance.probes;

import robocode.AdvancedRobot;
import robocode.RobocodeFileOutputStream;

import java.io.File;
import java.io.IOException;

/**
 * Writes through {@code getDataFile} with a plain name and with a root-relative name that
 * looks like it escapes the data directory, then reports what was resolved and what the
 * directory listing sees, so a conformance test can prove the two calls agree.
 */
public class FileRedirectionProbe extends AdvancedRobot {

    @Override
    public void run() {
        out.println("DataDirectory:" + getDataDirectory().getAbsolutePath());
        writeAndReport("plain-name.txt");
        writeAndReport("/root-relative-name.txt");
        listDirectory();
        while (true) {
            turnLeft(1);
        }
    }

    private void writeAndReport(String name) {
        try {
            File file = getDataFile(name);
            out.println("Resolved:" + name + ":" + file.getAbsolutePath());
            try (RobocodeFileOutputStream stream = new RobocodeFileOutputStream(file)) {
                stream.write(42);
            }
            out.println("WriteSucceeded:" + name + ":" + file.exists());
        } catch (IOException | SecurityException e) {
            out.println("WriteFailed:" + name + ":" + e);
        }
    }

    private void listDirectory() {
        File[] files = getDataDirectory().listFiles();
        if (files == null) {
            out.println("DirectoryListing:none");
            return;
        }
        for (File file : files) {
            out.println("DirectoryListing:" + file.getName());
        }
    }
}

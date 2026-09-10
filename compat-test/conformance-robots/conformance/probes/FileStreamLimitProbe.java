package conformance.probes;

import robocode.AdvancedRobot;
import robocode.RobocodeFileOutputStream;

import java.io.File;
import java.io.IOException;

/**
 * Exercises classic's five-open-robot-file-stream limit and confirms that closing a stream
 * releases its slot.
 */
public class FileStreamLimitProbe extends AdvancedRobot {

    @Override
    public void run() {
        RobocodeFileOutputStream[] streams = new RobocodeFileOutputStream[6];
        File[] files = new File[6];
        try {
            for (int i = 0; i < streams.length; i++) {
                files[i] = getDataFile("stream-limit-" + i);
                files[i].delete();
                streams[i] = new RobocodeFileOutputStream(files[i]);
                out.println("Opened:" + (i + 1));
            }
        } catch (SecurityException exception) {
            out.println("StreamLimit:" + exception.getMessage().split("\\R", 2)[0]);
        } catch (IOException exception) {
            out.println("UnexpectedIOException:" + exception.getMessage());
        } finally {
            closeAll(streams);
        }

        RobocodeFileOutputStream reopened = null;
        try {
            reopened = new RobocodeFileOutputStream(getDataFile("stream-limit-reopened"));
            out.println("Reopened:true");
        } catch (IOException | SecurityException exception) {
            out.println("Reopened:false:" + exception.getMessage());
        } finally {
            if (reopened != null) {
                try {
                    reopened.close();
                } catch (IOException ignored) {
                    // The result was already reported above.
                }
            }
        }

        while (true) {
            turnLeft(1);
        }
    }

    private void closeAll(RobocodeFileOutputStream[] streams) {
        for (RobocodeFileOutputStream stream : streams) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                    // The stream-limit result was already reported above.
                }
            }
        }
    }
}

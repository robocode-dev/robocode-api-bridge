package robocode;

import dev.robocode.tankroyale.bridge.RobotData;
import robocode.exception.RobotException;

import java.io.*;

/**
 * RobocodeFileOutputStream is similar to a {@link java.io.FileOutputStream}
 * and is used for streaming/writing data out to a file, which you got
 * previously by calling {@link AdvancedRobot#getDataFile(String) getDataFile()}.
 * <p>
 * You should read {@link java.io.FileOutputStream} for documentation of this
 * class.
 * <p>
 * Please notice that the max. size of your data file is set to 200000
 * (~195 KB).
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @see AdvancedRobot#getDataFile(String)
 * @see java.io.FileOutputStream
 */
@SuppressWarnings("unused") // API
public class RobocodeFileOutputStream extends OutputStream {
    private final FileOutputStream out;
    private final String fileName;

    /**
     * Constructs a new RobocodeFileOutputStream.
     * See {@link java.io.FileOutputStream#FileOutputStream(File)} for documentation about this constructor.
     *
     * @param file stream
     * @throws IOException when file could not be created
     * @see java.io.FileOutputStream#FileOutputStream(File)
     */
    public RobocodeFileOutputStream(File file) throws IOException {
        this(file.getPath());
    }

    /**
     * Constructs a new RobocodeFileOutputStream.
     * See {@link java.io.FileOutputStream#FileOutputStream(FileDescriptor)} for documentation about this constructor.
     *
     * @param fdObj the file descriptor to be opened for writing
     * @see java.io.FileOutputStream#FileOutputStream(FileDescriptor)
     */
    @Deprecated
    public RobocodeFileOutputStream(FileDescriptor fdObj) {
        throw new RobotException("Creating a RobocodeFileOutputStream with a FileDescriptor is not supported.");
    }

    /**
     * Constructs a new RobocodeFileOutputStream.
     * See {@link java.io.FileOutputStream#FileOutputStream(String)} for documentation about this constructor.
     *
     * @param fileName file name
     * @throws IOException when file could not be created
     * @see java.io.FileOutputStream#FileOutputStream(String)
     */
    public RobocodeFileOutputStream(String fileName) throws java.io.IOException {
        this(fileName, false);
    }

    /**
     * Constructs a new RobocodeFileOutputStream.
     * See {@link java.io.FileOutputStream#FileOutputStream(String, boolean)} for documentation about this constructor.
     *
     * @param fileName file name
     * @param append   should append at the end of the file
     * @throws IOException when file could not be created
     * @see java.io.FileOutputStream#FileOutputStream(String, boolean)
     */
    public RobocodeFileOutputStream(String fileName, boolean append) throws IOException {
        this.fileName = fileName;
        // fileName is opened verbatim, matching classic's ThreadManager.createRobotFileStream: the
        // redirecting resolution happens once, in AdvancedRobot#getDataFile, and a File obtained from
        // there must not be resolved a second time here or an absolute, already-resolved path would be
        // re-rooted under itself.
        out = new FileOutputStream(fileName, append);
    }

    /**
     * Closes this output stream. See {@link java.io.FileOutputStream#close()}
     * for documentation about this method.
     *
     * @see java.io.FileOutputStream#close()
     */
    @Override
    public final void close() throws IOException {
        out.close();
    }

    /**
     * Flushes this output stream. See {@link java.io.FileOutputStream#flush()} for documentation about this method.
     *
     * @see java.io.FileOutputStream#flush()
     */
    @Override
    public final void flush() throws IOException {
        out.flush();
    }

    /**
     * Returns the filename of this output stream.
     *
     * @return the filename of this output stream.
     */
    public final String getName() {
        return fileName;
    }

    /**
     * Writes a byte array to this output stream.
     * See {@link java.io.FileOutputStream#write(byte[])} for documentation about this method.
     *
     * @see java.io.FileOutputStream#write(byte[])
     */
    @Override
    public final void write(byte[] b) throws IOException {
        try {
            RobotData.checkQuota(b.length);
            out.write(b);
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    /**
     * Writes a byte array to this output stream.
     * See {@link java.io.FileOutputStream#write(byte[], int, int)} for documentation about this method.
     *
     * @see java.io.FileOutputStream#write(byte[], int, int)
     */
    @Override
    public final void write(byte[] b, int off, int len) throws IOException {
        try {
            RobotData.checkQuota(len);
            out.write(b, off, len);
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    /**
     * Writes a single byte to this output stream.
     * See {@link java.io.FileOutputStream#write(int)} for documentation about this method.
     *
     * @see java.io.FileOutputStream#write(int)
     */
    @Override
    public final void write(int b) throws IOException {
        try {
            RobotData.checkQuota(1);
            out.write(b);
        } catch (IOException e) {
            close();
            throw e;
        }
    }
}
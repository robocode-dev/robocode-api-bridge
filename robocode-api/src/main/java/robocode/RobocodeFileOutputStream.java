package robocode;

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
     * See {@link java.io.FileOutputStream#FileOutputStream(File)}
     * for documentation about this constructor.
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
     * See {@link java.io.FileOutputStream#FileOutputStream(FileDescriptor)}
     * for documentation about this constructor.
     *
     * @param fdObj desciptor
     * @see java.io.FileOutputStream#FileOutputStream(FileDescriptor)
     */
    @Deprecated
    public RobocodeFileOutputStream(FileDescriptor fdObj) {
        throw new RobotException("Creating a RobocodeFileOutputStream with a FileDescriptor is not supported.");
    }

    /**
     * Constructs a new RobocodeFileOutputStream.
     * See {@link java.io.FileOutputStream#FileOutputStream(String)}
     * for documentation about this constructor.
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
     * See {@link java.io.FileOutputStream#FileOutputStream(String, boolean)}
     * for documentation about this constructor.
     *
     * @param fileName file name
     * @param append   should append at the end of the file
     * @throws IOException when file could not be created
     * @see java.io.FileOutputStream#FileOutputStream(String, boolean)
     */
    public RobocodeFileOutputStream(String fileName, boolean append) throws IOException {
        this.fileName = fileName;

        final IThreadManagerBase threadManager = ContainerBase.getComponent(IThreadManagerBase.class);

        if (threadManager == null) {
            throw new RobotException("ThreadManager cannot be null!");
        }

        out = threadManager.createRobotFileStream(fileName, append);
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
     * Flushes this output stream. See {@link java.io.FileOutputStream#flush()}
     * for documentation about this method.
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
     * See {@link java.io.FileOutputStream#write(byte[])} for documentation
     * about this method.
     *
     * @see java.io.FileOutputStream#write(byte[])
     */
    @Override
    public final void write(byte[] b) throws IOException {
        out.write(b);
    }

    /**
     * Writes a byte array to this output stream.
     * See {@link java.io.FileOutputStream#write(byte[], int, int)} for
     * documentation about this method.
     *
     * @see java.io.FileOutputStream#write(byte[], int, int)
     */
    @Override
    public final void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    /**
     * Writes a single byte to this output stream.
     * See {@link java.io.FileOutputStream#write(int)} for documentation about
     * this method.
     *
     * @see java.io.FileOutputStream#write(int)
     */
    @Override
    public final void write(int b) throws IOException {
        out.write(b);
    }
}
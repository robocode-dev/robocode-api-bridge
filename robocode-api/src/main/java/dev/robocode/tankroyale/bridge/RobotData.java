package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessControlException;

/**
 * Resolves every path a robot names against its data directory, the way classic Robocode's
 * {@code RobotFileSystemManager} does, so no path a robot supplies reaches the filesystem unresolved.
 */
public final class RobotData {

    /** The classic filesystem quota, documented in {@link robocode.RobocodeFileOutputStream} and enforced here. */
    private static final long MAX_QUOTA = 200_000L;

    private static final Path dataDirPath;
    private static long quotaUsed;
    private static boolean quotaMessagePrinted;

    static {
        dataDirPath = Paths.get("").resolve(RobotName.getName() + ".data");
        try {
            Files.createDirectories(dataDirPath);
        } catch (IOException e) {
            throw new BotException("Could not create data directory: " + dataDirPath);
        }
        quotaUsed = 0;
        File[] existingFiles = dataDirPath.toFile().listFiles();
        if (existingFiles != null) {
            for (File file : existingFiles) {
                quotaUsed += file.length();
            }
        }
    }

    public static File getDataDirectory() {
        return dataDirPath.toAbsolutePath().toFile();
    }

    /**
     * Resolves a robot-supplied filename against the data directory, matching classic's
     * {@code RobotFileSystemManager.getDataFile}: asterisks are stripped, and a {@code java.io.File}
     * merge is used rather than {@code java.nio.file.Path#resolve} so an absolute or root-relative
     * name is re-rooted inside the directory instead of overriding it outright.
     *
     * @throws AccessControlException if filename contains "..", matching classic's
     *                                 {@code AdvancedRobotProxy.getDataFile}.
     */
    public static File getDataFile(String filename) {
        return resolve(getDataDirectory(), filename);
    }

    /**
     * The resolver itself, independent of the singleton data directory, so it can be exercised
     * directly against an arbitrary directory in a unit test.
     */
    static File resolve(File directory, String filename) {
        // Strip first, then check: a name like ".*." contains no literal ".." until the asterisk is
        // gone, and checking before stripping would let it collapse into ".." after the check passed.
        String sanitized = filename.replace("*", "");
        if (sanitized.contains("..")) {
            throw new AccessControlException("no relative path allowed");
        }
        return new File(directory, sanitized);
    }

    public static long getMaxQuota() {
        return MAX_QUOTA;
    }

    public static synchronized long getQuotaUsed() {
        return quotaUsed;
    }

    /**
     * Charges {@code numBytes} against the data directory's quota, matching classic's
     * {@code RobotFileSystemManager.checkQuota}: a write that would exceed {@link #MAX_QUOTA} is
     * refused with an {@link IOException} carrying the same message classic raises, printed once.
     */
    public static synchronized void checkQuota(long numBytes) throws IOException {
        if (numBytes < 0) {
            throw new IllegalArgumentException("checkQuota on negative numBytes!");
        }
        if (quotaUsed + numBytes <= MAX_QUOTA) {
            quotaUsed += numBytes;
        } else {
            String msg = "You have reached your filesystem quota of: " + MAX_QUOTA + " bytes.";
            if (!quotaMessagePrinted) {
                System.out.println("SYSTEM: " + msg);
                quotaMessagePrinted = true;
            }
            throw new IOException(msg);
        }
    }
}

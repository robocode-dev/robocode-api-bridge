import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Properties;

final class PreviewFiles {
    private PreviewFiles() { }

    static String sha256(byte[] bytes) {
        try {
            return hex(MessageDigest.getInstance("SHA-256").digest(bytes));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }

    static String sha256(Path path) throws IOException {
        try (InputStream input = Files.newInputStream(path)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[65536];
            int count;
            while ((count = input.read(buffer)) != -1) digest.update(buffer, 0, count);
            return hex(digest.digest());
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }

    private static String hex(byte[] bytes) {
        StringBuilder text = new StringBuilder();
        for (byte value : bytes) text.append(String.format("%02x", value & 255));
        return text.toString();
    }

    static void safeSegment(String name) throws IOException {
        String base = name.split("\\.", 2)[0].toUpperCase(Locale.ROOT);
        if (name.isBlank() || name.equals(".") || name.equals("..") || name.endsWith(".") || name.endsWith(" ")
                || name.matches(".*[\\x00-\\x1f\\\\/:*?\"<>|].*")
                || base.matches("CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9]")) {
            throw new IOException("Unsafe generated filename: " + name);
        }
    }

    static void safeClassName(String name) throws IOException {
        safeSegment(name);
        for (String segment : name.split("\\.", -1)) {
            if (segment.isEmpty() || !Character.isJavaIdentifierStart(segment.codePointAt(0))) {
                throw new IOException("Invalid robot class name: " + name);
            }
            for (int offset = Character.charCount(segment.codePointAt(0)); offset < segment.length();) {
                int point = segment.codePointAt(offset);
                if (!Character.isJavaIdentifierPart(point)) throw new IOException("Invalid robot class name: " + name);
                offset += Character.charCount(point);
            }
        }
    }

    static Path canonical(Path path) throws IOException {
        Path absolute = path.toAbsolutePath().normalize();
        Path existing = absolute;
        while (existing != null && !Files.exists(existing, LinkOption.NOFOLLOW_LINKS)) existing = existing.getParent();
        if (existing == null) throw new IOException("No existing parent for " + path);
        return existing.toRealPath().resolve(existing.relativize(absolute)).normalize();
    }

    static void rejectLinks(Path path) throws IOException {
        Path current = path.toAbsolutePath().normalize();
        while (current != null) {
            if (Files.isSymbolicLink(current) || (Files.exists(current, LinkOption.NOFOLLOW_LINKS)
                    && !current.toRealPath().equals(current))) {
                throw new IOException("Symbolic-link or redirected destination is not supported: " + path);
            }
            current = current.getParent();
        }
    }

    static Path child(Path root, String relative) throws IOException {
        Path target = root.resolve(relative).normalize();
        if (Path.of(relative).isAbsolute() || !target.startsWith(root) || target.equals(root)) {
            throw new IOException("Managed path escapes bot root: " + relative);
        }
        rejectLinks(target);
        return target;
    }

    static Properties load(Path path) throws IOException {
        Properties result = new Properties();
        if (Files.exists(path)) {
            try (InputStream input = Files.newInputStream(path)) { result.load(input); }
        }
        return result;
    }

    static void save(Path path, Properties values) throws IOException {
        Path temporary = Files.createTempFile(path.getParent(), ".bridge-state-", ".tmp");
        try {
            try (OutputStream output = Files.newOutputStream(temporary)) { values.store(output, "Robocode bridge managed output"); }
            move(temporary, path);
        } finally { Files.deleteIfExists(temporary); }
    }

    static void move(Path from, Path to) throws IOException {
        try { Files.move(from, to, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
        catch (AtomicMoveNotSupportedException unsupported) { Files.move(from, to, StandardCopyOption.REPLACE_EXISTING); }
    }

    static void removeTree(Path root) throws IOException {
        if (!Files.exists(root, LinkOption.NOFOLLOW_LINKS)) return;
        try (var files = Files.walk(root)) {
            for (Path path : (Iterable<Path>) files.sorted(Comparator.reverseOrder())::iterator) Files.delete(path);
        }
    }

    static boolean dataFile(String relative) {
        for (Path segment : Path.of(relative)) if (segment.toString().endsWith(".data")) return true;
        return false;
    }
}

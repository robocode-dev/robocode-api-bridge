import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

/** Reads Classic settings without loading or starting the Classic engine. */
final class SourceDiscovery {
    private static final List<String> RUMBLES = List.of("roborumble", "meleerumble", "teamrumble", "twinduel");

    private SourceDiscovery() { }

    static List<Path> resolve(Path supplied) throws IOException {
        Path root = PreviewFiles.canonical(supplied);
        if (!Files.isDirectory(root)) throw new IOException("Robot directory does not exist: " + root);
        LinkedHashSet<Path> configured = new LinkedHashSet<>();
        boolean hasSettings = false;
        for (String rumble : RUMBLES) {
            Path config = root.resolve("roborumble").resolve(rumble + ".txt");
            if (!Files.isRegularFile(config)) continue;
            String value = PreviewFiles.load(config).getProperty("BOTSREP");
            if (value == null || value.isBlank()) continue;
            hasSettings = true;
            Path path = Path.of(value.trim());
            if (!path.isAbsolute()) path = root.resolve(path);
            path = PreviewFiles.canonical(path);
            if (!Files.isDirectory(path)) throw new IOException("BOTSREP in " + config + " points to a missing directory: " + path);
            configured.add(path);
        }
        if (hasSettings) return new ArrayList<>(configured);
        if (Files.isDirectory(root.resolve("robots"))) root = PreviewFiles.canonical(root.resolve("robots"));
        return List.of(root);
    }

    static List<Path> candidates(Path workingDirectory, Path userHome, String systemDrive) throws IOException {
        LinkedHashSet<Path> result = new LinkedHashSet<>();
        List<Path> locations = new ArrayList<>();
        locations.add(workingDirectory);
        locations.add(userHome.resolve("robocode"));
        if (systemDrive != null && !systemDrive.isBlank()) locations.add(Path.of(systemDrive + "/robocode"));
        LinkedHashSet<List<Path>> seenRoots = new LinkedHashSet<>();
        for (Path location : locations) {
            if (!recognizable(location)) continue;
            try {
                List<Path> roots = resolve(location);
                if (seenRoots.add(roots)) result.add(PreviewFiles.canonical(location));
            } catch (IOException invalidInstallation) {
                // Explicitly selecting this location reports its invalid BOTSREP; discovery keeps searching.
            }
        }
        return new ArrayList<>(result);
    }

    private static boolean recognizable(Path root) throws IOException {
        if (!Files.isDirectory(root)) return false;
        if (Files.isDirectory(root.resolve("roborumble")) || Files.isDirectory(root.resolve("robots"))) return true;
        try (var files = Files.list(root)) {
            return files.anyMatch(p -> Files.isRegularFile(p) && p.toString().toLowerCase(java.util.Locale.ROOT).endsWith(".jar"));
        }
    }

    static List<Path> remembered(Properties state) throws IOException {
        List<Path> roots = new ArrayList<>();
        int count;
        try { count = Integer.parseInt(state.getProperty("source.count", "0")); }
        catch (NumberFormatException invalid) { throw new IOException("Invalid source count in bridge state", invalid); }
        if (count < 0 || count > 1000) throw new IOException("Invalid source count in bridge state");
        for (int i = 0; i < count; i++) {
            String value = state.getProperty("source." + i);
            if (value == null) throw new IOException("Missing remembered source " + i);
            Path path = PreviewFiles.canonical(Path.of(value));
            if (!Files.isDirectory(path)) throw new IOException("Remembered source no longer exists: " + path + ". Supply its new location.");
            roots.add(path);
        }
        return roots;
    }
}

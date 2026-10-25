import java.io.Console;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import javax.tools.ToolProvider;

/** Entry point used by the distributable launchers; Main remains the developer/harness entry point. */
public final class PreviewMain {
    public static void main(String[] args) {
        System.exit(run(args, System.out, System.err));
    }

    static int run(String[] args, PrintStream out, PrintStream err) {
        try {
            Path source = null;
            Path output = Path.of(System.getProperty("user.home"), "RobocodeBridge", "bots");
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--help":
                        out.println("Usage: wrap-robots [<robot-directory-or-classic-home>] [--output <bot-directory>]");
                        out.println("       wrap-robots --version");
                        out.println("Requires JDK 11 or newer. Classic is not required. See guide/README.md.");
                        return 0;
                    case "--version":
                        Properties metadata = PreviewFiles.load(packageHome().resolve("preview.properties"));
                        out.println("Robocode API bridge " + metadata.getProperty("version", "development"));
                        out.println("Source revision: " + metadata.getProperty("revision", "unrecorded"));
                        out.println("Tank Royale minimum: " + metadata.getProperty("tankRoyale.minimum", "1.4.0")
                                + "; compatible major: " + metadata.getProperty("tankRoyale.major", "1"));
                        return 0;
                    case "--output":
                        if (++i == args.length) throw new IllegalArgumentException("--output requires a directory");
                        output = Path.of(args[i]);
                        break;
                    default:
                        if (args[i].startsWith("--") || source != null) throw new IllegalArgumentException("Unexpected argument: " + args[i]);
                        source = Path.of(args[i]);
                }
            }
            if (ToolProvider.getSystemJavaCompiler() == null) throw new IOException("A full JDK 11 or newer is required to run generated Wrapper.java files. Set JAVA_HOME to a JDK.");
            PreviewFiles.rejectLinks(output);
            output = PreviewFiles.canonical(output);
            Properties state = PreviewImporter.loadState(output);
            List<Path> roots;
            if (source != null) roots = SourceDiscovery.resolve(source);
            else {
                roots = SourceDiscovery.remembered(state);
                if (roots.isEmpty()) {
                    List<Path> candidates = SourceDiscovery.candidates(Path.of(""), Path.of(System.getProperty("user.home")), System.getenv("SystemDrive"));
                    if (candidates.size() == 1) {
                        out.println("Found robot source: " + candidates.get(0));
                        roots = SourceDiscovery.resolve(candidates.get(0));
                    } else {
                        Console console = System.console();
                        if (console == null) throw new IllegalArgumentException("No unambiguous robot source. Supply a robot directory or Classic installation as the first argument.");
                        for (int i = 0; i < candidates.size(); i++) out.println((i + 1) + ": " + candidates.get(i));
                        String selected = console.readLine("Robot directory or Classic installation%s: ", candidates.isEmpty() ? "" : " (or candidate number)");
                        if (selected == null || selected.isBlank()) throw new IllegalArgumentException("No robot source selected");
                        Path chosen;
                        if (selected.matches("[0-9]+")) {
                            int index = Integer.parseInt(selected) - 1;
                            if (index < 0 || index >= candidates.size()) throw new IllegalArgumentException("Invalid candidate number");
                            chosen = candidates.get(index);
                        } else chosen = Path.of(selected.trim());
                        roots = SourceDiscovery.resolve(chosen);
                    }
                }
            }
            out.println("Robot sources: " + roots);
            out.println("Tank Royale bot root: " + output);
            int failures = new PreviewImporter(packageHome(), output, out, err).importSources(roots);
            out.println("In Tank Royale: Config > Bot Root Directories > Add: " + output);
            return failures == 0 ? 0 : 1;
        } catch (IllegalArgumentException invalid) {
            err.println("wrap-robots: " + invalid.getMessage() + ". Use --help for usage.");
            return 2;
        } catch (Exception failure) {
            err.println("wrap-robots: " + failure.getMessage());
            return 1;
        }
    }

    static Path packageHome() throws Exception {
        Path code = Path.of(PreviewMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (!Files.isRegularFile(code)) throw new IOException("Use wrap-robots from an unpacked preview archive");
        Path home = code.getParent().getParent();
        if (!Files.isRegularFile(home.resolve("preview.properties"))) throw new IOException("Preview metadata is missing beside the tools directory. Unpack the complete archive again.");
        return home;
    }
}

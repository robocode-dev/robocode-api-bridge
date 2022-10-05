import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

// TODO: Handle robot.properties field `includeData` (boolean)

public class Main {

    static final String JAVA_WRAPPER = "Wrapper.java";

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide directory path as first argument, e.g. C:/robots");
            System.exit(-1);
        }

        String dirName = args[0];

        try (var files = Files.list(new File(dirName).toPath())) {
            files   .filter(path -> path.toString().toLowerCase().endsWith(".jar"))
                    .forEach(Main::processJar);
        }
    }

    static void processJar(Path path) {
        ZipEntry zipEntry;
        try {
            var jarFile = path.toFile();

            try (var zipFile = new ZipFile(jarFile)) {
                var zipStream = new ZipInputStream(new FileInputStream(jarFile));
                while ((zipEntry = zipStream.getNextEntry()) != null) {
                    var filename = zipEntry.getName();
                    if (filename.toLowerCase().endsWith(".properties")) {
                        var robot = processProperties(zipFile.getInputStream(zipEntry));
                        if (robot != null) {
                            createBotDir(path, jarFile.getName(), robot);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("IO exception occurred when processing " + path + ": " + ex.getMessage());
        }
    }

    static RobotProperties processProperties(InputStream is) throws IOException {
        var props = new Properties();
        props.load(is);

        var robotProps = new RobotProperties();
        robotProps.classname = props.getProperty("robot.classname");
        if (robotProps.classname == null || robotProps.classname.isEmpty()) {
            return null;
        }
        robotProps.version = props.getProperty("robot.version");
        robotProps.author = props.getProperty("robot.author.name");
        robotProps.description = props.getProperty("robot.description");
        robotProps.webpage = props.getProperty("robot.webpage");

        String includeDataStr = props.getProperty("robot.include.data");
        if (includeDataStr != null) {
            robotProps.includeData = Boolean.parseBoolean(includeDataStr);
            System.err.println("Include data is not supported yet: " + robotProps.name());
        }

        return robotProps;
    }

    static void createBotDir(Path dir, String jarFilename, RobotProperties robotProps) throws IOException {
        var robotClassAndVersion = robotProps.classname + "_" + robotProps.version;
        var botDir = dir.getParent().resolve(robotClassAndVersion);
        Files.createDirectories(botDir);

        createOrOverwriteFile(botDir, jarFilename); // create empty file with the jar name for info

        createJsonFile(botDir, robotProps);
        createJavaWrapper(botDir, robotProps.classname, robotClassAndVersion);
        createScriptFile(botDir, jarFilename, ':', ".sh");
        createScriptFile(botDir, jarFilename, ';', ".cmd");

        System.out.println((robotProps.classname + " " + robotProps.version).trim() + " (" + jarFilename + ")");
    }

    static void createJsonFile(Path botDir, RobotProperties robotProps) throws IOException {
        File file = createOrOverwriteFile(botDir, botDir.getFileName() + ".json");

        String author = robotProps.author;
        if (author == null || author.isBlank()) {
            author = robotProps.classname.substring(0, robotProps.classname.indexOf('.'));
        }

        try (var writer = new FileWriter(file)) {
            writer.write(
            "{\n" +
                "  \"name\": \"" + robotProps.name() + "\",\n" +
                "  \"version\": \"" + escape(replaceIfBlank(robotProps.version, "[n/a]")) + "\",\n" +
                "  \"authors\": \"" + escape(replaceIfBlank(author, "[n/a]")) + "\",\n" +
                "  \"description\": \"" + escape(replaceIfBlank(robotProps.description, "")) + "\",\n" +
                "  \"homepage\": \"" + escape(replaceIfBlank(robotProps.webpage, "")) + "\",\n" +
                "  \"platform\": \"JVM\"\n" +
                "}\n"
            );
        }
    }

    static void createJavaWrapper(Path botDir, String robotClass, String robotClassAndVersion) throws IOException {
        File file = createOrOverwriteFile(botDir, JAVA_WRAPPER);

        try (var writer = new FileWriter(file)) {
            writer.write(
            "import dev.robocode.tankroyale.bridge.BotPeer;\n" +
                "import dev.robocode.tankroyale.botapi.BotInfo;\n\n" +
                "public class Wrapper {\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tvar robot = new " + robotClass + "();\n" +
                "\t\tvar peer = new BotPeer(robot, BotInfo.fromFile(\"" + robotClassAndVersion + ".json\"));\n" +
                "\t\trobot.setPeer(peer);\n" +
                "\t\tpeer.start();\n" +
                "\t}\n" +
                "}"
            );
        }
    }

    static void createScriptFile(Path botDir, String jarFilename, char separator, String fileExt) throws IOException {
        File file = createOrOverwriteFile(botDir, botDir.getFileName() + fileExt);

        try (var writer = new FileWriter(file)) {
            String javaCommand = "java -cp ../lib/*" + separator + "../" + jarFilename + " " + JAVA_WRAPPER;
            if (fileExt.equalsIgnoreCase(".cmd")) {
                javaCommand += " >nul";
            }
            writer.write(javaCommand);
        }
        file.setExecutable(true);
    }

    static File createOrOverwriteFile(Path botDir, String filename) throws IOException {
        var path = botDir.resolve(filename);
        File file = path.toFile();
        file.delete();
        Files.createFile(path);
        return file;
    }

    static String replaceIfBlank(String str, String replacement) {
        return (str == null || str.isBlank()) ? replacement : str;
    }

    static String escape(String str) {
        return str
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
            .replace("\f", "\\f")
            .replace("\b", "\\b");
    }

    static class RobotProperties {
        String classname;
        String version;
        String author;
        String description;
        String webpage;
        Boolean includeData; // TODO

        String name() {
            return classname.substring(classname.lastIndexOf(".") + 1);
        }
    }
}
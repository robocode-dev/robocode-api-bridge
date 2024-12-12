import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

// TODO: Handle robot.properties field `includeData` (boolean)

import org.apache.bcel.classfile.ClassParser;

public class Main {

    static final String JAVA_WRAPPER = "Wrapper.java";

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please provide directory path as first argument, e.g. C:/robots");
            System.exit(-1);
        }

        Path dirPath = Path.of(args[0]);

        try (var stream = Files.list(dirPath)) {
            stream.filter(path -> path.toString().toLowerCase().endsWith(".jar"))
                    .forEach(Main::processJar);
        }

        try (var stream = Files.list(dirPath)) {
            stream.filter(path -> path.toString().toLowerCase().endsWith(".class"))
                    .forEach(Main::processClass);
        }
    }

    static void processJar(Path jarPath) {
        ZipEntry zipEntry;
        try {
            var jarFile = jarPath.toFile();

            try (var zipFile = new ZipFile(jarFile)) {
                var zipStream = new ZipInputStream(new FileInputStream(jarFile));
                while ((zipEntry = zipStream.getNextEntry()) != null) {
                    var filename = zipEntry.getName();
                    if (filename.toLowerCase().endsWith(".properties")) {
                        var robotProperties = processProperties(zipFile.getInputStream(zipEntry));
                        if (robotProperties != null) {
                            createBotDir(jarPath, jarFile.getName(), robotProperties);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("IO exception occurred when processing " + jarPath + ": " + ex.getMessage());
        }
    }

    static void processClass(Path classPath) {
        try {
            var baseFilename = toBaseFilename(classPath);
            var propertiesPath = classPath.getParent().resolve(baseFilename + ".properties");

            RobotProperties robotProperties;

            if (Files.exists(propertiesPath)) {
                try (InputStream inputStream = Files.newInputStream(propertiesPath)) {
                    robotProperties = processProperties(inputStream);
                }
            } else {
                robotProperties = new RobotProperties();
                robotProperties.classname = toFullyQualifiedClassName(classPath);
            }

            if (robotProperties != null) {
                createBotDir(classPath, baseFilename, robotProperties);
            }

        } catch (Exception ex) {
            System.err.println("IO exception occurred when processing " + classPath + ": " + ex.getMessage());
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

    static void createBotDir(Path botDirPath, String filename, RobotProperties robotProps) throws IOException {
        var className = robotProps.classname;

        var robotClassAndVersion = className;
        if (robotProps.version != null) {
            robotClassAndVersion += "_" + robotProps.version;
        }
        var botDir = botDirPath.getParent().resolve(robotClassAndVersion);
        Files.createDirectories(botDir);

        createJsonFile(botDir, robotProps);
        createJavaWrapper(botDir, className, robotClassAndVersion);
        createScriptFile(botDir, filename, ':', ".sh");
        createScriptFile(botDir, filename, ';', ".cmd");

        var version = robotProps.version;
        if (version == null) {
            version = "";
        }
        System.out.println((className + " " + version).trim() + " (" + filename + ")");
    }

    static void createJsonFile(Path botDir, RobotProperties robotProps) throws IOException {
        File file = createOrOverwriteFile(botDir, botDir.getFileName() + ".json");

        String author = robotProps.author;
        if (author == null || author.isBlank()) {
            author = robotProps.classname.substring(0, robotProps.classname.indexOf('.'));
        }

        try (var writer = new FileWriter(file)) {
            writer.write("{\n" +
                    "  \"name\": \"" + robotProps.name() + "\",\n" +
                    "  \"version\": \"" + escape(replaceIfBlank(robotProps.version, "[n/a]")) + "\",\n" +
                    "  \"authors\": [\"" + escape(replaceIfBlank(author, "[n/a]")) + "\"],\n" +
                    "  \"description\": \"" + escape(replaceIfBlank(robotProps.description, "")) + "\",\n" +
                    "  \"homepage\": \"" + escape(replaceIfBlank(robotProps.webpage, "")) + "\",\n" +
                    "  \"platform\": \"JVM\"\n" +
                    "  \"language\": \"Java\"\n" +
                    "}\n"
            );
        }
    }

    static void createJavaWrapper(Path botDir, String robotClass, String robotClassAndVersion) throws IOException {
        File file = createOrOverwriteFile(botDir, JAVA_WRAPPER);

        try (var writer = new FileWriter(file)) {
            writer.write("import dev.robocode.tankroyale.bridge.BotPeer;\n" +
                    "import dev.robocode.tankroyale.botapi.BotInfo;\n" +
                    "import robocode.robotinterfaces.IBasicRobot;\n\n" +
                    "public class Wrapper {\n" +
                    "\tpublic static void main(String[] args) throws Exception {\n" +
                    "\t\tvar robot = (IBasicRobot)Class.forName(\"" + robotClass + "\").getDeclaredConstructor().newInstance();\n" +
                    "\t\tvar peer = new BotPeer(robot, BotInfo.fromFile(\"" + robotClassAndVersion + ".json\"));\n" +
                    "\t\trobot.setPeer(peer);\n" +
                    "\t\tpeer.start();\n" +
                    "\t}\n" +
                    "}"
            );
        }
    }

    static void createScriptFile(Path botDir, String filename, char separator, String fileExt) throws IOException {
        File file = createOrOverwriteFile(botDir, botDir.getFileName() + fileExt);

        try (var writer = new FileWriter(file)) {
            String javaCommand = "java -cp ." + separator + ".." + separator + "../.." + separator + "../lib/*" + separator + "../" + filename + " " + JAVA_WRAPPER;
            if (fileExt.equalsIgnoreCase(".cmd")) {
                javaCommand += " >nul"; // to avoid the process to become unresponsive
            }
            writer.write(javaCommand);
        }
        if (!file.setExecutable(true)) {
            System.err.println("Could not set the file " + filename + " as executable");
        }
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

    static String toBaseFilename(Path filePath) {
        return filePath.getFileName().toString().split("\\.(?=[^.]+$)")[0];
    }

    static String toFullyQualifiedClassName(Path classPath) throws IOException {
        var classParser = new ClassParser(classPath.getFileName().toString());
        var javaClass = classParser.parse();
        return javaClass.getClassName();
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

// TODO: Handle robot.properties field `includeData` (boolean)

public class MakeWrappers {

    static final String JAVA_WRAPPER = "Wrapper.java";

    public static void main(String[] args) throws IOException {
        /*
        String dirName = "C:/Robots robocode";

        try (var files = Files.list(new File(dirName).toPath())) {
            files.limit(1)
                    .filter(path -> path.toString().toLowerCase().endsWith(".jar"))
                    .forEach(MakeWrappers::processJar);
        }*/
        processJar(Paths.get("C:/Robots robocode/acid.Bl4ck_1.0.jar"));
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
            System.out.println("IO exception occurred when processing: " + path);
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
        var botDir = dir.getParent().resolve(robotProps.classname);
        Files.createDirectories(botDir);

        createJsonFile(botDir, robotProps);
        createJavaWrapper(botDir, robotProps.classname);
        createScriptFile(botDir, jarFilename, robotProps.classname, ':', ".sh");
        createScriptFile(botDir, jarFilename, robotProps.classname, ';', ".cmd");

        System.out.println((robotProps.classname + " " + robotProps.version).trim());
    }

    static void createJsonFile(Path botDir, RobotProperties robotProps) throws IOException {
        Path path = Files.createFile(botDir.resolve(robotProps.classname + ".json"));

        try (var out = new OutputStreamWriter(new FileOutputStream(path.toFile()))) {
            out.write(
            "{\n" +
                "  \"name\": \"" + robotProps.name() + "\",\n" +
                "  \"version\": \"" + replaceIfBlank(robotProps.version, "[n/a]") + "\",\n" +
                "  \"authors\": \"" + replaceIfBlank(robotProps.author, "[n/a]") + "\",\n" +
                "  \"description\": \"" + replaceIfBlank(robotProps.description, "") + "\",\n" +
                "  \"webpage\": \"" + replaceIfBlank(robotProps.webpage, "") + "\",\n" +
                "  \"gameTypes\": \"classic, melee, 1v1\",\n" +
                "  \"platform\": \"JVM\"\n" +
                "}\n"
            );
        }
    }

    static void createJavaWrapper(Path botDir, String robotClass) throws IOException {
        Path path = Files.createFile(botDir.resolve(JAVA_WRAPPER));

        try (var out = new OutputStreamWriter(new FileOutputStream(path.toFile()))) {
            out.write(
            "import dev.robocode.tankroyale.bridge.BotPeer;\n" +
                "import dev.robocode.tankroyale.botapi.BotInfo;\n\n" +
                "public class Wrapper {\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tvar robot = new " + robotClass + "();\n" +
                "\t\tvar peer = new BotPeer(robot, BotInfo.fromFile(\"" + robotClass + ".json\"));\n" +
                "\t\trobot.setPeer(peer);\n" +
                "\t\tpeer.start();\n" +
                "\t}\n" +
                "}"
            );
        }
    }

    static void createScriptFile(Path botDir, String jarFilename, String robotClass, char separator, String fileExt) throws IOException {
        Path path = Files.createFile(botDir.resolve(robotClass + fileExt));
        File file = path.toFile();

        try (var out = new OutputStreamWriter(new FileOutputStream(file))) {
            out.write("java -cp ../lib/*" + separator + "../" + jarFilename + " " + JAVA_WRAPPER);
        }
        file.setExecutable(true);
    }

    static String replaceIfBlank(String str, String replacement) {
        return (str == null || str.isBlank()) ? replacement : str;
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


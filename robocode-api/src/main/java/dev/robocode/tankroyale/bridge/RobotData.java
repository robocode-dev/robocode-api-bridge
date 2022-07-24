package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RobotData {

    private static final Path dataDirPath;

    static {
        dataDirPath = Paths.get("").resolve(RobotName.getName() + ".data");
        try {
            Files.createDirectories(dataDirPath);
        } catch (IOException e) {
            throw new BotException("Could not create data directory: " + dataDirPath);
        }
    }

    public static File getDataFile(String filename) {
        return dataDirPath.resolve(filename).toAbsolutePath().toFile();
    }
}

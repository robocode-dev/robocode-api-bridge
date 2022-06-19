package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import robocode.RobotStatus;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcRadians;

final class IBotToRobotStatusMapper {

    public static robocode.RobotStatus map(IBot bot) {
        return new RobotStatus(
                bot.getEnergy(),
                bot.getX(),
                bot.getY(),
                toRcRadians(bot.getDirection()),
                toRcRadians(bot.getGunDirection()),
                toRcRadians(bot.getRadarDirection()),
                toRcRadians(bot.getSpeed()),
                bot.getTurnRemaining(),
                bot.getGunTurnRemaining(),
                bot.getRadarTurnRemaining(),
                bot.getDistanceRemaining(),
                bot.getGunHeat(),
                bot.getEnemyCount(),
                0, // numSentries, not supported
                bot.getRoundNumber(),
                bot.getNumberOfRounds(),
                bot.getTurnNumber()
        );
    }
}
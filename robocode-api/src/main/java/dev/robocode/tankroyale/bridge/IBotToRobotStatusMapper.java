package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import robocode.RobotStatus;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRcHeadingRad;
import static java.lang.Math.toRadians;

final class IBotToRobotStatusMapper {

    public static robocode.RobotStatus map(IBot bot) {
        return new RobotStatus(
                bot.getEnergy(),
                bot.getX(),
                bot.getY(),
                toRcHeadingRad(bot.getDirection()),
                toRcHeadingRad(bot.getGunDirection()),
                toRcHeadingRad(bot.getRadarDirection()),
                bot.getSpeed(),
                toRadians(bot.getTurnRemaining()),
                toRadians(bot.getGunTurnRemaining()),
                toRadians(bot.getRadarTurnRemaining()),
                bot.getDistanceRemaining(),
                bot.getGunHeat(),
                bot.getEnemyCount(),
                0, // numSentries, not supported
                bot.getRoundNumber() - 1,
                bot.getNumberOfRounds(),
                bot.getTurnNumber()
        );
    }
}
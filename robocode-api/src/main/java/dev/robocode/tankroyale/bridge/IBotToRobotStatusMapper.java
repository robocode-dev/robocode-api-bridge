package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import robocode.RobotStatus;

import static dev.robocode.tankroyale.bridge.AngleConverter.toRobocodeHeadingRad;
import static java.lang.Math.toRadians;

final class IBotToRobotStatusMapper {

    public static robocode.RobotStatus map(IBot bot) {
        return new RobotStatus(
                bot.getEnergy(),
                bot.getX(),
                bot.getY(),
                toRobocodeHeadingRad(bot.getDirection()),
                toRobocodeHeadingRad(bot.getGunDirection()),
                toRobocodeHeadingRad(bot.getRadarDirection()),
                bot.getSpeed(),
                toRadians(bot.getTurnRemaining()),
                // RobotStatus takes radar before gun here, unlike the body/gun/radar order it
                // uses for the headings above. Classic's constructor is the same shape, and
                // ARCH-002 fixes the signature, so the argument order is what has to bend.
                toRadians(bot.getRadarTurnRemaining()),
                toRadians(bot.getGunTurnRemaining()),
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
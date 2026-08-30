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
                // Negated, because the engines disagree about which way is positive: Tank
                // Royale reports a positive turn remaining when turning left, Robocode when
                // turning right. A remainder is a signed rotation, so crossing the frame
                // flips it -- unlike a command such as turnRight, which names its direction.
                // BotPeer's own getters negate; this path did not, so the same quantity had
                // opposite signs depending on whether a robot read it directly or from its
                // status (AN-007).
                //
                // RobotStatus also takes radar before gun here, unlike the body/gun/radar
                // order it uses for the headings above. Classic's constructor is the same
                // shape, and ARCH-002 fixes the signature, so the argument order bends.
                -toRadians(bot.getTurnRemaining()),
                -toRadians(bot.getRadarTurnRemaining()),
                -toRadians(bot.getGunTurnRemaining()),
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
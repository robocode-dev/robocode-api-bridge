package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;
import robocode.Rules;

public class JuniorRobotImpl {

    public static void turnAndMove(IBot bot, double distance, double degrees) {
        if (distance == 0) {
            bot.turnRight(degrees);
            return;
        }

        // Save current max. velocity and max. turn rate, so they can be restored
        final double savedMaxVelocity = bot.getMaxSpeed();
        final double savedMaxTurnRate = bot.getMaxTurnRate();

        final double absDegrees = Math.abs(degrees);
        final double absDistance = Math.abs(distance);

        /* -- Calculate max. velocity for moving perfect in a circle --
         *
         * maxTurnRate = 10 * 0.75 * abs(velocity)  (Robocode rule), and
         * maxTurnRate = velocity * degrees / distance  (curve turn rate)
         *
         * Hence, max. velocity = 10 / (degrees / distance + 0.75)
         */
        final double maxVelocity = Math.min(Rules.MAX_VELOCITY, 10 / (absDegrees / absDistance + 0.75));

        // -- Calculate number of turns for acceleration + deceleration --

        double accDist = 0; // accumulated distance during acceleration
        double decDist = 0; // accumulated distance during deceleration

        int turns = 0; // number of turns to it will take to move the distance

        // Calculate the amount of turn it will take to accelerate + decelerate
        // up to the max. velocity, but stop if the distance for used for
        // acceleration + deceleration gets bigger than the total distance to move
        for (int t = 1; t < maxVelocity; t++) {

            // Add the current velocity to the acceleration distance
            accDist += t;

            // Every 2nd time we add the deceleration distance needed to
            // get to a velocity of 0
            if (t > 2 && (t % 2) > 0) {
                decDist += t - 2;
            }

            // Stop if the acceleration + deceleration > total distance to move
            if ((accDist + decDist) >= absDistance) {
                break;
            }

            // Increment turn for acceleration
            turns++;

            // Every 2nd time we increment time for deceleration
            if (t > 2 && (t % 2) > 0) {
                turns++;
            }
        }

        // Add number of turns for the remaining distance at max velocity
        if ((accDist + decDist) < absDistance) {
            turns += (int) ((absDistance - accDist - decDist) / maxVelocity + 1);
        }

        // -- Move and turn in a curve --

        // Set the calculated max. velocity
        bot.setMaxSpeed(maxVelocity);

        // Set the robot to move the specified distance
        bot.setForward(distance);

        // Set the robot to turn its body to the specified amount of radians
        bot.setTurnRight(degrees);

        // Loop through the number of turns it will take to move the distance and adjust
        // the max. turn rate, so it fit the current velocity of the robot
        for (int t = turns; t >= 0; t--) {
            bot.setMaxTurnRate(degrees * bot.getSpeed() / absDistance); // getSpeed() changes from turn to turn
            bot.go(); // Perform next turn
        }
        // Stop movement
        bot.setTurnRight(0);
        bot.setForward(0);

        // Restore the saved max. velocity and max. turn rate
        bot.setMaxSpeed(savedMaxVelocity);
        bot.setMaxTurnRate(savedMaxTurnRate);

        // Execute
        bot.go();
    }
}

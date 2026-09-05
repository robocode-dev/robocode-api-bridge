package dev.robocode.tankroyale.bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.peer.IBasicRobotPeer;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit coverage for {@link BotPeer#isDroidRobot}, the decision {@code createBotImpl} uses to
 * pick a Tank Royale {@code Droid}-marked bot. Tested directly rather than through a battle
 * connection: Tank Royale reads droid status from the connecting bot's own type, so getting
 * this one-line check wrong would misreport every droid team member's scanner status.
 */
class BotPeerDroidSelectionTest {

    private static class MinimalRobot implements IBasicRobot {
        @Override
        public Runnable getRobotRunnable() {
            return null;
        }

        @Override
        public IBasicEvents getBasicEventListener() {
            return null;
        }

        @Override
        public void setPeer(IBasicRobotPeer peer) {
        }

        @Override
        public void setOut(PrintStream out) {
        }
    }

    private static final class DroidRobot extends MinimalRobot implements robocode.Droid {
    }

    @Test
    @DisplayName("a robocode.Droid-implementing robot is treated as a droid")
    void testIsDroidRobot_True_ForADroidImplementingRobot() {
        assertTrue(BotPeer.isDroidRobot(new DroidRobot()));
    }

    @Test
    @DisplayName("an ordinary robot is not treated as a droid")
    void testIsDroidRobot_False_ForAnOrdinaryRobot() {
        assertFalse(BotPeer.isDroidRobot(new MinimalRobot()));
    }
}

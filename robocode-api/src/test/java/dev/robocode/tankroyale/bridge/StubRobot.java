package dev.robocode.tankroyale.bridge;

import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IAdvancedRobot;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.peer.IBasicRobotPeer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * The robot side of a routing test: enough of an {@link IAdvancedRobot} for {@link BotPeer}
 * to construct, and a record of which event handlers were entered.
 * <p>
 * Advanced rather than basic because the peer resolves an advanced listener when the robot is
 * one and substitutes an adaptor otherwise. Using the advanced shape means a test can assert
 * on handlers that only exist there, and the basic case is the one the peer already covers by
 * substitution.
 */
final class StubRobot implements IAdvancedRobot {

    private final List<String> handlerCalls = new ArrayList<>();
    private final IBasicEvents basicEvents;
    private final IAdvancedEvents advancedEvents;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    StubRobot() {
        basicEvents = (IBasicEvents) recordingListener(IBasicEvents.class);
        advancedEvents = (IAdvancedEvents) recordingListener(IAdvancedEvents.class);
    }

    /** Names of the event handlers the peer entered, in order. */
    List<String> handlerCalls() {
        return List.copyOf(handlerCalls);
    }

    String printed() {
        return out.toString();
    }

    private Object recordingListener(Class<?> type) {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type },
                (proxy, method, args) -> {
                    handlerCalls.add(method.getName());
                    Class<?> returns = method.getReturnType();
                    if (returns == double.class) return 0.0;
                    if (returns == int.class) return 0;
                    if (returns == boolean.class) return false;
                    return null;
                });
    }

    @Override
    public Runnable getRobotRunnable() {
        return () -> { };
    }

    @Override
    public IBasicEvents getBasicEventListener() {
        return basicEvents;
    }

    @Override
    public IAdvancedEvents getAdvancedEventListener() {
        return advancedEvents;
    }

    @Override
    public void setPeer(IBasicRobotPeer peer) {
        // The peer hands itself to the robot; a routing test drives the peer directly.
    }

    @Override
    public void setOut(PrintStream printStream) {
        // Ignored: the peer redirects the robot's output to the process stdout, and a test
        // reading it back would be asserting on Tank Royale's stream rather than on routing.
    }
}

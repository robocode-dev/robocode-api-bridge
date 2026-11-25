package conformance.blockingteams;

import robocode.Condition;
import robocode.StatusEvent;
import robocode.TeamRobot;
import java.io.IOException;

public class BlockingSender extends TeamRobot {
    private boolean moving;
    private boolean handlerSent;
    private long movementStart;

    private void broadcast(String phase) {
        try {
            broadcastMessage(phase + "@" + getTime());
        } catch (IOException exception) {
            out.println("BlockingMessageError:" + exception);
        }
    }

    @Override
    public void run() {
        // Let every member subscribe before measuring delivery, as in TeamLeader.
        turnRadarRight(360);
        movementStart = getTime();
        moving = true;
        handlerSent = false;
        broadcast("move");
        // No explicit execute: status handlers must be able to send during movement.
        while (getTime() < movementStart + 12) {
            ahead(100);
            back(100);
        }
        moving = false;
        broadcast("body");
        turnRight(90);
        broadcast("gun");
        turnGunRight(90);
        broadcast("radar");
        turnRadarRight(90);
        broadcast("wait");
        final long end = getTime() + 3;
        waitFor(new Condition() {
            @Override public boolean test() { return getTime() >= end; }
        });
        broadcast("execute");
        execute();
        while (true) {
            ahead(100);
            back(100);
        }
    }

    @Override
    public void onStatus(StatusEvent event) {
        if (moving && !handlerSent && getTime() >= movementStart + 5) {
            handlerSent = true;
            broadcast("handler-a");
            try {
                sendMessage(getTeammates()[0], "handler-b@" + getTime());
            } catch (IOException exception) {
                out.println("BlockingMessageError:" + exception);
            }
        }
    }
}

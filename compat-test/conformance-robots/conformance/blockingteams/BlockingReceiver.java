package conformance.blockingteams;

import robocode.Droid;
import robocode.MessageEvent;
import robocode.TeamRobot;

public class BlockingReceiver extends TeamRobot implements Droid {
    private static final String[] ORDER = {
            "move", "handler-a", "handler-b", "body", "gun", "radar", "wait", "execute"
    };
    private int next;

    @Override
    public void run() {
        next = 0;
        while (true) execute();
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        String[] fields = String.valueOf(event.getMessage()).split("@");
        if (fields.length != 2 || next >= ORDER.length || !ORDER[next].equals(fields[0])) {
            out.println("BlockingMessageError:order:" + event.getMessage());
            return;
        }
        next++;
        long sent = Long.parseLong(fields[1]);
        if (getTime() != sent + 1 || event.getTime() != sent + 1 || !isTeammate(event.getSender())) {
            out.println("BlockingMessageError:delivery:" + event.getMessage()
                    + ":time=" + getTime() + ":event=" + event.getTime());
            return;
        }
        out.println("BlockingMessageOk:" + fields[0] + ":next-turn");
    }
}

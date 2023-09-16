package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.events.TeamMessageEvent;
import robocode.MessageEvent;

import java.io.Serializable;

final class MessageEventMapper {

    public static MessageEvent map(TeamMessageEvent teamMessageEvent) {
        var sender = String.valueOf(teamMessageEvent.getSenderId());

        var message = teamMessageEvent.getMessage();
        if (!(message instanceof Serializable)) {
            throw new IllegalStateException("Team messages in Robocode is expected to implement the Serializable interface");
        }
        return new MessageEvent(sender, (Serializable)message);
    }
}

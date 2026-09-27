package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;
import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.events.TeamMessageEvent;
import robocode.MessageEvent;

import java.io.Serializable;

final class MessageEventMapper {

    public static MessageEvent map(TeamMessageEvent teamMessageEvent, IBot bot) {
        var sender = TankRoyaleBotNameResolver.getNameOrId(bot, teamMessageEvent.getSenderId());

        var message = teamMessageEvent.getMessage();
        if (message instanceof BridgeTeamMessage) {
            message = ((BridgeTeamMessage) message).decode();
        }
        if (!(message instanceof Serializable)) {
            throw new BotException("MessageEventMapper.map: Team messages in Robocode is expected to implement the Serializable interface");
        }
        return new MessageEvent(sender, (Serializable)message);
    }
}

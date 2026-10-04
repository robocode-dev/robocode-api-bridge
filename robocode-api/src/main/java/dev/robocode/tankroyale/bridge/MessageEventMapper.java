package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;
import dev.robocode.tankroyale.botapi.IBot;
import dev.robocode.tankroyale.botapi.TeamMessageBatch;
import dev.robocode.tankroyale.botapi.events.TeamMessageEvent;
import robocode.MessageEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final class MessageEventMapper {

    public static List<MessageEvent> map(TeamMessageEvent teamMessageEvent, IBot bot) {
        var sender = TankRoyaleBotNameResolver.getNameOrId(bot, teamMessageEvent.getSenderId());

        Object payload = teamMessageEvent.getMessage();
        List<?> payloads = payload instanceof TeamMessageBatch
                ? ((TeamMessageBatch) payload).getMessages()
                : List.of(payload);

        var events = new ArrayList<MessageEvent>(payloads.size());
        for (Object item : payloads) {
            Object message = item instanceof BridgeTeamMessage
                    ? ((BridgeTeamMessage) item).decode()
                    : item;
            if (message instanceof BridgeTeamMessage.RoutedMessage) {
                var routed = (BridgeTeamMessage.RoutedMessage) message;
                if (routed.getRecipientId() != bot.getMyId()) {
                    continue;
                }
                message = routed.getMessage();
            }
            if (!(message instanceof Serializable)) {
                throw new BotException("MessageEventMapper.map: Team messages in Robocode is expected to implement the Serializable interface");
            }
            events.add(new MessageEvent(sender, (Serializable) message));
        }
        return events;
    }
}

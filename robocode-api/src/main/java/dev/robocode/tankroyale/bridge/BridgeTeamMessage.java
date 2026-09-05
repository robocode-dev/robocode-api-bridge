package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * Carries a classic Serializable team message through Tank Royale's JSON-only message payload.
 *
 * <p>Tank Royale's Gson instance cannot reflect into some JDK-owned message classes, such as
 * {@code java.awt.Color}, on a strongly encapsulated JDK. Classic team messages are Java
 * serialized values, so preserving that representation here keeps the frozen API's message
 * type and object value semantics without asking the Bot API's Gson to inspect the object.
 */
public final class BridgeTeamMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String payload;

    /** Required by Gson when a teammate receives the bridge envelope. */
    public BridgeTeamMessage() {
    }

    private BridgeTeamMessage(String payload) {
        this.payload = payload;
    }

    /** Returns a Gson-safe transport value while keeping simple messages unchanged. */
    public static Serializable forTransport(Serializable message) {
        if (message instanceof String || message instanceof Number || message instanceof Boolean
                || message instanceof Character || message instanceof Enum) {
            return message;
        }
        return encode(message);
    }

    /** Encodes one classic team message in a Gson-safe field. */
    public static BridgeTeamMessage encode(Serializable message) {
        try {
            var bytes = new ByteArrayOutputStream();
            try (var output = new ObjectOutputStream(bytes)) {
                output.writeObject(message);
            }
            return new BridgeTeamMessage(Base64.getEncoder().encodeToString(bytes.toByteArray()));
        } catch (IOException exception) {
            throw new BotException("Could not serialize team message: " + exception.getMessage());
        }
    }

    /** Restores the original classic message object. */
    public Serializable decode() {
        if (payload == null) {
            throw new BotException("Could not deserialize empty team message");
        }
        try {
            var bytes = Base64.getDecoder().decode(payload);
            try (var input = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
                return (Serializable) input.readObject();
            }
        } catch (IOException | ClassNotFoundException | ClassCastException exception) {
            throw new BotException("Could not deserialize team message: " + exception.getMessage());
        }
    }
}

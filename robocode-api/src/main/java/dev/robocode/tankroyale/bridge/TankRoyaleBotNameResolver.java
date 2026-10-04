package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotException;

import java.lang.reflect.InvocationTargetException;

/** Reads the optional name map added to newer Tank Royale Bot APIs without breaking older ones. */
final class TankRoyaleBotNameResolver {

    private TankRoyaleBotNameResolver() { }

    static String getName(Object bot, int botId) {
        if (bot == null) return null;
        final var method = getMethod(bot);
        if (method == null) return null;
        try {
            return (String) method.invoke(bot, botId);
        } catch (IllegalAccessException e) {
            throw new BotException("Could not read the Tank Royale bot-name map");
        } catch (InvocationTargetException e) {
            var cause = e.getCause();
            if (cause instanceof BotException) return null;
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            throw new BotException("Could not read the Tank Royale bot-name map");
        }
    }

    static String getNameOrId(Object bot, int botId) {
        var name = getName(bot, botId);
        return (name != null ? name : String.valueOf(botId)).intern();
    }

    private static java.lang.reflect.Method getMethod(Object bot) {
        try {
            return bot.getClass().getMethod("getBotName", int.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}

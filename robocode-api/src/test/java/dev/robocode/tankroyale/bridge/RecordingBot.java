package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.IBot;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An {@link IBot} that records every call made to it and answers canned values for the
 * getters.
 * <p>
 * This is what makes call routing observable. Almost everything {@link BotPeer} does is
 * translate a robot's call into a Bot API call, and that translation is only visible at the
 * boundary between the two — which, in a real battle, sits behind a WebSocket and reports
 * back as a score some minutes later.
 * <p>
 * Built on a dynamic proxy rather than a hand-written stub because {@code IBot} is wide and
 * still growing. A stub would need a new method body every time the Bot API gains one, and
 * the compiler error that forces it would arrive as an obstacle rather than as information.
 * The proxy answers anything, so the test that notices a new method is the coverage check,
 * which is where that news belongs.
 */
final class RecordingBot {

    /** One call the peer made, with the arguments it passed. */
    static final class Call {
        final String name;
        final Object[] args;

        Call(String name, Object[] args) {
            this.name = name;
            this.args = args == null ? new Object[0] : args;
        }

        double doubleArg(int index) {
            return ((Number) args[index]).doubleValue();
        }

        @Override
        public String toString() {
            return name + Arrays.toString(args);
        }
    }

    private final List<Call> calls = new ArrayList<>();
    private final Map<String, Object> canned = new HashMap<>();
    private final IBot proxy;

    private RecordingBot() {
        proxy = (IBot) Proxy.newProxyInstance(
                IBot.class.getClassLoader(),
                new Class<?>[] { IBot.class },
                (p, method, args) -> {
                    calls.add(new Call(method.getName(), args));
                    return answer(method);
                });
    }

    static RecordingBot create() {
        return new RecordingBot();
    }

    /** Sets the value a named getter returns, e.g. {@code returning("getEnergy", 42.0)}. */
    RecordingBot returning(String method, Object value) {
        canned.put(method, value);
        return this;
    }

    IBot asBot() {
        return proxy;
    }

    /** Every call made so far, in order. */
    List<Call> calls() {
        return List.copyOf(calls);
    }

    /** Forgets what has been recorded, so a test can act and then assert on that alone. */
    RecordingBot clear() {
        calls.clear();
        return this;
    }

    /** True when the peer called this Bot API method at least once. */
    boolean called(String method) {
        return calls.stream().anyMatch(c -> c.name.equals(method));
    }

    /**
     * The single call to the named method.
     *
     * @throws AssertionError when it was never called, or called more than once — both are
     *                        routing faults worth failing on rather than papering over by
     *                        taking the first match.
     */
    Call onlyCall(String method) {
        List<Call> matching = calls.stream()
                .filter(c -> c.name.equals(method))
                .collect(Collectors.toList());
        if (matching.isEmpty()) {
            throw new AssertionError("expected a call to " + method + ", but saw: " + names());
        }
        if (matching.size() > 1) {
            throw new AssertionError(
                    "expected exactly one call to " + method + ", but saw " + matching.size()
                            + ": " + matching);
        }
        return matching.get(0);
    }

    /** The names of every recorded call, for an assertion message that says what happened. */
    String names() {
        return calls.isEmpty() ? "(no calls)"
                : calls.stream().map(c -> c.name).collect(Collectors.joining(", "));
    }

    private Object answer(Method method) {
        Object value = canned.get(method.getName());
        if (value != null) {
            return value;
        }
        Class<?> type = method.getReturnType();
        if (type == double.class) return 0.0;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == boolean.class) return false;
        if (type == String.class) return "";
        if (type == java.util.List.class) return List.of();
        if (type == java.util.Set.class) return java.util.Set.of();
        if (type == java.util.Collection.class) return List.of();
        return null;
    }
}

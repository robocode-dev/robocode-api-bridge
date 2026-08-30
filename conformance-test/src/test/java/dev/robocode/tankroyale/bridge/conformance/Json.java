package dev.robocode.tankroyale.bridge.conformance;

import java.util.ArrayList;
import java.util.List;

/**
 * Just enough JSON reading for the harness's result object.
 *
 * Deliberately not a library. The tier adds no dependency it does not need, the shape it
 * reads is produced by a script in this repository, and the alternative -- pulling a JSON
 * parser onto a test classpath that also has to stay clear of two engines' own jars -- buys
 * nothing here.
 */
final class Json {

    private Json() {
    }

    /** The value of a scalar field, unquoted, or null when the field is absent. */
    static String scalar(String json, String field) {
        int at = indexOfKey(json, field);
        if (at < 0) {
            return null;
        }
        int cursor = at;
        while (cursor < json.length() && json.charAt(cursor) != ':') {
            cursor++;
        }
        cursor++;
        while (cursor < json.length() && Character.isWhitespace(json.charAt(cursor))) {
            cursor++;
        }
        if (cursor >= json.length()) {
            return null;
        }
        if (json.charAt(cursor) == '"') {
            StringBuilder value = new StringBuilder();
            readString(json, cursor, value);
            return value.toString();
        }
        int end = cursor;
        while (end < json.length() && ",}]".indexOf(json.charAt(end)) < 0) {
            end++;
        }
        return json.substring(cursor, end).trim();
    }

    /** The elements of an array of strings, or an empty list when the field is absent. */
    static List<String> stringArray(String json, String field) {
        List<String> values = new ArrayList<>();
        int at = indexOfKey(json, field);
        if (at < 0) {
            return values;
        }
        int cursor = json.indexOf('[', at);
        if (cursor < 0) {
            return values;
        }
        cursor++;
        while (cursor < json.length()) {
            char c = json.charAt(cursor);
            if (c == ']') {
                break;
            }
            if (c == '"') {
                StringBuilder value = new StringBuilder();
                cursor = readString(json, cursor, value);
                values.add(value.toString());
                continue;
            }
            cursor++;
        }
        return values;
    }

    /**
     * Reads a quoted string starting at {@code start}, appending the unescaped content to
     * {@code out}. Returns the index just past the closing quote.
     */
    private static int readString(String json, int start, StringBuilder out) {
        int cursor = start + 1; // skip the opening quote
        while (cursor < json.length()) {
            char c = json.charAt(cursor);
            if (c == '\\' && cursor + 1 < json.length()) {
                char escaped = json.charAt(cursor + 1);
                switch (escaped) {
                    case 'n': out.append('\n'); break;
                    case 'r': out.append('\r'); break;
                    case 't': out.append('\t'); break;
                    case 'b': out.append('\b'); break;
                    case 'f': out.append('\f'); break;
                    case 'u':
                        if (cursor + 5 < json.length()) {
                            out.append((char) Integer.parseInt(json.substring(cursor + 2, cursor + 6), 16));
                            cursor += 4;
                        }
                        break;
                    default: out.append(escaped); break;
                }
                cursor += 2;
                continue;
            }
            if (c == '"') {
                return cursor + 1;
            }
            out.append(c);
            cursor++;
        }
        return cursor;
    }

    /** Finds a top-level-ish key by name, matching the quoted key rather than any substring. */
    private static int indexOfKey(String json, String field) {
        return json.indexOf('"' + field + '"');
    }
}

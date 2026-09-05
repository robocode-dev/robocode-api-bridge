package conformance.teams;

import java.awt.Color;
import java.io.Serializable;

/** Serializable payload with a JDK-owned field, exercising the bridge message envelope. */
final class TeamPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String marker;
    private final Color accent;

    TeamPayload(String marker) {
        this.marker = marker;
        this.accent = new Color(12, 34, 56);
    }

    @Override
    public String toString() {
        return marker;
    }
}

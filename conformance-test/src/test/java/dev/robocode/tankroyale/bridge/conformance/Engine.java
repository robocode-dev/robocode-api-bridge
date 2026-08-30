package dev.robocode.tankroyale.bridge.conformance;

/** The two engines a conformance expectation is asserted against. */
enum Engine {

    /** Classic Robocode, driven through its Control API. The specification (G-001). */
    CLASSIC("rc", "classic Robocode"),

    /** Tank Royale running the robot through the bridge. */
    BRIDGE("tr", "Tank Royale via the bridge");

    private final String harnessName;
    private final String description;

    Engine(String harnessName, String description) {
        this.harnessName = harnessName;
        this.description = description;
    }

    String harnessName() {
        return harnessName;
    }

    @Override
    public String toString() {
        return description;
    }
}

# Robocode API adapter library

## About the adapter library

This is an adapter library for the [Robocode API] of the original Robocode allowing robots developed for the original
Robocode to run with Robocode Tank Royale.

The generated library (`robocode-api-x.y.z.jar`) is a replacement for the original `robocode.jar` library.
Robocode robot developers can use this library to code against the original Robocode API, but run the robot in Tank
Royale.

The `robocode` and `gl4java` packages should be considered and kept _read only_ as these are taken from the original
Robocode API sources. The sources for the adapter are kept entirely within the `dev.robocode.tankroyale.bridge` package,
and can be changed to improve the adapter code.

The `BotPeer` in this package can be considered as the main class as these implements interfaces from the original
Robocode making the adapter possible. The `BotPeer` transforms commands and events from the original Robocode API into
commands and events for the Tank Royale API.

## Build commands

#### Clean build directory:

```shell
gradle clean
```

#### Build/compile artifact:

```shell
gradle build
```

The generated library ([Robocode API] bridge) can be found under `/build/libs` named `robocode-api-x.y.z.jar`.


[Robocode API]: https://robocode.sourceforge.io/docs/robocode/ "Original Robocode API"

[javadoc]: https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html "Javadoc Home Page"
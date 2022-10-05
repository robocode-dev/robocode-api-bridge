# Robocode API bridge for Tank Royale

This project is provided to make it possible to run original Robocode robots in Robocode Tank Royale by creating a
bridge
between the original [Robocode API] and the [Robocode Tank Royale Bot API].

Note that only java based Robocode robots are supported. Not robots developed for the .Net plugin.

The Robocode API bridge contains:

- [Robocode API adapter library] - Provides a library that is compatible with the original Robocode Bot API, but allows
  Robocode robots to run on the Tank Royale platform.
- [Robocode Robots Wrapper] - Provides a tool for creating Tank Royale bot directories inside a directory containing
  robot jar files for the original Robocode making it possible to run the robots on Tank Royale.

[Robocode API]: https://robocode.sourceforge.io/docs/robocode/ "Original Robocode API"

[Robocode Tank Royale Bot API]: https://robocode-dev.github.io/tank-royale/api/java "Robocode Tank Royale Bot API for Java"

[Robocode API adapter library]: /robocode-api "Robocode API adapter library"

[Robocode Robots Wrapper]: /robots-wrapper "Robocode Robots Wrapper"
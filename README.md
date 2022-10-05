# Robocode API bridge for Tank Royale

This project is provided to make it possible to run original Robocode robots in Robocode Tank Royale by creating a
bridge
between the original [Robocode API] and the [Robocode Tank Royale Bot API].

## Overview

The Robocode API bridge contains:

- [Robocode API adapter library] - Provides a library that is compatible with the original Robocode Bot API, but allows
  Robocode robots to run on the Tank Royale platform.
- [Robocode Robots Wrapper] - Provides a tool for creating Tank Royale bot directories inside a directory containing
  robot jar files for the original Robocode making it possible to run the robots on Tank Royale.

## 🗒️ Notes

### ❌ No support for the old .Net plugin

Note that only java based Robocode robots are supported. Not robots developed for the abandoned Net plugin for Robocode.

### 🌅 Early days

This bridge is still in its very early days, and issues exist that are being worked on continuously. However, it has
been provided for developers interested in trying this out, e.g. to help out testing it and potentially fixing bugs.
Bugs might occur in the bridge, but might also occur in the Tank Royale platform itself.

### 🎯 Backwards compatibility

Don't expect the bridge to provide a 100% backward compatible robot behaviors. That might be a dream 💖 that is simply
not
feasible, and is not the main focus of Tank Royale.

Tank Royale was written from scratch with a clean sheet without backwards compatibility as end goal. And the intention
is to keep the design of the new platform as simple and clean as possible.

Hence, we should not "pollute" the Tank Royale platform with quirks, workarounds, built-in Robocode bugs etc. in order
to be backwards compatible with legacy robots. Instead, the bridge should try to provide this compatibility, if
possible.

But if there is a bug in Tank Royale giving troubles with bots running on the bridge, the bug must be fixed in
Tank Royale, of course.

Some legacy bots might not be possible to run via the Robocode bridge for one or the other reason. E.g. because it uses
some internal Java classes (e.g. Sun classes), give classloader problems due to obfuscation, has timing issues, or
simply contain unstable or bad code.


[Robocode API]: https://robocode.sourceforge.io/docs/robocode/ "Original Robocode API"

[Robocode Tank Royale Bot API]: https://robocode-dev.github.io/tank-royale/api/java "Robocode Tank Royale Bot API for Java"

[Robocode API adapter library]: /robocode-api "Robocode API adapter library"

[Robocode Robots Wrapper]: /robots-wrapper "Robocode Robots Wrapper"
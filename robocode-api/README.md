# Robocode API adapter library

The adapter reproduces the Classic Robocode API used by compiled Java robots and translates calls, state and events to Tank Royale. The generated `robocode-api` jar and a compatible Tank Royale Bot API run inside each bot's JVM.

Use the [installation, first battle and troubleshooting guide](../guide/README.md) to run existing robots from the preview package. The package supplies these libraries automatically. A JDK is required because generated bots start `Wrapper.java` as source; Classic Robocode itself is not required.

## Development

The reproduced `robocode.*` surface is frozen: changing a signature breaks already compiled robots. Implementation lives in `dev.robocode.tankroyale.bridge`, with `BotPeer` routing robot operations through the Tank Royale Bot API. Read the [frozen-surface contract](../docs/architecture/ARCH-002-frozen-robocode-api-surface.md) before editing the reproduced API.

```powershell
.\gradlew.bat :robocode-api:test
.\gradlew.bat :robocode-api:jar
```

The adapter jar appears under `robocode-api/build/libs`. The wrapper module's `previewDist` task packages it with the published runtime and user guide. Compatibility investigation may explicitly select a matched local Bot API/runner build; preview candidates use public dependencies and reject that local override.

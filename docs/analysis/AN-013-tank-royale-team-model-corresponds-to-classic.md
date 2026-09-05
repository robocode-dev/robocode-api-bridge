---
id: AN-013
type: analysis
status: active
links: [CAP-006, ARCH-001, M-005]
title: Tank Royale's native team model corresponds closely enough to classic's to map onto directly
provenance: verified
---

# AN-013 — Tank Royale's native team model corresponds closely enough to classic's to map onto directly

## The unknown

`docs/capabilities/CAP-006-team-robot-support/design.md` left one decision open before any `M-005` implementation: does Tank Royale's team concept correspond closely enough to classic Robocode's to translate onto directly (`CAP-001`'s "delegate to the engine" reasoning), or does the correspondence break down somewhere that forces the bridge to reconstruct team semantics itself above individual bots?

## Evidence boundary

Read from the linked Bot API's own sources, decompiled from the published sources JAR (not the runtime API surface, which does not carry doc comments): `robocode-tankroyale-bot-api-1.0.2-sources.jar`, resolved from the local Maven cache at `~/.m2/repository/dev/robocode/tankroyale/robocode-tankroyale-bot-api/1.0.2/`. `1.0.2` is the version this repository's `robocode-api/build.gradle.kts:10` currently pins as the default `tankRoyaleBotApiVersion`. Compared directly against this repository's own frozen classic surface at `robocode-api/src/main/java/robocode/TeamRobot.java` and `robocode-api/src/main/java/robocode/Droid.java` (`ARCH-002`), which is classic's team API by definition — this project already treats it as the specification. No network access, no Tank Royale server source; the bot-side API and its doc comments are the only sources consulted, so nothing here confirms server-side scoring or routing behaviour beyond what the client API's doc comments assert.

## What was tried

Unpacked the Bot API sources jar and searched for every team-shaped symbol: membership, messaging, droid, and per-battle scoring records.

Found, in `dev.robocode.tankroyale.botapi`:

- `IBaseBot.getTeammateIds()` / `isTeammate(int botId)` / `broadcastTeamMessage(Object)` / `sendTeamMessage(int teammateId, Object)` — membership and messaging, all server-mediated.
- `Droid` marker interface (`dev/robocode/tankroyale/botapi/Droid.java`), doc comment: "A droid has 20 additional energy points (120 in total from the start), but has no scanner!"
- `EnvVars.getTeamId()` / `getTeamName()` / `getTeamVersion()`, reading `TEAM_ID` / `TEAM_NAME` / `TEAM_VERSION` from the bot process's own environment.
- `BotHandshakeFactory.create(...)`: every bot process sends `teamId`, `teamName`, `teamVersion`, and `isDroid` in its own handshake to the server at connect time.
- `ResultsForObserver` (schema): carries an `isTeam` flag, doc comment "Name of participant, e.g. Killer Bee (bot) or Killer Bees (team)" — the server aggregates and reports results at team granularity natively.

Compared against classic's frozen surface:

- `TeamRobot.getTeammates()` / `isTeammate(String name)` / `broadcastMessage(Serializable)` / `sendMessage(String name, Serializable)`.
- `Droid.java`'s doc comment, word for word the same claim as Tank Royale's: "+20 extra life/energy... has no scanner."

## What this establishes

The two engines' team models are the same shape at every point checked: membership, droid semantics, messaging, and team-level scoring are all first-class, server-owned concepts in Tank Royale, exactly as they are in classic. Nothing found suggests the bridge needs to reconstruct team semantics itself. `CAP-001`'s established reasoning — delegate to the engine rather than reimplement what it already does — applies to teams the same way it applied to event dispatch.

One structural difference is real and will shape the implementation, not the architecture choice: classic addresses teammates by robot **name** (`String`, e.g. `"sample.MyFirstLeader"`); Tank Royale addresses them by a numeric **bot id** (`int`) assigned per battle participant. A bridge that maps onto Tank Royale teams still needs a name-to-id table per battle to keep `TeamRobot`'s name-based methods working — this is translation work in the adapter layer, not evidence against native mapping, and it is no different in kind from other name/id or unit translations `ARCH-002`'s frozen surface already requires elsewhere.

Team message size and per-turn count are capped on the Tank Royale side (`Constants.MAX_NUMBER_OF_TEAM_MESSAGES_PER_TURN`, referenced from `IBaseBot.broadcastTeamMessage`/`sendTeamMessage`'s doc comments); this analysis did not compare that cap against classic's own limit, and doing so is `TEAM-002`'s evidence question, not this one's.

The wrapper side (`robots-wrapper/src/main/java/Main.java`) already loops per-`.properties`-file inside a jar to produce one bot directory per robot; it does not yet read a `.team` descriptor or emit `TEAM_ID`/`TEAM_NAME` for the directories it produces from a team jar. That gap matches what `ARCH-001` already named — the one-jar-to-one-bot-directory assumption has to give for a team jar — and is implementation work for `M-005`, not a design unknown.

## What was rejected

Reconstructing team semantics above individual bots (the design doc's second option) is rejected as unnecessary: it would duplicate membership, droid gating, messaging, and scoring logic the server already owns, with no evidence of a correspondence gap that would force it. See `ADR-002` for the routed decision.

## Consumer

`ADR-002` records the routed decision. `clue-delta` for `M-005` implementation follows from that ADR: extend `robots-wrapper` to read `.team` descriptors and emit one Tank Royale bot directory per member with shared `TEAM_ID`/`TEAM_NAME`, and add the name↔id translation layer `ITeamRobotPeer`'s bridge implementation needs.

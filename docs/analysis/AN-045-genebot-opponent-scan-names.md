---
id: AN-045
type: analysis
status: active
links: [P-001, CAP-003, CAP-005, CAP-006, ADR-002, AN-015]
title: GeneBot's score gap follows Tank Royale withholding opponent names from scans
provenance: inferred
reversal-cost: low
---

# AN-045 — GeneBot's score gap follows Tank Royale withholding opponent names from scans

## Question

Does the confirmed `teamrumble/sp.Minis.GeneBotUpgrade_1.1.jar` score advantage identify a robot-side difference, a bridge name-mapping defect, or missing opponent identity in Tank Royale's Bot API?

## Evidence boundary

The read-only team jar has SHA-256 `64916763891da023ba2935e8ad61d34638e4a0297d858ffc4e79b81b3e2f66e7`. Its descriptor runs five copies of `sp.Minis.geneticBot [1.0*]`; the selected Classic robot is `sp.Minis.geneticBot 1.1`. The jar bundles no source. Bytecode inspection shows `onScannedRobot` suppresses firing when the event name contains `genetic`; otherwise it aims predictively and fires with power `1000 / distance`. The team jar was only read, never rewritten.

The official five-pair observation `7bae417652f7cb81` completed on 2026-10-05 with Classic Robocode 1.11.1, bridge commit `fef17858fce2008bdb792396d7ca15f2f1e5968f`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API 1.4.0, Runner 1.4.0, and wrapper 0.3.1. The pinned bridge, Bot API, Runner, and wrapper jar hashes are respectively `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`. The prepared Windows environment used official teamrumble parameters (1200×1200, 10 rounds, two teams of five); the observation does not pin the Java executable version.

The per-pair Tank Royale score deltas were +44.9%, +37.3%, +44.9%, +29.7%, and +38.2%, for a mean of +39.0% (`CONFIRMED (score)`). Mean Classic score was 14,109.6 and mean Tank Royale score was 19,588.0. No bridge-only exceptions occurred. Captured skipped-turn telemetry, when present, was limited to round 1 turn 1. In the fifth pair's score breakdown, Classic recorded 0 bullet damage and 0 bullet bonus, while Tank Royale recorded 5,288 bullet damage and 287 bullet bonus; Classic recorded 1,685 ram damage and Tank Royale 259.

The bridge mapper constructs `ScannedRobotEvent.getName()` with `TankRoyaleBotNameResolver.getNameOrId`; that resolver uses an interned numeric bot ID only when the Bot API name lookup returns null. In the matching Tank Royale source revision, `GameServer` assigns names from each handshake's team-member name and version, but `BotNameMapper.namesForBotAndTeammates` includes only the observing bot and its teammates in `GameStartedEventForBot`. Java `BaseBotInternals.getBotName(int)` returns `botNames.get(botId)`, so an opponent has no mapped name. The bridge source path is `ScannedRobotEventMapper.java` and `TankRoyaleBotNameResolver.java`; the pinned upstream implementations are [`GameServer.kt`](https://github.com/robocode-dev/tank-royale/blob/ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9/server/src/main/kotlin/dev/robocode/tankroyale/server/core/GameServer.kt#L123-L135), [`BotNameMapper.kt`](https://github.com/robocode-dev/tank-royale/blob/ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9/server/src/main/kotlin/dev/robocode/tankroyale/server/mapper/BotNameMapper.kt#L33-L46), and [`BaseBotInternals.java`](https://github.com/robocode-dev/tank-royale/blob/ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9/bot-api/java/src/main/java/dev/robocode/tankroyale/botapi/internal/BaseBotInternals.java#L765-L770).

Classic builds scan-event names from the target robot's battle name ([pinned Classic source](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/peer/RobotPeer.java#L1554-L1566)). For the opposing copies of `sp.Minis.geneticBot`, Classic therefore supplies a name containing `genetic`, while the Tank Royale source path supplies the numeric fallback. This conclusion is derived from the versioned source and bytecode; the harness did not separately log callback names.

## Finding

The score gap is consistent with a Tank Royale information-boundary difference: opposing bot names are absent from the Bot API name map, so the bridge can only provide numeric IDs to legacy scan callbacks. GeneBot's name-based firing branch consequently behaves differently against another geneticBot team, and the fifth pair's bullet-score breakdown matches that mechanism. The bridge correctly uses every name the API supplies; it cannot reconstruct an opponent's name from a scan ID. The cause is `tank-royale-opponent-names-not-exposed`, owner `tank-royale`.

No bridge code fix is indicated by this evidence. Restoring the Classic scan-name contract requires an upstream protocol/API decision to expose opponent names, or another authoritative source for those identities. That changes which battle information Tank Royale provides and should use the full route before implementation. The current score discrepancy remains open for M-006 until an upstream repair is built as a matched Bot API and Runner pair and the official case is retested. No changelog entry applies because no bugfix was made.

## M-006 handoff

Keep `teamrumble/sp.Minis.GeneBotUpgrade_1.1.jar` at `CONFIRMED (score)` with the Tank Royale name-visibility diagnosis and the original five-pair observation. Continue in registry order with `teamrumble/tmnr.TMNR_1.01.jar`.

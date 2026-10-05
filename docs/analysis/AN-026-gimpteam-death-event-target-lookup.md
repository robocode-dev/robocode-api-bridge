---
id: AN-026
type: analysis
status: active
links: [P-001, CAP-001, CAP-006, CAP-007, AN-016, AN-020]
title: GimpTeam's classic death callback dereferences a missing target entry
provenance: inferred
reversal-cost: low
---

# AN-026 — GimpTeam's classic death callback dereferences a missing target entry

## Question

Does the current GimpTeam error discrepancy identify a bridge death-event defect, a robot defect, or only a robot crash mechanism?

## Evidence boundary

The read-only team jar has SHA-256 `cbe24b9d14afcf2e5e60bd2f10c065e827592b83b6ee155e6738a52c44a54330`. The current official pair used classic Robocode 1.11.1, bridge commit `381c33de8ffbc1acd1e0eacddd85636fdb5b460e`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local 1.4.0 runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The pair used official teamrumble settings: a 1200×1200 field, 10 rounds, and two teams. The collection jar remained read-only. Tank Royale skipped-turn telemetry recorded one event for each of the ten bot IDs, all at round 1, turn 1.

## Observation

Classic scored 22,013 and reported two error lines for a NullPointerException in `gimp.GimpBot.onRobotDeath` during round 4. The exception text says it could not assign `Enemy.live` because local variable `en` was null. Tank Royale scored 19,859 and reported no runtime errors. The current registry observation is `be52cefcf409116c`; it remains `DISCREPANCY (errors)`. Its −9.8% score delta is not a standalone score finding while the error outcomes differ.

The earlier registry observation `d373ae9bed366ff4` recorded the same classic callback signature and no Tank Royale error under older bridge and Tank Royale artifacts. The fresh pair reproduces the classic crash mechanism on the current artifact set.

## Bytecode finding

`GimpBot.run` initializes a `Hashtable` named `targets`. `onScannedRobot` adds an `Enemy` under each scanned robot name. `onRobotDeath` calls `targets.get(event.getName())` and immediately writes the returned object's `live` field, without checking for null. The exception therefore occurs when the callback names a robot for which this bot has no `Enemy` entry. The captured stack does not contain that victim name or establish why the entry is absent.

The current Tank Royale server emits public death events to the bots in a turn. The Bot API maps another bot's death to `BotDeathEvent`, and `BotPeer` maps that event to a legacy `RobotDeathEvent` using the bot-name resolver before calling `onRobotDeath`. This confirms that the bridge has a route for death notifications; it does not prove which names or callbacks occurred in the Tank Royale pair, nor that event timing matched the independent classic battle.

## Finding

The null dereference is robot-owned: GimpTeam assumes every robot named by a death event already exists in its scan-built table. The cause of the cross-engine occurrence difference remains unlocalized. The engines ran independent battles without a shared seed, and the available logs do not show the death-event victim name or a paired scan/death timeline. No bridge correction is justified by this evidence.

The registry records `robot-unchecked-death-event-target-lookup` with owner `robot` for the crash mechanism while preserving `DISCREPANCY (errors)` for the asymmetric observed outcome. The read-only collection jar is unchanged.

## M-006 handoff

Keep the current error discrepancy in the registry. A focused follow-up would need to record scanned names and death-event victim names on both engines for the same controlled participant setup before changing team-death delivery or name mapping.

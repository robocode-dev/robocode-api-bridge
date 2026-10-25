---
id: AN-712
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, C-004, C-007, AN-015, AN-193, AN-319, AN-711]
title: Fermat's negative scan-time index depends on its previous-scan state
provenance: inferred
reversal-cost: low
---

# AN-712 — Fermat's negative scan-time index depends on its previous-scan state

## Risk investigated

Whether the intermittent `Index -1` failure at `ak.RobotBody.getDirection` can be related to an observable startup event in the current official Fermat run, and what the robot's read-only bytecode says about the value that produces the index.

## Evidence boundary

This finding supplements AN-711 using the same read-only jar and three uninstrumented official retests: `eb08cb7a71a0a8a6` and `fed84e78864ba5e3` completed without the index error, while `98e486da57c9c38b` reproduced it. All used the official 35-round, ten-bot melee setup and pin bridge commit `7d61d52`. The latest run scored 114,496 on Classic and 111,174 on Tank Royale (−2.9%); its 298 Classic errors and 30 Tank Royale errors were the known fixed-opponent-pool failures. The first observation records four skipped-turn events at round 1, turn 1, for bot IDs 4, 5, 6, and 9. The saved Tank Royale runner log names a participant `Fermat 2.0`, and that participant's process output reports `BRIDGE_SKIPPED_TURN_TELEMETRY_READY botId=5` followed by `BRIDGE_SKIPPED_TURN botId=5 round=1 turn=1`; this maps one of the events to Fermat. A separate class-filtered scan diagnostic (`687cc56e0780db5d`) is not parity evidence because synchronous logging changed the observed startup schedule. The original jar contains no bundled Java sources, so the read-only `javap` disassembly was used.

## What was inspected

In `ak.OtherBot`'s constructor, the bytecode initializes `previousScanTime` to zero. In `ak.OtherBot.update`, it copies `scanTime` into `previousScanTime` before advancing the scan time. At `ak.RobotBody.getDirection` source line 115, the code indexes `Fermat.updateTime` using `(other.previousScanTime - 1) % 50`. If this path runs while `previousScanTime` is zero, Java's remainder yields `-1` and the array access throws the recorded `Index -1 out of bounds for length 50` exception. The disassembly narrows the path: for an alive target, it reaches this index while handling a target classified as attacking with an energy decrease greater than `0.09` and less than `3.01`, when the scan-time gap is at most eight turns. A first scan alone does not enter this branch. In the separate diagnostic run, the first logged Fermat scan had `eventTurn=3`, mapped `eventTime=2`, and `peerTime=2`, targeting bot 9; the battle completed without the index error. That run also recorded nine participants skipping turn 1, so the trace cannot establish the normal first-scan schedule.

Two uninstrumented official runs completed without the index error, and the intervening run reproduced the `ArrayIndexOutOfBoundsException` through `ak.Fermat.doUpdates` and `ak.RobotBody.getDirection`. The latest run had no skipped-turn telemetry and its Tank Royale error signatures were confined to the known opponent pool. The retained logs do not show the value of `previousScanTime` at the failing access. The bot-ID mapping therefore corrects AN-711's statement that the saved evidence could not identify Fermat, and the three official results confirm an intermittent failure without identifying its trigger.

## Finding

The negative index is formed from the robot's own `previousScanTime` field; it is not directly formed from `getTime()`. The guard against zero or negative previous scan times is absent at the indexed access. The failure requires the robot's conditional opponent-fire prediction path, so a first scan at time zero is not enough to explain it. The one captured scan event had matching mapped event time and peer time, but its logging perturbed the schedule and the run did not fail. Whether the bridge's normal scan and status ordering can leave `previousScanTime` at zero when that prediction path runs remains unproven. This evidence confirms the intermittent failure but is insufficient to attribute it to the robot or the bridge.

## Implications for M-006

Keep `initial-status-before-run` open. A useful next trace must correlate the first scan and first qualifying opponent energy drop with the robot's update order, without synchronous per-event logging that changes the schedule; preserve the bot-ID-to-name mapping in that evidence. Do not change the bridge's time or scan mapping based on these runs alone. The diagnostic run is excluded from the parity registry, and no rumble jar was modified.

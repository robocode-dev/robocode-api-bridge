---
id: AN-043
type: analysis
status: active
links: [P-001, CAP-001, CAP-005, CAP-006, CAP-007, AN-002, AN-020]
title: GlowingHawks dereferences an unscanned target in its death handler
provenance: inferred
reversal-cost: low
---

# AN-043 — GlowingHawks dereferences an unscanned target in its death handler

## Question

Does the current Tank Royale-only `NullPointerException` for `teamrumble/rz.GlowingHawks_0.2.jar` identify a bridge event or name-mapping defect, or does the robot assume that every death event follows a scan?

## Evidence boundary

The read-only team jar has SHA-256 `9aed0826521a57def3253fa9b220bae37b5b8f2261604e842c08f8d54a5b77ce`; its descriptor contains five copies of `rz.GHMember [0.2]`, and it bundles no Java source. Read-only bytecode inspection shows `onScannedRobot` creates entries in the static `targets` table and `onRobotDeath` executes `targets.get(e.getName()).live = false` at source line 157 without checking for a missing entry. The captured Tank Royale stderr says `Cannot assign field "live" because the return value of "java.util.Hashtable.get(Object)" is null` at that line.

The current official observation is `c88aa80da0c0d8b1`, completed 2026-10-05, with the official team parameters (1200×1200 arena, 10 rounds, two teams). The prepared Windows environment used Classic Robocode 1.11.1, Tank Royale Runner and Bot API 1.4.0, and bridge commit `4af70cd1f6af42aedcb42fb4117237eb3fc10026`; bridge API SHA-256 is `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, Runner SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, Bot API SHA-256 is `ab65c4d5cec1808adeb71375ada6e15341ae250def89a6c10fc9da879de0752`, and wrapper SHA-256 is `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`. The observation recorded Classic 1.11.1's scores and errors, but does not pin the Java executable version.

The five-pair score confirmation stopped after two complete pairs when attempt three produced a Tank Royale-only `NullPointerException` in `rz.GHMember.onRobotDeath`. The complete pair deltas were −1.7% and −6.0% (mean −3.85%); the attempt-three scores are not a complete pair. Earlier +29.5% observations used Bot API 1.2.0. This is a prepared-environment, statistical comparison; the current observation contains fewer than the five valid pairs required to confirm or clear a score gap.

The Classic source checkout was at commit `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`. `Battle` publishes each death to every living robot, with no scan-history condition ([pinned source](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/Battle.java#L581-L588)). Classic constructs both scan and death event names with `RobotPeer.getNameForEvent` ([name mapping](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/peer/RobotPeer.java#L1009-L1014), [scan event](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/peer/RobotPeer.java#L1554-L1566)). The bridge likewise resolves the scanned bot ID and death victim ID through the same `TankRoyaleBotNameResolver.getNameOrId` path.

## Finding

The error is caused by a robot-owned assumption: `onRobotDeath` expects a target-table entry even though Classic's documented and implemented contract permits a death event for a robot this observer has never scanned. The bridge does not require a scan before dispatching death events, and the scanned and dead names use the same resolver, so this evidence does not indicate a bridge identity mismatch. No bridge repair is indicated; the rumble jar remains unchanged.

The old +29.5% score signal is not reproduced by the two complete current pairs, but the five-pair confirmation did not finish. Keep the score question open rather than treating the two-pair mean as a confirmation or a clearance. The current registry observation remains `DISCREPANCY (errors)` and is tagged with cause `robot-death-event-assumes-prior-scan`, owner `robot`.

## M-006 handoff

Keep `teamrumble/rz.GlowingHawks_0.2.jar` recorded with its diagnosed robot-owned death-handler error and incomplete score confirmation. Continue in registry order with `teamrumble/rz.HOFSwarm_1.1.jar`.

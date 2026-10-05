---
id: AN-046
type: analysis
status: active
links: [P-001, CAP-001, CAP-003, CAP-005, CAP-006, AN-043]
title: TMNR's death handler assumes the target was scanned first
provenance: inferred
reversal-cost: low
---

# AN-046 — TMNR's death handler assumes the target was scanned first

## Question

Does the current `teamrumble/tmnr.TMNR_1.01.jar` Tank Royale-only exception indicate a bridge name or event-delivery defect, or does the legacy robot dereference a target that it never recorded from a scan?

## Evidence boundary

The read-only team jar has SHA-256 `6ba527fe5cd0f98ebba9dd4ccb2cdba8b8e6da4ef60f0d69b0b4a65b24bd6e47`. Its descriptor lists `tmnr.Donatello`, `tmnr.Giovanni`, `tmnr.Leonardo`, `tmnr.Michelangelo`, and `tmnr.Raphael`; the team jar was not rewritten.

The current official observation `3c9d506cf26eda09` completed on 2026-10-05 with Classic Robocode 1.11.1, bridge commit `fef17858fce2008bdb792396d7ca15f2f1e5968f`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API 1.4.0, Runner 1.4.0, and wrapper 0.3.1. The artifact hashes and prepared Windows setup match the five-pair observation recorded in AN-045: official teamrumble parameters, 1200×1200, 10 rounds, two teams of five. The observation does not pin the Java executable version.

Classic completed with no errors and score 19,726. Tank Royale produced a `NullPointerException` from `tmnr.Raphael$Robot.access$6`, with message `Cannot assign field "alive" because "<parameter1>" is null`; its stack enters `tmnr.Raphael.onRobotDeath` at line 112 through `BotPeer.dispatchRobotDeathEvent`, and the battle produced no Tank Royale score. The current registry result is `DISCREPANCY (outcome)`, not a score comparison.

Read-only bytecode inspection shows `Raphael.onScannedRobot` looking up the scan name in its `robots` map and creating and inserting a `Robot` record when that name is new. `Raphael.onRobotDeath` does `robots.get(e.getName())` and immediately calls the generated `access$6(..., false)` accessor; the accessor writes the `alive` field and has no null guard. Thus a death event for a target this member has not scanned dereferences null. The captured callback stack is consistent with that path.

The older official observation `f255c21a2cd735b1` used Bot API 1.2.0 and a different Runner. It recorded the same null-assignment signature in `tmnr.Giovanni$Robot.access$6` on Classic, while Tank Royale had no exception. That older score delta (+41.8%) is not a valid current score confirmation because one engine errored and the artifacts are old. It does show the same robot-owned callback assumption can fail on Classic as well.

The bridge dispatches every Tank Royale bot-death event and maps its victim name through `TankRoyaleBotNameResolver`; scan callbacks use the same resolver. If this robot had first inserted the same target name from a scan, the death lookup would find that entry. Classic likewise publishes robot-death events without requiring the observer to have scanned the victim ([pinned Classic battle source](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/Battle.java#L581-L588)). The older Classic exception follows the same null-unsafe robot method.

## Finding

The immediate failure is robot-owned: `onRobotDeath` assumes the victim already exists in a scan-built map. Classic's event contract does not guarantee that, and the current Tank Royale-only occurrence is consistent with an unscanned victim; the older Classic run independently reached the same null dereference. The bridge's scan and death name paths agree, so the evidence does not justify a bridge name-mapping fix. Tag the case `robot-death-event-assumes-prior-scan`, owner `robot`, and retain the current `DISCREPANCY (outcome)` because the paired run did not produce two usable scores. The legacy jar remains unchanged.

## M-006 handoff

Keep the current error observation and the older Classic-side recurrence in registry history. Do not infer a score result from either errored observation. Continue in teamrumble registry order with `ustimaw.NightmareTeam_3.3.jar`.

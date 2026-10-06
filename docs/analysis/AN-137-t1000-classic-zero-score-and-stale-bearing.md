---
id: AN-137
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: T1000's Classic zero score persists, and its bytecode ignores scanned bearing
provenance: inferred
reversal-cost: low
---

# AN-137 — T1000's Classic zero score persists, and its bytecode ignores scanned bearing

## Risk investigated

Whether com.sociesc.T1000's historical Classic zero-score result recurs with current matched artifacts, and whether the robot bytecode offers an explanation.

## Evidence boundary

The read-only subject jar `com.sociesc.T1000_1.0.0.jar` has SHA-256 `4398cb800fc016f19b0a9841c795e5bdf33ab65beaff628759abf81ce711732f`. The current official five-pair confirmation `f0a71c562a4e01e4` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `81eec77d46fb2e0d04f5a4f0eb716914903026f7`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 0 in all five attempts; Tank Royale scores were 240, 601, 422, 360, and 360, averaging 396.6. Neither engine reported errors, and all five Tank Royale runs captured empty skipped-turn event lists. No valid pair deltas could be computed because every Classic score was zero. The registry status is `DISCREPANCY (outcome)`.

Earlier observations `c50ef80274e4a320` and `0f2d5c4b9089dfeb` also recorded Classic scores of 0 and 3, while Tank Royale scored 362 and 421, without errors. Read-only disassembly shows `T1000` initializes `enemyAngle` to `Double.MAX_VALUE`; `run()` reads that field to choose the turn direction and amount. `onScannedRobot()` computes the bearing into a local variable but returns without storing it to `enemyAngle`. This gives the robot a stale turn target, a plausible source of poor behavior that does not by itself prove why Classic scores zero while Tank Royale earns modest points.

## Finding

The Classic zero-score outcome persists across the five current attempts, while Tank Royale produces a low but nonzero score. The bytecode exposes a robot-side target-tracking defect, but its connection to the cross-engine score difference remains unproven. No runtime errors or captured skipped turns occurred, and no bridge or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/conscience.Bulldozer_1.0a.jar` (`score-review`).

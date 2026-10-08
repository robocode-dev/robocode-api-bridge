---
id: AN-303
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ShinigamiKNN's unguarded histogram index error recurs
provenance: inferred
reversal-cost: low
---

# AN-303 — ShinigamiKNN's unguarded histogram index error recurs

## Risk investigated

Whether ShinigamiKNN's historical Tank Royale no-score outcome and array bounds error recur under the latest matched artifacts, and whether the current evidence identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `7ce1f15f0a454f7f0a29dffe867b728d1ebc56e98b4a9e629f31237a93b1695e`. The official observation `1fc2149178754052` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c9147546cb7d05628efc6cda11e32351bc4979e2`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 5,940 with no errors. Tank Royale produced no score and recorded eight `java.lang.ArrayIndexOutOfBoundsException` entries, all with message `Index 32 out of bounds for length 32`, plus a worker-without-result entry. One exception was attributed to `doka.ShinigamiKNN.onScannedRobot`; the remaining origins are unknown. The registry records the callback signature as bridge-only, and skipped-turn telemetry is incomplete because the run stopped before completion. The status is `DISCREPANCY (outcome)`.

AN-177's prior observation recorded two exceptions with the same message and callback origin. Its read-only bytecode inspection found that the robot creates an `int[32]`, computes a histogram index without clamping, and indexes the array. The current repeated `Index 32` error is consistent with that robot-owned bounds defect. The evidence does not show why Tank Royale reached the endpoint in this run or establish a bridge cause.

## Finding

ShinigamiKNN's Tank Royale no-score outcome and robot callback array bounds error recur; the current run records more repeated exceptions than AN-177. The unguarded histogram index explains the direct failure, but the cross-engine trigger remains unknown. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/donjezza.Jezza_1.0.jar` (`score-review`).

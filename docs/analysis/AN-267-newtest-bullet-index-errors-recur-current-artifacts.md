---
id: AN-267
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: NewTest's Classic bullet-index errors recur less often with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-267 — NewTest's Classic bullet-index errors recur less often with the latest artifacts

## Risk investigated

Whether `com.arsenic.NewTest_1.0.jar`'s Classic-only bullet-index errors recur under the latest matched artifacts and whether their frequency changes.

## Evidence boundary

The read-only subject jar has SHA-256 `0a5a9d382489ace685399cf9c5d709daa70f7e545ac85d54a1b0e81bf7d3a412`. The official observation `1193dbc4cad7591c` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 6,752 and logged four error lines representing two `ArrayIndexOutOfBoundsException` events in `com.arsenic.NewTest.onBulletHit`, with indexes −56 and −48 against arrays of length 2. Tank Royale scored 6,199 with no errors, for a −8.2% delta, and captured an empty skipped-turn event list. The registry status remains `DISCREPANCY (errors)`.

AN-135's bytecode inspection found that `onBulletHit` and `onBulletMissed` use the extracted bullet-power byte directly as an index into two-entry arrays. The current `onBulletHit` failures match that unchecked-index path. The error count is lower than AN-135's 12 logged errors; the current single pair does not estimate how often the robot reaches the path.

## Finding

NewTest's Classic-only bullet-index error recurs with the latest matched artifacts, with two observed events rather than the six events represented in AN-135's log count. Tank Royale reports no errors, and the score difference is within the single-pair review threshold. The robot-owned indexing defect remains the supported cause; no bridge or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/com.blogspot.malinkody.DestrobotMalin_1.0.jar` (`CONFIRMED (score)`).

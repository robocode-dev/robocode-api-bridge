---
id: AN-269
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: T1000's Classic zero-score outcome persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-269 — T1000's Classic zero-score outcome persists with the latest artifacts

## Risk investigated

Whether `com.sociesc.T1000_1.0.0.jar`'s recurring Classic zero-score outcome persists under the latest matched artifacts and whether the five-pair run can confirm a percentage score gap.

## Evidence boundary

The read-only subject jar has SHA-256 `4398cb800fc016f19b0a9841c795e5bdf33ab65beaff628759abf81ce711732f`. The official five-pair confirmation attempt `fcd94c98adebc6da` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scores across the five attempts were 3, 0, 0, 0, and 0; Tank Royale scores were 300, 482, 300, 360, and 540. The respective means were 0.6 and 396.4. Neither engine reported runtime errors, and all five Tank Royale skipped-turn captures were empty. Only one percentage delta was calculable: +9,900% from the 3-point Classic score to 300 in Tank Royale. With just one valid delta out of five, the harness status is `DISCREPANCY (outcome)` rather than a confirmed score gap.

AN-137's earlier confirmation had Classic scores of 0 in all five attempts and Tank Royale scores from 240 to 601. The latest run again has four Classic zero scores and one near-zero score, so the same denominator problem prevents a meaningful percentage confirmation. AN-137's bytecode inspection found that T1000 reads a stale `enemyAngle` field because its scan callback computes but does not store the new bearing; this remains a robot-side behavioral lead, not a demonstrated explanation of the score difference.

## Finding

T1000's Classic zero-score outcome persists with the latest matched artifacts, and the five-pair result still cannot produce a stable percentage confirmation. The single +9,900% delta is caused by the three-point Classic denominator and is not a meaningful estimate of score advantage. No runtime errors or captured skipped turns occurred. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/conscience.Bulldozer_1.0a.jar` (`CONFIRMED (score)`).

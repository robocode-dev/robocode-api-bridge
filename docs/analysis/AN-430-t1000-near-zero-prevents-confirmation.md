---
id: AN-430
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-137, AN-269]
title: T1000's near-zero Classic scores still prevent a reliable score confirmation
provenance: inferred
reversal-cost: low
---

# AN-430 — T1000's near-zero Classic scores still prevent a reliable score confirmation

## Risk investigated

Whether `com.sociesc.T1000_1.0.0.jar`'s recurring near-zero Classic scores persist under the latest matched artifacts, and whether the five-pair run can establish a meaningful percentage score gap.

## Evidence boundary

The read-only subject jar has SHA-256 `4398cb800fc016f19b0a9841c795e5bdf33ab65beaff628759abf81ce711732f`. The official five-attempt confirmation `d1599f3b55efae4f` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1505150c660236e2d868aa090c7c6746c815f37c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

Five attempts ran. Only three produced a calculable percentage delta because Classic scored zero in two attempts. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry status is `DISCREPANCY (outcome)`; the reported mean delta of +18,100% summarizes only those three calculable pairs and is not a five-pair score confirmation. The manifest's `threshold: 25.0` is the regular sweep setting and does not resolve this outcome classification.

## What was tried

Classic scores were 4, 2, 3, 0, and 0, averaging 1.8. Tank Royale scores were 542, 541, 420, 480, and 662, averaging 529.0. The three calculable pair deltas were +13,450%, +26,950%, and +13,900%. Neither engine reported runtime errors, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-269's preceding confirmation had Classic scores of 3, 0, 0, 0, and 0, averaging 0.6, and Tank Royale scores averaging 396.4; only one percentage delta was calculable. The current run has three nonzero Classic scores rather than one, but two zero-score attempts still prevent a stable five-pair percentage comparison. AN-137's bytecode review found a stale `enemyAngle` tracking behavior in the robot; its connection to the score difference remains unproven.

## What was not pursued

The near-zero scores were not attributed to the bridge or the robot from the aggregate results alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

T1000 continues to produce near-zero Classic scores, with two zero-score attempts out of five. Tank Royale scores remain modest and nonzero, but the three resulting percentage deltas are inflated by Classic's tiny denominator and cannot establish a reliable score-gap magnitude. No runtime errors or skipped-turn events occurred, and the cause remains open.

## M-006 handoff

Continue in registry order with `roborumble/conscience.Bulldozer_1.0a.jar` (`CONFIRMED (score)`).
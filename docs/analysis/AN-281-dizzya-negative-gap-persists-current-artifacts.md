---
id: AN-281
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: DizzyA's large negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-281 — DizzyA's large negative score gap persists with the latest artifacts

## Risk investigated

Whether `daemons.DizzyA_1.0.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `aeba11ca1cd50603c4b74166f04dea30238c77faead6f6830874571bea4091ad`. The official five-pair confirmation `c8636f3c42595ce2` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c0687e317ddc8775f40a7d8b4a88226cfedb2f6c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 12,020.8 points and Tank Royale averaged 3,896.4, for a −67.5% mean delta. Pair deltas were −69.6%, −70.1%, −70.1%, −63.3%, and −64.4%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-152's prior five-pair confirmation averaged 11,223.2 in Classic and 3,813 in Tank Royale, a −66.0% mean delta. The latest run reproduces the large Classic advantage with a slightly larger magnitude.

## Finding

DizzyA's large negative score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dam.MogBot_2.9.jar` (`DISCREPANCY (outcome)`).

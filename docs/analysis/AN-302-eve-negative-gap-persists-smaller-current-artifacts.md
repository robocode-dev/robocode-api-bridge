---
id: AN-302
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Eve's negative score gap persists with a smaller mean difference
provenance: inferred
reversal-cost: low
---

# AN-302 — Eve's negative score gap persists with a smaller mean difference

## Risk investigated

Whether `dmp.nano.Eve_3.41.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `edd8e3e4a88e6abb36bba0d2241c3b7d03dda39619f4f9264e4b4757f1eb2da9`. The official five-pair confirmation `154df2b2813a62bd` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c9147546cb7d05628efc6cda11e32351bc4979e2`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 2,944.0 points and Tank Royale averaged 1,819.2, for a −38.16% mean delta. Pair deltas were −34.2%, −36.8%, −40.6%, −40.1%, and −39.1%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-176's prior five-pair confirmation averaged 2,968.6 in Classic and 1,264.0 in Tank Royale, a −56.2% mean delta. The latest run preserves the Classic advantage, but with a smaller mean magnitude.

## Finding

Eve's negative score gap persists under the latest matched artifacts with a smaller mean magnitude than in AN-176. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/doka.ShinigamiKNN_1.0.jar` (`DISCREPANCY (outcome)`).

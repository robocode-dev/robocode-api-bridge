---
id: AN-297
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: gpBot's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-297 — gpBot's negative score gap persists with the latest artifacts

## Risk investigated

Whether `dggp.haiku.gpBot_0_1.1.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `a19725573c6849e5f85f63fecf569745978d77dbefc73280f000c141a695e619`. The official five-pair confirmation `255126bc4a951a44` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1165900f02513a45d1135b61cc927875cea0eca4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,322.8 points and Tank Royale averaged 2,348.8, for a −55.78% mean delta. Pair deltas were −62.1%, −53.3%, −55.6%, −54.6%, and −53.3%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-169's prior five-pair confirmation averaged 5,179.2 in Classic and 2,464.8 in Tank Royale, a −52.34% mean delta. The latest run reproduces the Classic advantage with a slightly larger mean magnitude.

## Finding

gpBot's negative score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dittman.BlindSquirl_Retired.jar` (`score-review`).

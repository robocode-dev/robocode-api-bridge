---
id: AN-304
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Jezza's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-304 — Jezza's negative score gap persists with the latest artifacts

## Risk investigated

Whether `donjezza.Jezza_1.0.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `1188605236143d82517db214ae55e4881181a3c88245d6f498dfd9a1f5fd9de5`. The official five-pair confirmation `5d30c62d6d296153` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c9147546cb7d05628efc6cda11e32351bc4979e2`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 7,864.8 points and Tank Royale averaged 5,907.2, for a −24.86% mean delta. Pair deltas were −22.8%, −25.1%, −25.7%, −24.3%, and −26.4%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-178's prior five-pair confirmation averaged 7,844.2 in Classic and 5,914.2 in Tank Royale, a −24.6% mean delta. The current result reproduces a very similar negative score gap.

## Finding

Jezza's negative score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/donjezza.Muncho_1.0.jar` (`score-review`).

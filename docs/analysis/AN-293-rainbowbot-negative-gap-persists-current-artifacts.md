---
id: AN-293
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: RainbowBot's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-293 — RainbowBot's negative score gap persists with the latest artifacts

## Risk investigated

Whether `deo.virtual.RainbowBot_1.0.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `3854b1a74b34f26b4fccfc997b5faadb4db60177cd48857d8bdba79e63f5bba4`. The official five-pair confirmation `6dbff24d35f87faf` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `88fece317986abcac66aa0245596105c88338789`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 6,703.0 points and Tank Royale averaged 3,301.0, for a −50.68% mean delta. Pair deltas were −44.3%, −53.8%, −56.2%, −49.4%, and −49.7%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-165's prior five-pair confirmation averaged 6,534.6 in Classic and 3,420.6 in Tank Royale, a −47.62% mean delta. The latest run reproduces the negative score gap with a slightly larger magnitude.

## Finding

RainbowBot's negative score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dft.Calliope_5.6.jar` (`score-review`).

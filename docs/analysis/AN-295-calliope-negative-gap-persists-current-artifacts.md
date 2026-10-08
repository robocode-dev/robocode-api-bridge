---
id: AN-295
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Calliope's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-295 — Calliope's negative score gap persists with the latest artifacts

## Risk investigated

Whether `dft.Calliope_5.6.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `dee2ec44341ba6c2574bf995c317b4f806d3189f19ab040f8565b487dab5248a`. The official five-pair confirmation `1392767dac32c816` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `88fece317986abcac66aa0245596105c88338789`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 8,696.0 points and Tank Royale averaged 6,863.4, for a −21.16% mean delta. Pair deltas were −19.9%, −35.6%, −20.0%, −13.4%, and −16.9%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-166's prior five-pair confirmation averaged 8,663 in Classic and 6,219.8 in Tank Royale, a −28.22% mean delta. The latest run preserves the negative direction, with a smaller mean magnitude.

## Finding

Calliope's negative score gap persists under the latest matched artifacts, though its mean magnitude is smaller than in AN-166. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dft.Freddie_1.32.jar` (`score-review`).

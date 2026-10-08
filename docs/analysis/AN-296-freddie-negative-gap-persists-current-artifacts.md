---
id: AN-296
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Freddie's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-296 — Freddie's negative score gap persists with the latest artifacts

## Risk investigated

Whether `dft.Freddie_1.32.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `76565239b343d40c03dd6c4e886fc78749335da2796662d280006545176057ae`. The official five-pair confirmation `c8a969cda214e030` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `88fece317986abcac66aa0245596105c88338789`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 7,322.0 points and Tank Royale averaged 3,336.0, for a −54.34% mean delta. Pair deltas were −58.7%, −55.3%, −49.9%, −54.4%, and −53.4%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-167's prior five-pair confirmation averaged 7,519.2 in Classic and 2,965.0 in Tank Royale, a −60.52% mean delta. The latest run reproduces the negative direction with a smaller mean magnitude.

## Finding

Freddie's negative score gap persists under the latest matched artifacts, though its mean magnitude is smaller than in AN-167. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Krazy is `MATCHED (score noise)` in AN-168 and is skipped; continue with `roborumble/dggp.haiku.gpBot_0_1.1.jar` (`score-review`).

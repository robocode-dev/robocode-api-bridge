---
id: AN-167
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Freddie's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-167 — Freddie's negative score gap is confirmed across five pairs

## Risk investigated

Whether Freddie's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dft.Freddie_1.32.jar` has SHA-256 `76565239b343d40c03dd6c4e886fc78749335da2796662d280006545176057ae`. The official confirmation `987482f75234b5ca` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `a6a86b69802e06a5a65a748a4b6e7b5b45ff2982`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −55.6%, −61.1%, −64.9%, −59.7%, and −61.3%. Classic averaged 7,519.2 points and Tank Royale averaged 2,965, for a −60.52% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `341a18222d734d39` recorded a −51.2% single-pair delta without runtime errors. The current five-pair result confirms the same direction at a larger magnitude.

## Finding

Freddie's Classic score advantage is confirmed with current matched artifacts and is consistent in direction with its earlier observation. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dft.Krazy_1.5.jar` (`score-review`).

---
id: AN-165
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: RainbowBot's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-165 — RainbowBot's negative score gap is confirmed across five pairs

## Risk investigated

Whether RainbowBot's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `deo.virtual.RainbowBot_1.0.jar` has SHA-256 `3854b1a74b34f26b4fccfc997b5faadb4db60177cd48857d8bdba79e63f5bba4`. The official confirmation `0216591340b75295` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `645bde45291f103b923d448617e5535da5015d28`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −48.1%, −45.7%, −49.6%, −39.9%, and −54.8%. Classic averaged 6,534.6 points and Tank Royale averaged 3,420.6, for a −47.62% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `38ab74151e9be2ed` and `ec739c27975085dd` recorded −51.9% and −42.9% single-pair deltas without runtime errors. The current five-pair mean agrees with both in direction and magnitude.

## Finding

RainbowBot's Classic score advantage is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dft.Calliope_5.6.jar` (`score-review`).

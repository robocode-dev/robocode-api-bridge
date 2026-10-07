---
id: AN-166
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Calliope's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-166 — Calliope's negative score gap is confirmed across five pairs

## Risk investigated

Whether Calliope's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dft.Calliope_5.6.jar` has SHA-256 `dee2ec44341ba6c2574bf995c317b4f806d3189f19ab040f8565b487dab5248a`. The official confirmation `4da845144515e888` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `a0a0b5281e9cd800926c06db2e109707036c4bdc`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −24.5%, −35.9%, −31.1%, −21.6%, and −28.0%. Classic averaged 8,663 points and Tank Royale averaged 6,219.8, for a −28.22% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `1922e70abf214431` recorded a −37.7% single-pair delta without runtime errors. The current five-pair mean agrees with it in direction.

## Finding

Calliope's Classic score advantage is confirmed with current matched artifacts and agrees with its earlier observation. No runtime errors or skipped turns were observed. The score measurements do not establish the behavioral cause of the difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dft.Freddie_1.32.jar` (`score-review`).

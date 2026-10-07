---
id: AN-164
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: CloudBot's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-164 — CloudBot's negative score gap is confirmed across five pairs

## Risk investigated

Whether CloudBot's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `deo.CloudBot_1.3.jar` has SHA-256 `d62f0561529c81e5da65ad44c4c6db8b31dceca043ec2d9fcc4646aa15ed28f3`. The official confirmation `12d5843a8fce4435` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `fef3dcef8aa982bacbcffb9efead04369ee20fbf`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −51.8%, −23.8%, −20.3%, −25.4%, and −32.6%. Classic averaged 5,722.4 points and Tank Royale averaged 3,931.2, for a −30.78% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `78bba33b96e662cd` and `602f15d5ee46510e` recorded −48.6% and −29.9% single-pair deltas without runtime errors. The current five-pair mean agrees with both in direction and clears the confirmation threshold.

## Finding

CloudBot's Classic score advantage is confirmed with current matched artifacts. The pair results vary, but the current mean remains below Tank Royale by 30.78%. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the gap. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/deo.virtual.RainbowBot_1.0.jar` (`score-review`).

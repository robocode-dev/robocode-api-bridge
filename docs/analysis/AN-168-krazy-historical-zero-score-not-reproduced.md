---
id: AN-168
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Krazy's historical Tank Royale zero score does not recur
provenance: inferred
reversal-cost: low
---

# AN-168 — Krazy's historical Tank Royale zero score does not recur

## Risk investigated

Whether Krazy's historical zero Tank Royale score and large score discrepancy recur with current matched artifacts.

## Evidence boundary

The read-only subject jar `dft.Krazy_1.5.jar` has SHA-256 `0daebd3dfdc2b82bc332c3ea31b9286b2da388667ce4caaacb4e237d4a1f8634`. The official confirmation `b6880d37b361d669` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `d66066be6592630bebdf5a61a10d892b58359fc6`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −8.5%, +5.1%, −5.4%, −5.4%, and +5.6%. Classic averaged 5,828 points and Tank Royale averaged 5,722.8, for a −1.72% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `MATCHED (score noise)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `164440b187073126` recorded a Classic score of 6,105 and a Tank Royale score of 0, a −100% delta, without runtime errors. The current five-pair mean does not reproduce that result.

## Finding

Krazy's historical Tank Royale zero score does not recur with current matched artifacts; the current mean is within the score-noise band. No runtime errors or skipped turns were observed. The measurements do not identify the cause of the earlier zero score. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dggp.haiku.gpBot_0_1.1.jar` (`score-review`).

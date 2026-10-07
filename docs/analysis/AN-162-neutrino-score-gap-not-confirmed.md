---
id: AN-162
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Neutrino's historical score gap does not clear the confirmation band
provenance: inferred
reversal-cost: low
---

# AN-162 — Neutrino's historical score gap does not clear the confirmation band

## Risk investigated

Whether Neutrino's historical Classic score advantage persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `demetrix.nano.Neutrino_0.27.jar` has SHA-256 `3ba40e81b6908456aad702dd6e8481f33d07898ac773a1307a20563157942a23`. The official confirmation `0b83a1386dac6d7f` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `12a5e5c2c6ad887a20a766b800abfcdb26252a17`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −10.9%, −10.6%, −23.3%, −10.4%, and −8.1%. Classic averaged 7,596 points and Tank Royale averaged 6,621, for a −12.66% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `MATCHED (score noise)`; the mean is below the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `c87a4b8d228674d0` and `c6d106d809914110` recorded −17.5% and −28.2% single-pair deltas without runtime errors. The current five-pair mean remains negative but does not confirm a score discrepancy at the 15-point confirmation band.

## Finding

Neutrino's current mean score difference is within the score-noise band, despite one −23.3% pair. The historical −28.2% result is not confirmed by the current five-pair mean. No runtime errors or skipped turns were observed, and the measurements do not identify the cause of the variation. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/demetrix.nano.SledgeHammer_0.22.jar` (`score-review`).

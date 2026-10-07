---
id: AN-163
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: SledgeHammer's positive score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-163 — SledgeHammer's positive score gap is confirmed across five pairs

## Risk investigated

Whether SledgeHammer's historical Tank Royale score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `demetrix.nano.SledgeHammer_0.22.jar` has SHA-256 `8ca91c5c9cbe37fbd67f136a38ac4be45c2f8b9eb470367957c3fe20e0edd3cd`. The official confirmation `51d7d3600b6ffb0f` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `e6248ce5518b8cade6016ea8df2185502557eb3c`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were +30.1%, +28.6%, +25.2%, +30.6%, and +31.1%. Classic averaged 10,558 points and Tank Royale averaged 13,633.6, for a +29.12% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`; the mean is just above the 25% confirmation threshold.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `804cc154798fef47` and `2dd6c1ce765e2145` recorded +58.1% and +62.3% single-pair deltas without runtime errors. The current positive gap is smaller but remains above the confirmation threshold.

## Finding

SledgeHammer's Tank Royale score advantage is confirmed with current matched artifacts and is consistent in direction with both earlier observations. No runtime errors or skipped turns were observed. The smaller current gap does not identify the behavior responsible for the difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/deo.CloudBot_1.3.jar` (`score-review`).

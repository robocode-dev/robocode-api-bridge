---
id: AN-158
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DuelistMicro's positive score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-158 — DuelistMicro's positive score gap is confirmed across five pairs

## Risk investigated

Whether DuelistMicro's historical Tank Royale score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `davidalves.net.DuelistMicro_1.22.jar` has SHA-256 `e51b32ab504ef17c0bcd47bb7953ccdd07f7e050b0fa1a3478e63281eaa0fe2a`. The official confirmation `22e68bab3c4a18a0` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `46c81253b799225e3c20760e8b6bad08d94459da`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were +72.2%, +54.9%, +54.5%, +51.2%, and +52.5%. Classic averaged 5,223 points and Tank Royale averaged 8,193, for a +57.06% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `4d34a6e0b2152b18` and `c28e90450fc99ae5` recorded +69.6% and +53.5% single-pair deltas. They were not treated as confirmation; the current official five-pair protocol was used to check whether the gap persists.

## Finding

DuelistMicro's positive score discrepancy is confirmed with current matched artifacts and agrees with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davv.DOne_b002.jar` (`score-review`).

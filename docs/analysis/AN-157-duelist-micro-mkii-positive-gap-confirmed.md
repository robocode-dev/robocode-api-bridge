---
id: AN-157
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DuelistMicroMkII's positive score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-157 — DuelistMicroMkII's positive score gap is confirmed across five pairs

## Risk investigated

Whether DuelistMicroMkII's historical Tank Royale score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `davidalves.net.DuelistMicroMkII_1.1.jar` has SHA-256 `0d3e784acde7d8156090280241668a002f890ee6d5d7fe6dadc971787f67b15e`. The official confirmation `da9175ceb5e99555` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `49fb4ae7f16dff55257bddc7e29b8652fc0a6a63`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were +96.0%, +117.3%, +114.9%, +104.5%, and +125.1%. Classic averaged 5,215.6 points and Tank Royale averaged 11,028.6, for a +111.56% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `34cf1f962dcb1bcd` and `40198e0e594d02d6` recorded +138.5% and +117.4% single-pair deltas without runtime errors. They were not treated as confirmation; the current official five-pair protocol was used to check whether the gap persists.

## Finding

DuelistMicroMkII's positive score discrepancy is confirmed with current matched artifacts and agrees with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.net.DuelistMicro_1.22.jar` (`score-review`).

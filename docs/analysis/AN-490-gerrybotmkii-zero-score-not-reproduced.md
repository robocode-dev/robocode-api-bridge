---
id: AN-490
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: GerryBotMkII's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-490 — GerryBotMkII's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gwah.GerryBotMkII_1.5.1.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `5fd6745b4f1cde058c3d7f173f9604363a5987982c481936bf7921008da4d636`. The official five-pair confirmation `0c876193c2ebc876` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `7e162246815d55f8c76efbb7094d55a68d60f8fa`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 12,382.4 points and Tank Royale averaged 11,792.6 points, for a −4.76% mean delta. Pair deltas were −4.4%, −7.4%, −4.6%, −1.8%, and −5.6%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `f10a31cfcd02142e` recorded Classic scores of 0 and 0 and Tank Royale scores of 6,073 and 5,829, with no runtime errors. The current run produced scores from both engines in every attempt. Its −4.76% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

GerryBotMkII's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −4.76%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gwah.GerryBotMkII_1.5.1.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/ha2.T2_0.2.jar` (`DISCREPANCY (no score)`).

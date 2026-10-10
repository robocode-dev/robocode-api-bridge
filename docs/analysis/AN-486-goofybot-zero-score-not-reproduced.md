---
id: AN-486
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: GoofyBot's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-486 — GoofyBot's earlier Classic zero score is not reproduced

## Risk investigated

Whether `grybgoofy.GoofyBot_0.10.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `483a31a648d051d9a856f78f266243a6b0c6ffe473100a85c7b3f8fae519578f`. The official five-pair confirmation `442cfb38bbd3cd70` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `7e162246815d55f8c76efbb7094d55a68d60f8fa`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,121.8 points and Tank Royale averaged 4,862.8 points, for a −5.02% mean delta. Pair deltas were −7.1%, −2.8%, −2.9%, −5.9%, and −6.4%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `8ca01ec24f3ecc76` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,612 and 2,073, with no runtime errors. The current run produced scores from both engines in every attempt. Its −5.02% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

GoofyBot's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −5.02%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/grybgoofy.GoofyBot_0.10.jar` and `roborumble/gtf.robocode.Strafer_2.1.1.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gu.MicroScoob_1.3.jar` (`DISCREPANCY (no score)`).

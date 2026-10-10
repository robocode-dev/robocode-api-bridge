---
id: AN-492
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: T2b's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-492 — T2b's earlier Classic zero score is not reproduced

## Risk investigated

Whether `ha2.T2b_0.2b.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `5f54c1a8bf8e4ed7d8ccc358241310fcebaff1f037156ec3729ac58a0df80b17`. The official five-pair confirmation `3ad015881eb94925` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea7f653a103424543763ad493b0b24ed9e940409`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,918.2 points and Tank Royale averaged 5,422.0 points, for a −8.42% mean delta. Pair deltas were −11.4%, −7.9%, −4.8%, −7.9%, and −10.1%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `15ea51b55ba7e79a` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,860 and 2,574, with no runtime errors. The current run produced scores from both engines in every attempt. Its −8.42% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

T2b's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −8.42%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/ha2.T2b_0.2b.jar` (`MATCHED (score noise)`) and `roborumble/ha2.T3_0.1.jar` (`PASS`). Record `roborumble/ha2.T3_0.2.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/hfgrobots.HFG_1.0.jar` (`score-review`).

---
id: AN-468
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Rinmorikazu's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-468 — Rinmorikazu's earlier Classic zero score is not reproduced

## Risk investigated

Whether `genprog.Rinmorikazu_1.0.jar`'s historical Classic zero-score result persists under the latest matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `3d869dbadee876e6a4d3006d6a10939fec114378316e15df130e3047ac917f80`. The official five-pair confirmation `2f6d9af766f83837` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `f1e3d67ca92799db2af4a4eb06ffffa16ab0e022`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used different bridge and Tank Royale commits and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,690.4 points and Tank Royale averaged 5,476.6 points, for a −3.58% mean delta. Pair deltas were +5.4%, −1.1%, −7.2%, −1.2%, and −13.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `e8721bfe1878ce0b` recorded a Classic score of 0 and a Tank Royale score of 5,115 with no runtime errors. The current run produced scores from both engines in every attempt. Its −3.58% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Rinmorikazu's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −3.58%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/genprog.Rinmorikazu_1.0.jar` and `roborumble/genprog.Zafaran_1.0.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/germ.TheMind_.2.jar` (`DISCREPANCY (no score)`).

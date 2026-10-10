---
id: AN-467
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: LittleAngel's earlier zero-score result is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-467 — LittleAngel's earlier zero-score result is not reproduced

## Risk investigated

Whether `florent.small.LittleAngel_1.8.jar`'s historical Classic zero-score outcome persists under the latest matched artifacts, and whether five current pairs show a material score difference.

## Evidence boundary

The read-only subject jar has SHA-256 `9dff2b2cb39fa54f0ab5b30a891f53005ef1a127e859963a4c40f3e42c24e6d5`. The official five-pair confirmation `7d84993039ca74df` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `f1e3d67ca92799db2af4a4eb06ffffa16ab0e022`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and runner artifacts. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,490.2 points and Tank Royale averaged 4,231.0 points, for a −5.64% mean delta. Pair deltas were +2.6%, −15.1%, −6.4%, −7.8%, and −1.5%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `3f8326a84f0bd900` recorded 0 points in Classic and 4,109 in Tank Royale, with no runtime errors; its score status was `DISCREPANCY (no score)`. The current five-pair result has scores from both engines on every attempt and a −5.64% mean delta. The five-pair classification uses the 15-point absolute mean-delta band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The change from a Classic zero score to positive scores was not attributed to the bridge, Tank Royale, or LittleAngel. The old observation used different artifacts, and no controlled trace or source comparison was made. No code or rumble-jar change was made.

## Finding

LittleAngel's prior zero-score discrepancy did not recur with current matched artifacts. All five pairs produced scores, the −5.64% mean delta is within the 15-point five-pair band, and the registry status is `MATCHED (score noise)`. The retest does not explain the earlier Classic zero score.

## M-006 handoff

Skip `roborumble/genprog.Rinmorikazu_1.0.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/genprog.Zafaran_1.0.jar` (`DISCREPANCY (no score)`).

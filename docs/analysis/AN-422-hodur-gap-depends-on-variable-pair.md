---
id: AN-422
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-124, AN-260]
title: Hodur's negative score gap persists but depends on a volatile pair
provenance: inferred
reversal-cost: low
---

# AN-422 — Hodur's negative score gap persists but depends on a volatile pair

## Risk investigated

Whether `bvh.hdr.Hodur_0.4.jar`'s previously confirmed negative score gap persists under the latest matched artifacts, and whether the five-pair mean depends on one unusually low Tank Royale score.

## Evidence boundary

The read-only subject jar has SHA-256 `2bd6a9804697badcfad118f8c84db756b33cd9154144da4b19166f9f7caea94e`. The official five-pair confirmation `676cd1ce3c1c1cd7` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ccb3203e88ad4eb1864704e890366eb04c43fcff`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 1,536.2 points and Tank Royale averaged 989.2 points, for a −27.82% mean delta. The five pair deltas were −45.5%, −10.7%, +9.2%, −87.2%, and −4.9%. The registry status remains `CONFIRMED (score)`. Excluding the −87.2% pair, the other four average −12.98%, within the 15-point confirmation band, so the classification depends on that unusually low Tank Royale result.

Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. AN-260's prior confirmation under newer artifacts had a −42.8% mean delta, with four negative pairs and one positive pair. AN-124 had previously recorded a −51.64% mean. The current mean is smaller in magnitude and the pair variation remains high.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Hodur from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Hodur's five-pair mean remains confirmed at −27.82%, but it depends on one −87.2% pair: the other four average −12.98%, inside the confirmation band. The current run reported no engine errors or skipped-turn events and did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bvh.loki.Loki_0.5.jar` (`CONFIRMED (score)`).
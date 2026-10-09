---
id: AN-423
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-125, AN-261]
title: Loki's negative score gap persists across all five pairs
provenance: inferred
reversal-cost: low
---

# AN-423 — Loki's negative score gap persists across all five pairs

## Risk investigated

Whether `bvh.loki.Loki_0.5.jar`'s confirmed negative score gap persists under the latest matched artifacts, and whether one unusually low pair determines the five-pair result.

## Evidence boundary

The read-only subject jar has SHA-256 `46f15811b1cbdfa571eca677320bd0efca497cfb6ed6b672e82f8c27dfb3f0c6`. The official five-pair confirmation `19f14a1f3023bff4` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ccb3203e88ad4eb1864704e890366eb04c43fcff`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 9,130.8 points and Tank Royale averaged 7,510.2 points, for a −17.6% mean delta. The five pair deltas were −18.4%, −23.8%, −13.4%, −17.1%, and −15.3%. The registry status remains `CONFIRMED (score)`; all pairs favored Classic, and the mean remains beyond the 15-point confirmation band even excluding the least negative pair.

Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. AN-261's preceding five-pair confirmation on 2026-10-08 had a −19.06% mean delta, also with all five pairs negative. The latest result keeps the same direction with a slightly smaller mean difference.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Loki from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Loki's lower Tank Royale score remains confirmed at −17.6%, close to AN-261's −19.06%. All five pairs favored Classic, and the current run reported no engine errors or skipped-turn events. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bwbaugh.nano.Tirunculus_0.0.0a.jar` (`CONFIRMED (score)`).
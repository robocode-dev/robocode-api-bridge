---
id: AN-461
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-178, AN-304]
title: Jezza's Classic score advantage persists at a similar magnitude
provenance: inferred
reversal-cost: low
---

# AN-461 — Jezza's Classic score advantage persists at a similar magnitude

## Risk investigated

Whether `donjezza.Jezza_1.0.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-304.

## Evidence boundary

The read-only subject jar has SHA-256 `1188605236143d82517db214ae55e4881181a3c88245d6f498dfd9a1f5fd9de5`. The official five-pair confirmation `c3fd7a400079f213` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `89f1aa92da35b79287488a5a9ce63be021d752d7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,795.2 points and Tank Royale averaged 5,897.4 points, for a −24.34% mean delta. The five pair deltas were −26.3%, −24.0%, −24.0%, −23.6%, and −23.8%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-304's preceding five-pair confirmation had a −24.86% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage at a similar magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Jezza from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Jezza's Classic score advantage remains confirmed at −24.34%, close to AN-304's −24.86%. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/donjezza.Muncho_1.0.jar` (`CONFIRMED (score)`).

---
id: AN-458
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-175, AN-301]
title: Aurora's Classic score advantage persists with a slightly smaller gap
provenance: inferred
reversal-cost: low
---

# AN-458 — Aurora's Classic score advantage persists with a slightly smaller gap

## Risk investigated

Whether `dmp.micro.Aurora_1.41.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-301.

## Evidence boundary

The read-only subject jar has SHA-256 `0550ff35cc6fd581a7469d3378ccf17ae5f3fb00888c56858ed3b54f515366e9`. The official five-pair confirmation `11f1c8a7b9ccc028` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1bd9c767df8f1312a76bb41b31db858526cae426`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,352.2 points and Tank Royale averaged 2,780.8 points, for a −35.96% mean delta. The five pair deltas were −34.9%, −44.6%, −27.3%, −38.9%, and −34.1%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-301's preceding five-pair confirmation had a −39.30% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a smaller mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Aurora from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Aurora's Classic score advantage remains confirmed at −35.96%, compared with −39.30% in AN-301. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/dmp.nano.Eve_3.41.jar` (`CONFIRMED (score)`).

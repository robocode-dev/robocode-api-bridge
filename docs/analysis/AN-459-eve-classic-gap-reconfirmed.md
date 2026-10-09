---
id: AN-459
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-176, AN-302]
title: Eve's Classic score advantage persists with a slightly larger gap
provenance: inferred
reversal-cost: low
---

# AN-459 — Eve's Classic score advantage persists with a slightly larger gap

## Risk investigated

Whether `dmp.nano.Eve_3.41.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-302.

## Evidence boundary

The read-only subject jar has SHA-256 `edd8e3e4a88e6abb36bba0d2241c3b7d03dda39619f4f9264e4b4757f1eb2da9`. The official five-pair confirmation `dcd21d8f5a627505` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1bd9c767df8f1312a76bb41b31db858526cae426`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 3,024.2 points and Tank Royale averaged 1,790.8 points, for a −40.32% mean delta. The five pair deltas were −33.1%, −45.9%, −41.3%, −47.3%, and −34.0%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-302's preceding five-pair confirmation had a −38.16% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a slightly larger mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Eve from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Eve's Classic score advantage remains confirmed at −40.32%, compared with −38.16% in AN-302. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/doka.ShinigamiKNN_1.0.jar` (`DISCREPANCY (outcome)`).

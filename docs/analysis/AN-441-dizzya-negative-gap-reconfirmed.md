---
id: AN-441
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-152, AN-281]
title: DizzyA's large lower Tank Royale score persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-441 — DizzyA's large lower Tank Royale score persists across five current pairs

## Risk investigated

Whether `daemons.DizzyA_1.0.jar`'s large negative score gap persists under the latest matched artifacts, and whether its magnitude materially changes.

## Evidence boundary

The read-only subject jar has SHA-256 `aeba11ca1cd50603c4b74166f04dea30238c77faead6f6830874571bea4091ad`. The official five-pair confirmation `a8342f198b07b331` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `78039932ddf7bbb1474c68bd4ffe2df72514d329`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 11,802.8 points and Tank Royale averaged 4,122.8 points, for a −64.9% mean delta. The five pair deltas were −69.0%, −54.5%, −72.1%, −63.5%, and −65.4%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-281's preceding five-pair confirmation on 2026-10-08 had a −67.5% mean delta, with all pairs negative and no errors or skipped-turn events. The current result reproduces the large Classic advantage with a slightly smaller mean gap.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or DizzyA from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

DizzyA's lower Tank Royale score remains confirmed at −64.9%, compared with −67.5% in AN-281. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/dam.MogBot_2.9.jar` (`DISCREPANCY (outcome)`).
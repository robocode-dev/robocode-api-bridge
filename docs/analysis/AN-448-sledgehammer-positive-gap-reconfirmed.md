---
id: AN-448
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-163, AN-291]
title: SledgeHammer's positive Tank Royale score gap persists
provenance: inferred
reversal-cost: low
---

# AN-448 — SledgeHammer's positive Tank Royale score gap persists

## Risk investigated

Whether `demetrix.nano.SledgeHammer_0.22.jar`'s confirmed Tank Royale score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-291.

## Evidence boundary

The read-only subject jar has SHA-256 `8ca91c5c9cbe37fbd67f136a38ac4be45c2f8b9eb470367957c3fe20e0edd3cd`. The official five-pair confirmation `85b7a7a84ba63c5` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `de3a59833f71e25c07e3fd9fe614747caffa36a7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 10,508.6 points and Tank Royale averaged 16,672.6 points, for a +58.68% mean delta. The five pair deltas were +59.9%, +60.7%, +57.9%, +57.2%, and +57.7%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-291's preceding five-pair confirmation had a +57.62% mean delta, with all five pairs positive and no errors or skipped-turn events. The current result reproduces the positive direction at a similar magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or SledgeHammer from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

SledgeHammer's Tank Royale score advantage remains confirmed at +58.68%, compared with +57.62% in AN-291. All five pairs favored Tank Royale, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/deo.CloudBot_1.3.jar` (`CONFIRMED (score)`).

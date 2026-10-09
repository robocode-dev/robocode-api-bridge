---
id: AN-439
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-150, AN-279]
title: Smog's lower Tank Royale score persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-439 — Smog's lower Tank Royale score persists across five current pairs

## Risk investigated

Whether `cx.nano.Smog_2.6.jar`'s previously confirmed negative score gap persists under the latest matched artifacts, and whether its magnitude changes materially.

## Evidence boundary

The read-only subject jar has SHA-256 `1bdea7a9d116c385e6d7dcd12014dc80270f619cf2b20046c5d85640aeda4bbf`. The official five-pair confirmation `440a62d4e632af1c` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `70f381da5890c929600cc50696b66d5a4a0d590b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,293.0 points and Tank Royale averaged 2,911.4 points, for a −31.1% mean delta. The five pair deltas were −45.1%, −17.2%, −35.9%, −25.2%, and −32.1%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-279's preceding five-pair confirmation on 2026-10-08 had a −33.62% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a slightly smaller mean difference.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Smog from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Smog's lower Tank Royale score remains confirmed at −31.1%, compared with −33.62% in AN-279. All five pairs favored Classic; no runtime errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/da.NewBGank_1.4.jar` (`CONFIRMED (score)`).
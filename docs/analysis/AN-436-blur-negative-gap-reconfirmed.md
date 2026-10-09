---
id: AN-436
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-147, AN-276]
title: Blur's large negative score gap persists with a complete error-free confirmation
provenance: inferred
reversal-cost: low
---

# AN-436 — Blur's large negative score gap persists with a complete error-free confirmation

## Risk investigated

Whether `cx.micro.Blur_0.2.jar`'s large negative score gap persists under the latest matched artifacts, and whether the prior null-wave interruption recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `a1d62b084d88196ff938d3f189e0f7a84f89e7f034bd21f83a8afb0bc20ba979`. The official five-pair confirmation `4bd0450682006c1a` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `70f381da5890c929600cc50696b66d5a4a0d590b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,667.2 points and Tank Royale averaged 1,712.4 points, for a −77.58% mean delta. The five pair deltas were −79.7%, −78.7%, −74.9%, −73.8%, and −80.8%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-276's preceding five-pair confirmation on 2026-10-08 had a −74.78% mean delta and did not reproduce AN-147's earlier null-wave interruption. The current confirmation again completes without errors and reproduces the large negative score gap, with a slightly larger magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Blur from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Blur's lower Tank Royale score remains confirmed at −77.58%, compared with −74.78% in AN-276. All five pairs favor Classic, and the current retest has no runtime errors or skipped-turn events. The score-gap cause remains open.

## M-006 handoff

Continue in registry order with `roborumble/cx.micro.Spark_0.6.jar` (`CONFIRMED (score)`).
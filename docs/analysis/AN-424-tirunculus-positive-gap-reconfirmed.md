---
id: AN-424
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-126, AN-262]
title: Tirunculus's large positive score gap remains tightly grouped
provenance: inferred
reversal-cost: low
---

# AN-424 — Tirunculus's large positive score gap remains tightly grouped

## Risk investigated

Whether `bwbaugh.nano.Tirunculus_0.0.0a.jar`'s previously confirmed positive score gap persists under the latest matched artifacts, and whether one pair determines the result.

## Evidence boundary

The read-only subject jar has SHA-256 `d1fcb6072a0110947df39ad0db284f0fa4f229e7ef0f0d2502553ca0b2acb7d4`. The official five-pair confirmation `1a0cf6f284b60cd1` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ccb3203e88ad4eb1864704e890366eb04c43fcff`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 10,878.0 points and Tank Royale averaged 16,546.6 points, for a +52.14% mean delta. The five pair deltas were +51.0%, +49.6%, +56.3%, +51.1%, and +52.7%. The registry status remains `CONFIRMED (score)`. Excluding the largest pair, the other four average +51.1%, so one pair does not determine the confirmation.

Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. AN-262's preceding five-pair confirmation on 2026-10-08 had a +50.18% mean delta, also with tightly grouped positive pairs. The latest result reproduces that large score advantage under newer matched artifacts.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Tirunculus from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Tirunculus's higher Tank Royale score remains confirmed at +52.14%, close to AN-262's +50.18%. All five pairs were tightly grouped and positive; the current run reported no engine errors or skipped-turn events. The retest did not identify the score-gap cause.

## M-006 handoff

Skip `roborumble/cb.Domogled_1.2.jar`, whose current registry status is `PASS`. Continue with `roborumble/cb.fire.Firestarter_2.0f.jar` (`DISCREPANCY (outcome)`).
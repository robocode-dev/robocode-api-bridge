---
id: AN-411
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-110]
title: FourWD's lower Tank Royale score is reconfirmed under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-411 — FourWD's lower Tank Royale score is reconfirmed under newer artifacts

## Risk investigated

Whether `ary.FourWD_1.3d.jar`'s previously confirmed lower Tank Royale score persists under the latest matched artifacts, and whether the earlier Classic null-target exception recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `bfe8768af3401df35cf4d4ec8e3e1d47d779cc5dfc284b212fda3153dbd21537`. The official five-pair confirmation `4bb77aad64541dde` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `26f82bcb83360debf53b9fc0310daef363359600`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,244.0 points and Tank Royale averaged 3,548.2 points, for a −32.32% mean delta. The five pair deltas were −34.1%, −37.1%, −32.3%, −28.8%, and −29.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status remains `CONFIRMED (score)`.

The preceding five-pair observation `d809601a19bd3eed` on 2026-10-06 had a −36.5% mean delta; this result reconfirms a similar score gap on the newer matched artifacts. AN-110 diagnosed the earlier `ary.FourWD.run` null-target exception as robot-owned; that error did not appear in the current five-pair report, and it does not explain the score difference.

## What was not pursued

The repeated score difference does not locate the divergent behavior, so no score-gap cause was assigned to the bridge, Tank Royale, or FourWD. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

FourWD's lower Tank Royale score remains confirmed at −32.32%, compared with −36.5% in the preceding five-pair result. The earlier robot-owned null-target error did not recur, and the current measurement does not explain the score gap.

## M-006 handoff

Continue in registry order with `roborumble/ary.Help_1.0.jar` (`CONFIRMED (score)`).

---
id: AN-418
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-256]
title: Shooter's lower Tank Royale score is reconfirmed under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-418 — Shooter's lower Tank Royale score is reconfirmed under newer artifacts

## Risk investigated

Whether `bk.Shooter_1.0.jar`'s previously confirmed lower Tank Royale score persists under the latest matched artifacts, and whether the retest identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `0dfe57a84edd868ba0517b270c817bfdc9765139b6517fe4c7868110cf9c0e16`. The official five-pair confirmation `3791db92f68beded` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d64e76ce2a011143165c5f032d81e221f656b580`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,284.6 points and Tank Royale averaged 4,981.2 points, for a −31.46% mean delta. The five pair deltas were −37.3%, −29.0%, −30.1%, −24.6%, and −36.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status remains `CONFIRMED (score)`.

The preceding five-pair confirmation `ac385a756b0f2146` on 2026-10-07 had a −31.54% mean delta; AN-118's earlier confirmation was −28.36%. The current result reproduces the same lower Tank Royale score under newer matched artifacts. AN-256 leaves the behavioral cause open, and this aggregate retest does not locate it.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Shooter from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Shooter's lower Tank Royale score remains confirmed at −31.46%, nearly unchanged from −31.54% in the preceding confirmation. The current run reported no errors or skipped-turn events and did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/blir.nano.Cabbage_R1.0.1.jar` (`CONFIRMED (score)`).

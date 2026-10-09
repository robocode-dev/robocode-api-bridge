---
id: AN-419
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-257]
title: Cabbage's large Tank Royale score advantage is reconfirmed under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-419 — Cabbage's large Tank Royale score advantage is reconfirmed under newer artifacts

## Risk investigated

Whether `blir.nano.Cabbage_R1.0.1.jar`'s previously confirmed higher Tank Royale score persists under the latest matched artifacts, and whether the retest identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `6048d93ca6c18440d2442930806a4edb783d6bc3e7b019f324fad7aebaccb222`. The official five-pair confirmation `7e6074445726042b` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d64e76ce2a011143165c5f032d81e221f656b580`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 1,402.4 points and Tank Royale averaged 3,033.2 points, for a +116.64% mean delta. The five pair deltas were +134.3%, +106.8%, +118.3%, +101.0%, and +122.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status remains `CONFIRMED (score)`.

The preceding confirmation `3e06224b7006e720` on 2026-10-07 had a +137.24% mean delta; AN-119's earlier mean was +80.78%. The current result remains strongly positive across all five pairs but is smaller than the preceding mean. AN-257 leaves the behavioral cause open, and this aggregate retest does not locate it.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Cabbage from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Cabbage's higher Tank Royale score remains confirmed at +116.64%, compared with +137.24% in the preceding five-pair result. The current run reported no errors or skipped-turn events and did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bons.NanoStalker_1.2.jar` (`CONFIRMED (score)`), skipping `roborumble/boe.Minerva_0.80.jar` (`PASS`).

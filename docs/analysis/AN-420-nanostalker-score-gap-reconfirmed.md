---
id: AN-420
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-258]
title: NanoStalker's large lower Tank Royale score is reconfirmed under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-420 — NanoStalker's large lower Tank Royale score is reconfirmed under newer artifacts

## Risk investigated

Whether `bons.NanoStalker_1.2.jar`'s previously confirmed lower Tank Royale score persists under the latest matched artifacts, and whether the retest identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `25169213aa301260dbe320aab5011618b95dd7283102fc6272cdd2e5afdc0936`. The official five-pair confirmation `4f44528661f929b7` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ccb3203e88ad4eb1864704e890366eb04c43fcff`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,764.2 points and Tank Royale averaged 2,800.6 points, for a −63.9% mean delta. The five pair deltas were −62.3%, −65.4%, −66.0%, −63.5%, and −62.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status remains `CONFIRMED (score)`.

The preceding five-pair confirmation `0e40287063d26abe` on 2026-10-07 had a −63.16% mean delta; the 2026-10-06 confirmation `5016c357e02add03` had −71.98%. The current result reconfirms a similarly large lower Tank Royale score under newer matched artifacts. AN-258 leaves the behavioral cause open, and this aggregate retest does not locate it.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or NanoStalker from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

NanoStalker's lower Tank Royale score remains confirmed at −63.9%, nearly unchanged from −63.16% in the preceding five-pair result. The current run reported no errors or skipped-turn events and did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bp.Kuma_1.0.jar` (`CONFIRMED (score)`).

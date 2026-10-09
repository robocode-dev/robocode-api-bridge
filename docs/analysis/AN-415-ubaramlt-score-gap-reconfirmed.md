---
id: AN-415
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-253]
title: UbaRamLT's higher Tank Royale score is reconfirmed under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-415 — UbaRamLT's higher Tank Royale score is reconfirmed under newer artifacts

## Risk investigated

Whether `bayen.UbaRamLT_1.0.jar`'s previously confirmed higher Tank Royale score persists under the latest matched artifacts, and whether the retest identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `604d19bdcfa6caa05964df927bb24e237e8f7aaa1fcbd7d13aea4e4d9b5e028b`. The official five-pair confirmation `dd63ea259e657b36` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d64e76ce2a011143165c5f032d81e221f656b580`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 10,902.4 points and Tank Royale averaged 14,173.4 points, for a +30.02% mean delta. The five pair deltas were +28.6%, +32.3%, +26.7%, +32.5%, and +30.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status remains `CONFIRMED (score)`.

The preceding five-pair confirmation `351ec7438a88bb7f` on 2026-10-07 had a +27.4% mean delta; the earlier confirmation `f0b9374e244abc40` had +19.88%. The current result reconfirms the higher Tank Royale score under newer matched artifacts. AN-253 leaves the behavioral cause open; this aggregate retest does not locate the divergent behavior.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or UbaRamLT from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

UbaRamLT's higher Tank Royale score remains confirmed at +30.02%, compared with +27.4% in the preceding five-pair result. The current run recorded no runtime errors or skipped-turn events and does not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bayen.nut.Squirrel_1.621.jar` (`CONFIRMED (score)`).

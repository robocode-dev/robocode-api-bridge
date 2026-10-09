---
id: AN-417
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-255]
title: RamboT's higher score remains confirmed without relying on one pair
provenance: inferred
reversal-cost: low
---

# AN-417 — RamboT's higher score remains confirmed without relying on one pair

## Risk investigated

Whether `bbo.RamboT_0.3.jar`'s previously confirmed higher Tank Royale score persists under the latest matched artifacts, and whether one unusually high-delta pair determines the confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `3c933f89750dfd882f03844f5fc83ae4bf36d5a6baca855251417b4a1457e6ed`. The official five-pair confirmation `cd41c475dc12cb16` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d64e76ce2a011143165c5f032d81e221f656b580`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 9,506.4 points and Tank Royale averaged 12,522.8 points, for a +32.44% mean delta. The five pair deltas were +46.8%, +28.9%, +18.5%, +35.1%, and +32.9%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status remains `CONFIRMED (score)`.

The preceding five-pair confirmation `a6c1692a3cac5b07` on 2026-10-07 had a +35.7% mean delta; AN-116's earlier result was +20.74%. Excluding the current largest pair (+46.8%), the other four average +28.85%, still beyond the 15.0-point confirmation band. The positive score gap therefore does not depend on one unusually high pair. AN-255 leaves the behavioral cause open, and this aggregate retest does not locate it.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or RamboT from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

RamboT's higher Tank Royale score remains confirmed at +32.44%, compared with +35.7% in the preceding confirmation. Even excluding the largest current pair, the other four average +28.85%; the result is not dependent on that pair. The current run reported no errors or skipped-turn events and did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bk.Shooter_1.0.jar` (`CONFIRMED (score)`).

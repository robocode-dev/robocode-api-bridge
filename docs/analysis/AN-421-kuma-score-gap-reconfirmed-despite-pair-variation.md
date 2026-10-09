---
id: AN-421
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-259]
title: Kuma's higher Tank Royale score remains confirmed despite pair variation
provenance: inferred
reversal-cost: low
---

# AN-421 — Kuma's higher Tank Royale score remains confirmed despite pair variation

## Risk investigated

Whether `bp.Kuma_1.0.jar`'s previously confirmed higher Tank Royale score persists under the latest matched artifacts, and whether one unusual pair determines the confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `2646c78e817ac3701fbd23306259844fdea377b82ac7748f8d3cf6ea36b4825e`. The official five-pair confirmation `3772195e7ddcb610` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ccb3203e88ad4eb1864704e890366eb04c43fcff`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 3,646.0 points and Tank Royale averaged 5,158.4 points, for a +42.64% mean delta. The five pair deltas were +8.3%, +84.9%, +46.6%, +39.7%, and +33.7%. The mean remains outside the confirmation band and the registry status is `CONFIRMED (score)`. Excluding the largest pair, the other four average +32.08%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

The preceding five-pair confirmation `e042013c498f7fd6` on 2026-10-07 had a +58.72% mean delta; AN-122's earlier confirmation had +53.12%. The current score advantage remains positive but is smaller than the preceding result. AN-259 leaves its behavioral cause open, and this aggregate retest does not locate it.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Kuma from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Kuma's higher Tank Royale score remains confirmed at +42.64%, compared with +58.72% in the preceding result. Although pair values vary, the mean remains beyond the confirmation band even without the largest pair. The current run reported no errors or skipped-turn events and did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/bvh.hdr.Hodur_0.4.jar` (`CONFIRMED (score)`), skipping `roborumble/bts.wiki.RipCurl_0.9b.jar` (`PASS`).

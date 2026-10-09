---
id: AN-438
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-149, AN-278]
title: Nimrod's large positive score gap persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-438 — Nimrod's large positive score gap persists across five current pairs

## Risk investigated

Whether `cx.mini.Nimrod_0.55.jar`'s large positive Tank Royale score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `9ccc75f18f1ea296fefe441150fd552e546989e1e42af4abdc578f297564a321`. The official five-pair confirmation `507b97aeca90e5dc` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `70f381da5890c929600cc50696b66d5a4a0d590b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,025.4 points and Tank Royale averaged 11,964.8 points, for a +138.26% mean delta. The five pair deltas were +126.4%, +145.4%, +130.2%, +151.5%, and +137.8%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-278's preceding five-pair confirmation on 2026-10-08 had a +141.14% mean delta, also with five positive pairs, no errors, and empty skipped-turn telemetry. The current result reproduces the large score gap with a slightly smaller mean.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Nimrod from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Nimrod's higher Tank Royale score remains confirmed at +138.26%, close to AN-278's +141.14%. All five pairs favored Tank Royale, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/cx.nano.Smog_2.6.jar` (`CONFIRMED (score)`).
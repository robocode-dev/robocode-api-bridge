---
id: AN-432
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-139, AN-271]
title: Idem's lower Tank Royale score remains confirmed with newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-432 — Idem's lower Tank Royale score remains confirmed with newer artifacts

## Risk investigated

Whether `conscience.Idem_1.0a.jar`'s previously confirmed negative score gap persists under the latest matched artifacts, and whether its mean magnitude changes materially.

## Evidence boundary

The read-only subject jar has SHA-256 `1635ea6692d7c89d41d0e089fced55ac334f24f429380fb686590ca58cecfb2d`. The official five-pair confirmation `3c2f3da4821aaed3` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1505150c660236e2d868aa090c7c6746c815f37c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 11,633.6 points and Tank Royale averaged 6,197.6 points, for a −46.5% mean delta. The five pair deltas were −45.9%, −46.1%, −54.8%, −42.8%, and −42.9%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-271's preceding five-pair confirmation on 2026-10-08 had a −41.98% mean delta, also with every pair negative and no errors or skipped-turn events. The current result reproduces the lower Tank Royale score under newer matched artifacts, with a somewhat larger mean gap.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Idem from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Idem's lower Tank Royale score remains confirmed at −46.5%, compared with −41.98% in AN-271. All five pairs favored Classic; no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/conscience.Suicidal_1.1.jar` (`DISCREPANCY (no score)`).
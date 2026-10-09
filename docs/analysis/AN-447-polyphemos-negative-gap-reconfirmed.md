---
id: AN-447
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-161, AN-290]
title: Polyphemos's lower Tank Royale score persists with a smaller mean gap
provenance: inferred
reversal-cost: low
---

# AN-447 — Polyphemos's lower Tank Royale score persists with a smaller mean gap

## Risk investigated

Whether `de.erdega.robocode.Polyphemos_0.4.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its mean magnitude changes.

## Evidence boundary

The read-only subject jar has SHA-256 `64765b6e60690f6889f1ef44ef0e8a566871eee38b280784e68900dff5d0e7be`. The official five-pair confirmation `263c4ec47cfdcfe4` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `de3a59833f71e25c07e3fd9fe614747caffa36a7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,473.0 points and Tank Royale averaged 3,347.0 points, for a −38.74% mean delta. The five pair deltas were −39.8%, −30.0%, −42.0%, −34.9%, and −47.0%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-290's preceding five-pair confirmation on 2026-10-08 had a −41.54% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a smaller mean gap.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Polyphemos from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Polyphemos's lower Tank Royale score remains confirmed at −38.74%, compared with −41.54% in AN-290. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Skip the intervening `PASS` and `MATCHED (score noise)` subjects. Continue in registry order with `roborumble/demetrix.nano.SledgeHammer_0.22.jar` (`CONFIRMED (score)`).

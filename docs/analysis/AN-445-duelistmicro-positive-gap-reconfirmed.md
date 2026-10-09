---
id: AN-445
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-158, AN-288]
title: DuelistMicro's positive score gap persists at a stable magnitude
provenance: inferred
reversal-cost: low
---

# AN-445 — DuelistMicro's positive score gap persists at a stable magnitude

## Risk investigated

Whether `davidalves.net.DuelistMicro_1.22.jar`'s previously confirmed positive Tank Royale score gap persists under the latest matched artifacts, and whether its magnitude materially changes.

## Evidence boundary

The read-only subject jar has SHA-256 `e51b32ab504ef17c0bcd47bb7953ccdd07f7e050b0fa1a3478e63281eaa0fe2a`. The official five-pair confirmation `b599bed5880f0ee4` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `de3a59833f71e25c07e3fd9fe614747caffa36a7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,260.6 points and Tank Royale averaged 8,375.4 points, for a +59.18% mean delta. The five pair deltas were +57.0%, +57.2%, +57.9%, +58.8%, and +65.0%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-288's preceding five-pair confirmation on 2026-10-08 had a +58.98% mean delta, also with all pairs positive and no errors or skipped-turn events. The current result reproduces the same positive score gap at a nearly unchanged magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or DuelistMicro from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

DuelistMicro's higher Tank Royale score remains confirmed at +59.18%, nearly unchanged from AN-288's +58.98%. All five pairs favored Tank Royale, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/davv.DOne_b002.jar` (`CONFIRMED (score)`).

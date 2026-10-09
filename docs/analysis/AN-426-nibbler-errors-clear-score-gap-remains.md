---
id: AN-426
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-132, AN-265]
title: Nibbler's prior Classic errors clear while its score gap remains confirmed
provenance: inferred
reversal-cost: low
---

# AN-426 — Nibbler's prior Classic errors clear while its score gap remains confirmed

## Risk investigated

Whether `cbot.agile.Nibbler_0.2.jar`'s Classic-only null-`Pray` errors recur under the latest matched artifacts, and whether its positive score gap remains outside the five-pair confirmation band.

## Evidence boundary

The read-only subject jar has SHA-256 `b1b79c3c45f03c2098bee75bd1c379e51b8e45f66418b0ac15a9e9747fef1689`. The official five-pair confirmation `7dd92f53687e5c8b` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1d37a31f57f1ce5576a1fdc5e24accb4f37c3a97`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,704.2 points and Tank Royale averaged 6,747.2 points, for a +18.44% mean delta. The five pair deltas were +21.0%, +24.7%, +6.3%, +21.6%, and +18.6%. The registry status is `CONFIRMED (score)`; excluding the largest pair, the remaining four average +16.88%, still beyond the confirmation band.

Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. AN-265's prior observation on 2026-10-08 had a +19.2% score delta, 10 Classic errors tied in part to the known null-`Pray` access, no Tank Royale errors, and one captured skipped-turn event. The current run does not reproduce the prior error imbalance or skipped-turn event, while the positive score gap remains similar.

## What was not pursued

The score difference was not attributed to the bridge, Tank Royale, or Nibbler from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Nibbler's prior Classic-only errors and skipped-turn event did not recur in the current five pairs. Its higher Tank Royale score remains confirmed at +18.44%, still outside the 15-point band even without the largest pair. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/cli.WasteOfAmmo_1.0.jar` (`DISCREPANCY (no score)`).
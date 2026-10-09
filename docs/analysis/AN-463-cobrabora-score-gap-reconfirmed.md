---
id: AN-463
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-181, AN-307]
title: CobraBora's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-463 — CobraBora's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `drm.CobraBora_1.12.jar`'s historical Tank Royale score advantage persists under the latest matched artifacts, and whether the previous no-score array-bounds failure recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `dcb1af76fd7509e2eeef13fb80a8b45a6184cd47c025315df67e000d37668b1e`. The official five-pair confirmation `404a1a3263c998a2` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `89f1aa92da35b79287488a5a9ce63be021d752d7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,518.0 points and Tank Royale averaged 9,948.2 points, for a +52.96% mean delta. The five pair deltas were +64.4%, +54.3%, +60.5%, +43.3%, and +42.3%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-307's preceding retry had no score samples and recorded an `ArrayIndexOutOfBoundsException` in the read-only robot's enemy-history lookup. That no-score outcome did not recur in this five-pair run. AN-181's older five-pair confirmation recorded a +39.98% Tank Royale mean advantage without errors; the current measurement reproduces the direction with a larger mean gap under newer artifacts.

## What was not pursued

The score advantage was not attributed to the bridge, Tank Royale, or CobraBora from aggregate scores alone. This retest did not isolate why the earlier run reached the robot's unchecked history index, and it does not establish whether that failure can recur under another event sequence. No controlled trace or code change was made, and the read-only subject jar was not modified.

## Finding

CobraBora's Tank Royale score advantage is confirmed at +52.96%, and the earlier no-score failure did not recur. All five pairs favored Tank Royale, with no errors or skipped-turn events. The retest did not identify the score-gap cause or explain the earlier array-bounds failure.

## M-006 handoff

Continue in registry order with `roborumble/dsekercioglu.shield.ColdBreath_1.0.jar` (`DISCREPANCY (no score)`).

---
id: AN-435
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-145, AN-275]
title: GhostShell's score gap and prior errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-435 — GhostShell's score gap and prior errors clear in five current pairs

## Risk investigated

Whether `cw.megas.GhostShell_GT.jar`'s previous score and runtime-error discrepancies recur under the latest matched artifacts, and whether the new retest identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `f75b19028ba3d181ae70d87a380370cf53e535b49e87f9060069a8591bc5888a`. The official five-pair confirmation `79dde5e15d5d3484` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1505150c660236e2d868aa090c7c6746c815f37c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 9,047.0 points and Tank Royale averaged 9,008.8 points, for a −0.1% mean delta. The five pair deltas were +5.7%, −5.6%, +6.5%, −4.8%, and −2.3%. The registry status is `MATCHED (score noise)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-275's preceding observation on 2026-10-08 had a −9.4% score delta, 596 Classic errors, and 1,036 Tank Royale errors. The current run does not reproduce that error imbalance or the prior no-score result from AN-145; its score difference is also inside the five-pair band. This shows the discrepancy did not recur in this setup, not that the historical robot-owned unchecked-state paths are permanently fixed.

## What was not pursued

No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified. The current pass does not assign a lasting repair to the bridge or the robot.

## Finding

GhostShell's score difference is within the noise band at −0.1%, and neither the historical runtime-error imbalance nor the earlier no-score outcome recurred in five current pairs. No skipped-turn events were captured. The result does not establish that the old errors are permanently eliminated.

## M-006 handoff

Skip the intervening `PASS` rows and continue in registry order with `roborumble/cx.micro.Blur_0.2.jar` (`CONFIRMED (score)`).
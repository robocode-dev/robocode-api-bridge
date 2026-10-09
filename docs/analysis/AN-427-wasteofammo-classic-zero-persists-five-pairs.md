---
id: AN-427
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-134, AN-266]
title: WasteOfAmmo's Classic zero persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-427 — WasteOfAmmo's Classic zero persists across five current pairs

## Risk investigated

Whether `cli.WasteOfAmmo_1.0.jar`'s recurring Classic zero-score outcome persists across five current pairs under the latest matched artifacts, and whether the run identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `c899395217eb5b9a8c9bdcd3e8869616ec23576e787e4ce3e2c7c3263695b36f`. The official five-attempt confirmation `45c4885e7622ace2` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1d37a31f57f1ce5576a1fdc5e24accb4f37c3a97`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

Five attempts ran; none produced a usable paired score delta because Classic scored zero in every attempt. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry status is `DISCREPANCY (outcome)`, since Classic returned no score while Tank Royale scored. The manifest's `threshold: 25.0` does not classify this zero-score outcome.

## What was tried

Classic scored 0 in all five attempts. Tank Royale scored 1,400, 1,560, 1,520, 1,560, and 1,520 points, averaging 1,512. Neither engine reported runtime errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry records zero completed confirmation samples and no mean delta because Classic's zero score prevents a paired comparison.

AN-266's preceding observation on 2026-10-08 also had Classic at 0 and Tank Royale at 1,460, without runtime errors or skipped-turn events. AN-134's earlier bytecode review observed that the robot's `run()` loop issues turns but does not call `execute()`; that behavior is a lead, not a proven explanation for the cross-engine score difference. The current retest does not resolve the cause.

## What was not pursued

The Classic zero was not attributed to the bridge or the robot from scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

WasteOfAmmo's Classic zero-score outcome persists across all five current attempts, while Tank Royale averages 1,512 points. Neither engine reported runtime errors and no skipped-turn events were captured. The cause of the asymmetric outcome remains open.

## M-006 handoff

Continue in registry order with `roborumble/com.arsenic.NewTest_1.0.jar` (`DISCREPANCY (errors)`).
---
id: AN-433
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-140, AN-272]
title: Suicidal's Classic zero-score outcome persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-433 — Suicidal's Classic zero-score outcome persists across five current pairs

## Risk investigated

Whether `conscience.Suicidal_1.1.jar`'s recurring Classic zero-score outcome persists across five pairs under the latest matched artifacts, and whether the retest clarifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `4650b8e8b5705e0fafb87fb4ffdcda77597f168c7c1c9272abae80691ad4f3d6`. The official five-attempt confirmation `05d3aa694b97dc42` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1505150c660236e2d868aa090c7c6746c815f37c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

Five attempts ran; none produced a usable paired score delta because Classic scored zero in every attempt. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry status is `DISCREPANCY (outcome)` because Classic returned no score while Tank Royale scored. The manifest's `threshold: 25.0` does not classify this zero-score outcome.

## What was tried

Classic scored 0 in all five attempts. Tank Royale scored 2,080, 2,060, 2,100, 2,100, and 2,060 points, averaging 2,080. Neither engine reported runtime errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry records zero completed score-confirmation samples and no mean delta because Classic's zero score prevents a paired comparison.

AN-272's preceding observation on 2026-10-08 also had Classic at 0 and Tank Royale at 2,080, with no errors or skipped-turn events. The five current pairs reproduce the same asymmetric outcome and a similar Tank Royale score. The cause remains open.

## What was not pursued

The Classic zero was not attributed to the bridge or the robot from scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Suicidal's Classic zero-score outcome persists across all five current attempts, while Tank Royale averages 2,080 points. Neither engine reported runtime errors and no skipped-turn events were captured. The retest does not explain the asymmetric outcome.

## M-006 handoff

Skip `roborumble/cre.Karolos_0.32.jar`, whose current registry status is `PASS`. Continue with `roborumble/cs.Nene_1.0.5.jar` (`CONFIRMED (score)`).
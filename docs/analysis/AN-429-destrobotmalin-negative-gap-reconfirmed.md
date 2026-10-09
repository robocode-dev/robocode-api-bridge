---
id: AN-429
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-136, AN-268]
title: DestrobotMalin's large lower score persists across five pairs
provenance: inferred
reversal-cost: low
---

# AN-429 — DestrobotMalin's large lower score persists across five pairs

## Risk investigated

Whether `com.blogspot.malinkody.DestrobotMalin_1.0.jar`'s confirmed lower Tank Royale score persists under the latest matched artifacts, and whether one pair determines the result.

## Evidence boundary

The read-only subject jar has SHA-256 `d3205656d01007c800f5fa5549d247aa7030a40380277752ecd590e091339c27`. The official five-pair confirmation `7164e2e014081c91` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1d37a31f57f1ce5576a1fdc5e24accb4f37c3a97`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,844.4 points and Tank Royale averaged 3,259.6 points, for a −58.44% mean delta. The five pair deltas were −52.6%, −60.4%, −65.8%, −59.7%, and −53.7%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-268's preceding five-pair confirmation on 2026-10-08 had a −57.6% mean delta, also with all five pairs negative and no errors or skipped-turn events. The current result reproduces the large negative score gap under newer matched artifacts.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or DestrobotMalin from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

DestrobotMalin's lower Tank Royale score remains confirmed at −58.44%, close to AN-268's −57.6%. All five pairs favored Classic, with no engine errors or skipped-turn events. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/com.sociesc.T1000_1.0.0.jar` (`DISCREPANCY (outcome)`).
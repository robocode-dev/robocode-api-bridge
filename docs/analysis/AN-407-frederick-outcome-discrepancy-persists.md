---
id: AN-407
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-109]
title: Frederick's low scores keep the cross-engine outcome discrepancy open
provenance: inferred
reversal-cost: low
---

# AN-407 — Frederick's low scores keep the cross-engine outcome discrepancy open

## Risk investigated

Whether `ap.Frederick_1.1.jar`'s repeated Classic zero scores and higher Tank Royale scores persist under the latest matched artifacts, and whether the current retest permits a meaningful score confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `d7f1709e4ea064f0662f383c339f57390977292fdd921933adc36e305a375899`. The official five-attempt retest `c21a140fa78952d5` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `960b55b85e93eb90fcfebddf55eca4c38b618977`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

Five attempts ran, but only two had nonzero Classic scores and therefore produced percentage deltas. This is a single subject and setup, not a population-wide estimate; the available percentage observations cannot be treated as a complete five-pair score confirmation. No confidence interval or significance test was calculated. The manifest's `threshold: 25.0` is the regular sweep setting; it is not the five-pair `REGRESSION_BAND_POINTS = 15.0` confirmation gate.

## What was tried

Classic's five-attempt mean was 5.2 points and Tank Royale's was 123.0. The per-attempt scores were Classic 24, 0, 2, 0, and 0 versus Tank Royale 121, 181, 1, 252, and 60. The two calculable percentage deltas were +404.2% and −50.0%; the registry's `delta_mean` of +177.1% averages only those two values. Three zero Classic scores leave the row classified `DISCREPANCY (outcome)`. Neither engine reported runtime errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-109 records an earlier 2026-10-06 five-attempt confirmation with two zero Classic scores and three calculable deltas. The current retest again has repeated Classic zeros, now with only two nonzero Classic scores. AN-109's bundled-source observation of `Math.random()` movement and the absence of firing supports outcome variability and low scores, but neither that observation nor this retest explains the cross-engine difference.

## What was not pursued

The percentage mean was not used as a complete five-pair score confirmation because three Classic values were zero. No cause was assigned to the bridge, Tank Royale, or robot from these small stochastic scores, and no code or read-only robot jar was changed.

## Finding

Frederick's outcome discrepancy remains open: three of five current attempts scored zero in Classic while Tank Royale scored positively. Only two percentage deltas are calculable, so the +177.1% delta mean is not a five-sample confirmation. Runtime errors and skipped-turn events did not explain the outcome pattern.

## M-006 handoff

Continue in registry order with `roborumble/apollokidd.ApolloKidd_0.9.jar` (`DISCREPANCY (score)`).

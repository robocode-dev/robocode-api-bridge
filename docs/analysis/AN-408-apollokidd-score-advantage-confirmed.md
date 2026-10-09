---
id: AN-408
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-329]
title: ApolloKidd's higher Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-408 — ApolloKidd's higher Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `apollokidd.ApolloKidd_0.9.jar`'s historical higher Tank Royale score in the `roborumble` registry row persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `e7153516f9b25d4d571e484022c7c9fc6ab19f036faeb46213764691c84e7390`. The official five-pair confirmation `8111258761f78e68` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `960b55b85e93eb90fcfebddf55eca4c38b618977`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 10,389.6 points and Tank Royale averaged 13,932.6 points, for a +34.16% mean delta. The five pair deltas were +27.3%, +35.3%, +36.9%, +44.5%, and +26.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `bcbade73beb02c58` on 2026-09-28 had a +32.4% delta without recorded errors; earlier score observations were +34.5% and +30.9%. The current five-pair result confirms a similar higher Tank Royale score under the newer matched artifacts. AN-329 concerns this same jar in the separate melee setup and does not explain the RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or ApolloKidd. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

ApolloKidd's higher Tank Royale score remains confirmed at +34.16%, compared with +32.4% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/ar.horizon.Horizon_1.2.2.jar` (`DISCREPANCY (score)`).

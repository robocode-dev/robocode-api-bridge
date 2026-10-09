---
id: AN-405
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-325]
title: SuperStrike's lower Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-405 — SuperStrike's lower Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `amk.superstrike.SuperStrike_0.3.jar`'s historical lower Tank Royale score in the `roborumble` registry row persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `ef146a488f92e25b5f8f957831fd715bb6a6b2b1f8cf72ffa05bad505c34c79b`. The official five-pair confirmation `0c86e6f067e03527` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `960b55b85e93eb90fcfebddf55eca4c38b618977`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,172.2 points and Tank Royale averaged 3,455.8 points, for a −51.7% mean delta. The five pair deltas were −57.4%, −51.5%, −54.8%, −52.0%, and −42.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `db8a1c3e449c274e` on 2026-09-28 had a −55.6% delta without recorded errors; earlier score observations were −57.0% and −52.1%. The current five-pair result confirms a similarly large lower Tank Royale score under the newer matched artifacts. AN-325 concerns this same jar in the separate melee setup and does not explain the RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or SuperStrike. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

SuperStrike's lower Tank Royale score remains confirmed at −51.7%, compared with −55.6% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/ao.T100_0.9.jar` (`DISCREPANCY (score)`).

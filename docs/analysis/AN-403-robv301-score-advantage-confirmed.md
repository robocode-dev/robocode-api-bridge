---
id: AN-403
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ROBv301's higher Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-403 — ROBv301's higher Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `amc.ROBv301_1.1.jar`'s historical higher Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `0f5ce30be90a631bbca672094046847e552d2b2370af3ec73b3d9ed8c737798f`. The official five-pair confirmation `db2e474505c89859` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `bb473347f99f9e44752f8f9106981cbf2077baf9`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 3,703.2 points and Tank Royale averaged 7,325.4 points, for a +98.7% mean delta. The five pair deltas were +99.7%, +78.9%, +78.7%, +117.0%, and +119.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `ebbef7fc4153c8ab` on 2026-09-28 had a +114.3% delta without recorded errors; older score observations were +70.0% and +99.0%. The current five-pair result confirms a similarly large higher Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or ROBv301. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

ROBv301's higher Tank Royale score remains confirmed at +98.7%, compared with +114.3% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/amc.ROBv400_1.0.jar` (`DISCREPANCY (score)`).

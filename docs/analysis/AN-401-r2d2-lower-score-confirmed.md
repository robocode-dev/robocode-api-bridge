---
id: AN-401
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: R2d2's lower Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-401 — R2d2's lower Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `ahf.r2d2.R2d2_0.86.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `cdd8e80b980375b1ed59df8fe5a361207c9d3ee32c6e0d81f936e000c2b1b9f2`. The official five-pair confirmation `7b3f5ff082286194` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `bb473347f99f9e44752f8f9106981cbf2077baf9`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,019.8 points and Tank Royale averaged 3,466.4 points, for a −42.34% mean delta. The five pair deltas were −41.7%, −47.9%, −40.8%, −41.9%, and −39.4%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `a2becfd0a85b24db` on 2026-09-28 had a −37.4% delta without recorded errors; three earlier score observations were −45.3%, −42.0%, and −50.7%. The current five-pair result confirms a similarly large lower Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or R2d2. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

R2d2's lower Tank Royale score remains confirmed at −42.34%, compared with −37.4% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/amc.ROBv300_1.1.jar` (`DISCREPANCY (score)`).

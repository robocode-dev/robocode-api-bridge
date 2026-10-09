---
id: AN-400
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-317]
title: NanoAndrew's lower Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-400 — NanoAndrew's lower Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `ahf.NanoAndrew_.4.jar`'s historical lower Tank Royale score in the `roborumble` registry row persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `667ad546b341badd93276d9de63623cf8ddd42918f46a2a04b23dbe114bacf2d`. The official five-pair confirmation `3b567af0a7d8619b` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `bb473347f99f9e44752f8f9106981cbf2077baf9`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,275.2 points and Tank Royale averaged 2,484.6 points, for a −60.34% mean delta. The five pair deltas were −62.4%, −60.0%, −56.8%, −63.1%, and −59.4%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `f6526bb51480d4f5` on 2026-09-28 had a −59.9% delta without recorded errors; earlier score observations were −58.4%, −56.0%, and −62.5%. The current five-pair mean confirms the same large lower Tank Royale score under newer matched artifacts. AN-317 concerns the same jar in the separate melee setup and does not explain this RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or NanoAndrew. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

NanoAndrew's lower Tank Royale score remains confirmed at −60.34%, compared with −59.9% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/ahf.r2d2.R2d2_0.86.jar` (`DISCREPANCY (score)`).

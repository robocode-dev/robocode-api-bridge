---
id: AN-413
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: xbots' higher Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-413 — xbots' higher Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `as.xbots_1.0.jar`'s historical higher Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `6adf1183af7ed3e4cccbaf35249fede5ffb8dc2101e49458106909cea95120cf`. The official five-pair confirmation `b89feb1de05779a5` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `26f82bcb83360debf53b9fc0310daef363359600`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 10,660.6 points and Tank Royale averaged 16,419.2 points, for a +54.04% mean delta. The five pair deltas were +53.8%, +56.3%, +54.0%, +49.2%, and +56.9%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `533a30f3b2272199` on 2026-09-11 had a +53.8% delta without recorded errors; the earlier observation on 2026-09-08 was +54.8%. The current five-pair result confirms a similarly large higher Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or xbots. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

xbots' higher Tank Royale score remains confirmed at +54.04%, compared with +53.8% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/axeBots.Okami_1.04.jar` (`DISCREPANCY (errors)`).

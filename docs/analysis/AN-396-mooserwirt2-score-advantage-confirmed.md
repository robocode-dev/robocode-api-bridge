---
id: AN-396
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-313]
title: Mooserwirt2's large Tank Royale score advantage persists in confirmation
provenance: inferred
reversal-cost: low
---

# AN-396 — Mooserwirt2's large Tank Royale score advantage persists in confirmation

## Risk investigated

Whether `agd.Mooserwirt2_2.7.jar`'s higher Tank Royale score in the `roborumble` registry row persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `377e6a4c674d0436ee7d3cf8e29c2581fc37e55238e6fefef86394c55a35b556`. The official five-pair confirmation `a6e1afa669bb7157` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ff03c80914aa57ad48b08ed5ca6df4949db1ef6a`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,098.4 points and Tank Royale averaged 12,408.0 points, for a +104.74% mean delta. The five pair deltas were +99.4%, +131.7%, +115.0%, +84.1%, and +93.5%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry captured zero events in attempts 1 and 5; telemetry was unavailable in attempts 2 through 4, so those runs are not treated as zero-event observations. The registry status is `CONFIRMED (score)`.

The preceding score observation `103589457c330e19` on 2026-09-28 had a +126.1% delta and no recorded errors; two older score observations were +93.5% and +96.6%. A separate 2026-09-11 observation was `DISCREPANCY (outcome)` with two Tank Royale errors, but the current report records a completed score result without errors. AN-313 concerns this same jar in the different melee setup and does not explain the RoboRumble score gap.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or Mooserwirt2. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Mooserwirt2's higher Tank Royale score remains confirmed at +104.74%, compared with +126.1% in the preceding score observation. The current report has no runtime errors; skipped-turn telemetry is incomplete, and the score difference remains unexplained.

## M-006 handoff

Continue in registry order with `roborumble/agrach.Dalek_1.0.jar` (`DISCREPANCY (score)`).

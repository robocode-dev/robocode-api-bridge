---
id: AN-395
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Syzygy's lower Tank Royale score persists in five-pair confirmation
provenance: inferred
reversal-cost: low
---

# AN-395 — Syzygy's lower Tank Royale score persists in five-pair confirmation

## Risk investigated

Whether `acid.Syzygy_1.0.4.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `a7336d30e377b013c9beaa6b4afabf93d3b8a83224d344beb7428e0361c4aa97`. The official five-pair confirmation `8e92162159ca7418` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ff03c80914aa57ad48b08ed5ca6df4949db1ef6a`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,548.2 points and Tank Royale averaged 3,607.2 points, for a −44.62% mean delta. The five pair deltas were −48.1%, −49.4%, −38.4%, −53.2%, and −34.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `6aa7029a15000fcf` on 2026-09-28 had a −48.9% delta and no recorded errors. The current five-pair mean confirms a similarly large lower Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

The repeated score difference does not locate divergent behavior, so no cause was assigned to the bridge, Tank Royale, or Syzygy. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Syzygy's lower Tank Royale score remains confirmed at −44.62%, compared with −48.9% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/agd.Mooserwirt2_2.7.jar` (`DISCREPANCY (score)`).

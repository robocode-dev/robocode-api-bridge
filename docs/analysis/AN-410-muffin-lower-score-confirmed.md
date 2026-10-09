---
id: AN-410
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Muffin's lower Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-410 — Muffin's lower Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `arthord.micro.Muffin_0.6.1.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `a5b7c2fe230839a0452e06b8036ef3ed5ebbe771c75a67a00e6d9f6ffe547c6c`. The official five-pair confirmation `ccf0c7887fd81260` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `26f82bcb83360debf53b9fc0310daef363359600`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,851.8 points and Tank Royale averaged 1,651.8 points, for a −71.72% mean delta. The five pair deltas were −69.9%, −71.0%, −72.2%, −75.0%, and −70.5%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `1c0e50209a64265e` on 2026-09-28 had a −68.8% delta without recorded errors; earlier score observations were −63.2% and −72.0%. The current five-pair result confirms a similarly large lower Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or Muffin. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Muffin's lower Tank Royale score remains confirmed at −71.72%, compared with −68.8% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/ary.FourWD_1.3d.jar` (`CONFIRMED (score)`).

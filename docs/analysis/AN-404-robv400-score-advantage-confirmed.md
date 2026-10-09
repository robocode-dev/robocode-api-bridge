---
id: AN-404
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ROBv400's higher Tank Royale score remains confirmed despite pair variation
provenance: inferred
reversal-cost: low
---

# AN-404 — ROBv400's higher Tank Royale score remains confirmed despite pair variation

## Risk investigated

Whether `amc.ROBv400_1.0.jar`'s historical higher Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `ed8d81415f6cc852706df58f162e484cb4dc68882357c4e2f5875f5908eafa86`. The official five-pair confirmation `ac18eb3a339e9db2` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `bb473347f99f9e44752f8f9106981cbf2077baf9`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 3,769.6 points and Tank Royale averaged 7,407.0 points, for a +98.04% mean delta. The five pair deltas were +93.6%, +76.6%, +150.6%, +120.3%, and +49.1%. The score advantage is present in every pair but varies substantially; the registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

The preceding observation `b4df957b9784537d` on 2026-09-28 had a +106.1% delta without recorded errors; earlier observations were +78.2% and +139.7%. The current five-pair mean confirms a similarly large higher Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

The variable pair deltas and aggregate score do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or ROBv400. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

ROBv400's higher Tank Royale score remains confirmed at +98.04%, compared with +106.1% in the preceding observation. The current five-pair run reported no errors or skipped-turn events; the wide pair variation remains unexplained.

## M-006 handoff

Continue in registry order with `roborumble/amk.superstrike.SuperStrike_0.3.jar` (`DISCREPANCY (score)`).

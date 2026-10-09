---
id: AN-402
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ROBv300's higher Tank Royale score remains confirmed despite pair variation
provenance: inferred
reversal-cost: low
---

# AN-402 — ROBv300's higher Tank Royale score remains confirmed despite pair variation

## Risk investigated

Whether `amc.ROBv300_1.1.jar`'s historical higher Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `6db4ed03047d5efd9e8de254be8f68d9e3417a875c591e974514ff055d390920`. The official five-pair confirmation `577ca9519440c1a0` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `bb473347f99f9e44752f8f9106981cbf2077baf9`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 2,628.6 points and Tank Royale averaged 4,112.8 points, for a +61.24% mean delta. The five pair deltas were +14.3%, +92.4%, +60.9%, +64.6%, and +74.0%. Four pairs were above +60%, while one was within the 15-point band; the mean remains well outside it and the registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

The preceding observation `d1b48fa6a62b3b67` on 2026-09-28 had a +74.7% delta without recorded errors; two older score observations were +67.9% and +30.5%. The current five-pair mean confirms a large higher Tank Royale score under the newer matched artifacts. No existing analysis note names a cause for this RoboRumble score difference.

## What was not pursued

The variable pair deltas and aggregate score do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or ROBv300. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

ROBv300's higher Tank Royale score remains confirmed at +61.24%, compared with +74.7% in the preceding observation. The current five-pair run reported no errors or skipped-turn events; the wide pair variation remains unexplained.

## M-006 handoff

Continue in registry order with `roborumble/amc.ROBv301_1.1.jar` (`DISCREPANCY (score)`).

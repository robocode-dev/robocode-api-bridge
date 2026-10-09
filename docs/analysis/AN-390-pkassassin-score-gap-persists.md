---
id: AN-390
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: PkAssassin's lower Tank Royale score persists under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-390 — PkAssassin's lower Tank Royale score persists under newer artifacts

## Risk investigated

Whether `PkKillers.PkAssassin_1.0.jar`'s previously observed lower Tank Royale score persists under the latest matched artifacts and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/PkKillers.PkAssassin_1.0.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `87b39ba6c8adbe0740613fe8004b29be2f2a6fae5a195ec42b811a5ec58e0678`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `4737f78f5e2f8ee6` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `916db0d2d23193d1651554d1980254cef9f55e25`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,617.4 points and Tank Royale averaged 3,669.4 points, for a −34.56% mean delta. The five pair deltas were −38.7%, −40.7%, −28.2%, −33.3%, and −31.9%. Neither engine reported errors, and no skipped-turn events were captured. The registry status is `CONFIRMED (score)`.

The preceding observation `e6c14288e7ce7aad` on 2026-09-21 had a −39.9% delta without errors under older bridge and Tank Royale commits. The lower Tank Royale score persists under the newer matched artifacts and remains well beyond the five-pair confirmation band. No existing analysis note names a cause for this gap.

## What was not pursued

The score difference was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

PkAssassin's lower Tank Royale score remains confirmed under the newer matched artifacts, at −34.56% compared with −39.9% in the earlier observation. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/RobotMarco.MarcoV_0.1.jar` (`DISCREPANCY (score)`).

---
id: AN-381
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: EBBU.Sim2's score gap contracts but remains confirmed
provenance: inferred
reversal-cost: low
---

# AN-381 — EBBU.Sim2's score gap contracts but remains confirmed

## Risk investigated

Whether `EBBU.Sim2_1.02.jar`'s earlier large Tank Royale score deficit persists under the latest matched artifacts and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/EBBU.Sim2_1.02.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `116ea3dea899b2cc289efafa466316df071151ba4c17361a25cde6b702e66c3d`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `0919dd220714d432` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `8812b71bd7edb44a5a30d39281c201bc4d051282`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score remains a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 7,104.8 points and Tank Royale averaged 5,995.8 points, for a −15.52% mean delta. The five pair deltas were −10.5%, −17.0%, −12.3%, −16.5%, and −21.3%. Neither engine reported errors, and no skipped-turn events were captured. Because the absolute mean is just above the 15.0-point confirmation band, the registry status is `CONFIRMED (score)`.

The preceding observation `82308feb917d2c0a` on 2026-09-12 had a −79.9% delta without errors under older bridge and Tank Royale commits. The current deficit is smaller, but remains beyond the five-pair confirmation band. No existing analysis note names a cause for EBBU.Sim2's score gap.

## What was not pursued

The score delta was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

EBBU.Sim2's score deficit contracted from −79.9% to −15.52% under the newer matched artifacts, but remains just outside the 15.0-point confirmation band. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/EE.LittleBig_1.0.jar` (`DISCREPANCY (score)`).

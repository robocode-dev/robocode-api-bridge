---
id: AN-383
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: EH.Fusion's Tank Royale score advantage persists under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-383 — EH.Fusion's Tank Royale score advantage persists under newer artifacts

## Risk investigated

Whether `EH.Fusion_0.32.jar`'s previously observed Tank Royale score advantage persists under the latest matched artifacts and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/EH.Fusion_0.32.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `988d1ac2f81d32bc6e37059181f522e396419f76b2ca76e929f69a9a219d56eb`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `b7e342ce95ccdf91` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `8812b71bd7edb44a5a30d39281c201bc4d051282`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 9,184.8 points and Tank Royale averaged 12,840.2 points, for a +39.84% mean delta. The five pair deltas were +44.6%, +37.8%, +38.1%, +35.6%, and +43.1%. Neither engine reported errors, and no skipped-turn events were captured. The registry status is `CONFIRMED (score)`.

The preceding observation `96dd484b2b911c5d` on 2026-09-12 had a +38.0% delta without errors under older bridge and Tank Royale commits. The positive gap is similar under the newer matched artifacts and remains well beyond the five-pair confirmation band. No existing analysis note names a cause for EH.Fusion's score advantage.

## What was not pursued

The score advantage was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

EH.Fusion's Tank Royale score advantage remains confirmed under the newer matched artifacts, at +39.84% compared with +38.0% in the earlier observation. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/Krabb.sliNk.Garm_0.9u.jar` (`DISCREPANCY (outcome)`).

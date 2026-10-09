---
id: AN-382
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: EE.LittleBig's large Tank Royale score advantage persists
provenance: inferred
reversal-cost: low
---

# AN-382 — EE.LittleBig's large Tank Royale score advantage persists

## Risk investigated

Whether `EE.LittleBig_1.0.jar`'s previously observed Tank Royale score advantage persists under the latest matched artifacts, and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/EE.LittleBig_1.0.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `89ea5fe0b50a1f906ef5440293004c29ec88ae771811d8a3c031cfe9afa32a3b`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `d9238a50e829bd57` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `8812b71bd7edb44a5a30d39281c201bc4d051282`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score remains a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,034.2 points and Tank Royale averaged 9,291.4 points, for a +85.12% mean delta. The five pair deltas were +108.5%, +92.8%, +85.3%, +94.1%, and +44.9%. Neither engine reported errors, and no skipped-turn events were captured. The registry status is `CONFIRMED (score)`.

The preceding observation `dd65d66b59ff8acb` on 2026-09-12 had a +148.6% delta without errors under older bridge and Tank Royale commits. The current Tank Royale advantage is smaller but remains far beyond the five-pair confirmation band. No existing analysis note names a cause for this gap.

## What was not pursued

The score advantage was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

EE.LittleBig's Tank Royale score advantage remains strongly confirmed under the newer matched artifacts, at +85.12% versus +148.6% in the earlier observation. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/EH.Fusion_0.32.jar` (`DISCREPANCY (score)`).

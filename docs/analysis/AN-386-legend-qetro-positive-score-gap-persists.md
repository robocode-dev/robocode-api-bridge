---
id: AN-386
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Legend.Qetro's large Tank Royale score advantage persists
provenance: inferred
reversal-cost: low
---

# AN-386 — Legend.Qetro's large Tank Royale score advantage persists

## Risk investigated

Whether `Legend.Qetro_1.6.jar`'s previously observed Tank Royale score advantage persists under the latest matched artifacts and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/Legend.Qetro_1.6.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `eb31a188abf9582606fb7045bf4d796bb8a04abbec31f0171916e6e7e8f32422`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `4702209b416d22e1` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `4f3f1774553d68045dc085d5dd0917fad5b239f4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 1,424.2 points and Tank Royale averaged 4,819.8 points, for a +254.7% mean delta. The five pair deltas were +210.9%, +177.5%, +228.0%, +412.1%, and +245.0%. Neither engine reported errors, and no skipped-turn events were captured. The registry status is `CONFIRMED (score)`.

The preceding observation `198ccdef7a8da62a` on 2026-09-12 had a +435.4% delta without errors under older bridge and Tank Royale commits. The Tank Royale advantage is smaller under the newer matched artifacts but remains far beyond the five-pair confirmation band. The current pair deltas vary substantially, including one +412.1% result; no confidence interval was calculated. No existing analysis note names a cause for this gap.

## What was not pursued

The score advantage was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

Legend.Qetro's Tank Royale score advantage remains confirmed under the newer matched artifacts, at +254.7% compared with +435.4% in the earlier observation. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/McS.Spanky_test_0.1a.jar` (`DISCREPANCY (score)`).

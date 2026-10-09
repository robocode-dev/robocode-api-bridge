---
id: AN-387
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: McS.Spanky's lower Tank Royale score remains just beyond the confirmation band
provenance: inferred
reversal-cost: low
---

# AN-387 — McS.Spanky's lower Tank Royale score remains just beyond the confirmation band

## Risk investigated

Whether `McS.Spanky_test_0.1a.jar`'s previously observed lower Tank Royale score persists under the latest matched artifacts and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/McS.Spanky_test_0.1a.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `de3c5ea13a4fd84f5148ef75263cf441f6f41f0fc0cd57d11437dd79e29fda15`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `e055789a3565f7f9` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `4f3f1774553d68045dc085d5dd0917fad5b239f4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,780.6 points and Tank Royale averaged 4,668.0 points, for a −18.66% mean delta. The five pair deltas were −28.8%, −14.2%, −14.1%, −29.3%, and −6.9%. Neither engine reported errors, and no skipped-turn events were captured. The mean is just beyond the 15.0-point confirmation band, so the registry status is `CONFIRMED (score)`.

The preceding observation `97acd28e075752eb` on 2026-09-12 had a −34.6% delta without errors under older bridge and Tank Royale commits. The current gap is smaller but remains outside the five-pair confirmation band. The pair deltas vary substantially, and no confidence interval was calculated. No existing analysis note names a cause for this gap.

## What was not pursued

The score difference was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

McS.Spanky's lower Tank Royale score remains confirmed under the newer matched artifacts, at −18.66% compared with −34.6% in the earlier observation. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/Noran.BitchingElk_0.054.jar` (`DISCREPANCY (score)`).

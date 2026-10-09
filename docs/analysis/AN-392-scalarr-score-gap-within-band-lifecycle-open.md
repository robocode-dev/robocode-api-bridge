---
id: AN-392
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-012]
title: ScalarR's current score gap falls within the confirmation band
provenance: inferred
reversal-cost: low
---

# AN-392 — ScalarR's current score gap falls within the confirmation band

## Risk investigated

Whether `aaa.r.ScalarR_0.005h.053.jar`'s previously observed Tank Royale score advantage persists under the latest matched artifacts and whether the earlier lifecycle failure recurs.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/aaa.r.ScalarR_0.005h.053.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `60fef68d968790732c85b20d885e1ca9e99c5ba245e43c1a167f9f3b8956c055`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `57078fe1295c4db3` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `916db0d2d23193d1651554d1980254cef9f55e25`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,162.6 points and Tank Royale averaged 4,763.8 points, for a +14.5% mean delta. The five pair deltas were +15.0%, +15.8%, +19.3%, +12.4%, and +10.0%. Neither engine reported errors, and the registry status is `MATCHED (score noise)` because the mean is within the 15.0-point confirmation band.

Skipped-turn telemetry was captured in every pair, with eight events per run. The registry records bot IDs, but this finding does not attribute those events to ScalarR. `AN-012` records an earlier Tank Royale lifecycle/tick-boundary exception in ScalarR's custom-event path and leaves ownership between the bridge callback boundary and Bot API scheduling unresolved. That exception did not appear in the current confirmation; the repeated telemetry events mean this run alone does not close the earlier lifecycle question.

The preceding score observation `1999b8e05dea9d75` on 2026-09-28 had a +205.7% delta without recorded engine errors. An older observation `7c97630f120460e2` on 2026-09-12 had a +91.4% delta. The current score difference is much smaller than either historical value.

## What was not pursued

The current within-band score result was not treated as proof that the prior lifecycle exception has been repaired. The eight skipped-turn events were not attributed to ScalarR or treated as an explanation for the score delta. No bridge or rumble-jar change was made from this measurement.

## Finding

ScalarR's earlier large Tank Royale score advantage was not reproduced beyond the five-pair confirmation band under the newer matched artifacts: the current mean is +14.5%. No runtime error occurred, but eight skipped-turn events were captured in each pair. The older lifecycle exception did not recur in this run, while its ownership remains unresolved.

## M-006 handoff

Continue in registry order with `roborumble/abud.ThirdRobo_1.0.jar` (`DISCREPANCY (score)`).

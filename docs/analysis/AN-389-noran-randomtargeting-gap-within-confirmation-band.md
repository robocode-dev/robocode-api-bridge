---
id: AN-389
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Noran.RandomTargeting's old score gap falls within the current confirmation band
provenance: inferred
reversal-cost: low
---

# AN-389 — Noran.RandomTargeting's old score gap falls within the current confirmation band

## Risk investigated

Whether `Noran.RandomTargeting_0.02.jar`'s previously observed lower Tank Royale score persists under the latest matched artifacts and whether the current result identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/Noran.RandomTargeting_0.02.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `d45abf78b719a316f0d97abbdd01022c2d7a0b8d4e918a025606f2d974b4983b`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `a156e5486b03b328` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `4f3f1774553d68045dc085d5dd0917fad5b239f4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,300.4 points and Tank Royale averaged 6,060.2 points, for a −3.82% mean delta. The five pair deltas were −2.7%, −4.5%, −4.5%, −2.4%, and −5.0%. Neither engine reported errors, and no skipped-turn events were captured. The mean is within the 15.0-point confirmation band, so the registry status is `MATCHED (score noise)`.

The preceding observation `e012f27fbf629333` on 2026-09-12 had a −74.7% delta without errors under older bridge and Tank Royale commits. The current score difference is much smaller. No existing analysis note names a cause for the historical gap, and this run does not establish why it fell.

## What was not pursued

The older score gap was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made from the current within-band result.

## Finding

Noran.RandomTargeting's historical lower Tank Royale score was not reproduced beyond the five-pair confirmation band under the newer matched artifacts. The current mean delta is −3.82%, with no runtime errors or skipped-turn events. This result does not explain the earlier −74.7% observation or establish a root cause.

## M-006 handoff

Continue in registry order with `roborumble/PkKillers.PkAssassin_1.0.jar` (`DISCREPANCY (score)`).

---
id: AN-394
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Bl4ck's lower Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-394 — Bl4ck's lower Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `acid.Bl4ck_1.0.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `50cca7b205a1a20119a7b0d3810f54e6ce0a0c985612da73c161fcdc5bbbd5fd`. The official five-pair confirmation `999a01a924ee9307` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `916db0d2d23193d1651554d1980254cef9f55e25`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,591.6 points and Tank Royale averaged 3,761.8 points, for a −42.76% mean delta. The five pair deltas were −43.8%, −48.5%, −32.3%, −41.2%, and −48.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `c227ddb44177ae63` on 2026-09-28 had a −55.6% delta and no recorded errors. The score gap remains large under the newer matched artifacts, though the current five-pair mean is less negative. No existing analysis note names a cause for this specific RoboRumble score difference.

## What was not pursued

The repeated score difference does not locate divergent behavior, so no cause was assigned to the bridge, Tank Royale, or Bl4ck. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Bl4ck's lower Tank Royale score remains confirmed at −42.76%, compared with −55.6% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/acid.Syzygy_1.0.4.jar` (`DISCREPANCY (score)`).

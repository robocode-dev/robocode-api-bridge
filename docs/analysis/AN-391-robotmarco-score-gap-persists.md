---
id: AN-391
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: RobotMarco's large lower Tank Royale score persists under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-391 — RobotMarco's large lower Tank Royale score persists under newer artifacts

## Risk investigated

Whether `RobotMarco.MarcoV_0.1.jar`'s previously observed lower Tank Royale score persists under the latest matched artifacts and whether the current run identifies its cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/RobotMarco.MarcoV_0.1.jar`, previously recorded as `DISCREPANCY (score)`. The read-only subject jar has SHA-256 `6d2976a0cd1f611e6e3a74447c89dd1189278e2caee935db1e4cfc0fc919d7c1`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `67aa520ee2f223ee` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `916db0d2d23193d1651554d1980254cef9f55e25`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,171.0 points and Tank Royale averaged 2,113.0 points, for a −58.9% mean delta. The five pair deltas were −57.5%, −53.0%, −60.8%, −61.1%, and −62.1%. Neither engine reported errors, and no skipped-turn events were captured. The registry status is `CONFIRMED (score)`.

The preceding observation `4ce94ab4ed71983a` on 2026-09-21 had a −60.8% delta without errors under older bridge and Tank Royale commits. The lower Tank Royale score persists at a similar magnitude with the newer matched artifacts. No existing analysis note names a cause for this gap.

## What was not pursued

The score difference was not attributed to the bridge, Tank Royale, or robot behavior from aggregate scores alone. No code or rumble-jar change was made without a behavioral trace that locates the divergence.

## Finding

RobotMarco's lower Tank Royale score remains confirmed under the newer matched artifacts, at −58.9% compared with −60.8% in the earlier observation. The run produced no runtime errors or skipped-turn events and does not identify the cause. The score gap remains open for diagnosis.

## M-006 handoff

Continue in registry order with `roborumble/aaa.r.ScalarR_0.005h.053.jar` (`DISCREPANCY (score)`).

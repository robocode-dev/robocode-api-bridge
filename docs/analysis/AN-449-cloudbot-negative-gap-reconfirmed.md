---
id: AN-449
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-164, AN-292]
title: CloudBot's negative Tank Royale score gap persists
provenance: inferred
reversal-cost: low
---

# AN-449 — CloudBot's negative Tank Royale score gap persists

## Risk investigated

Whether `deo.CloudBot_1.3.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-292.

## Evidence boundary

The read-only subject jar has SHA-256 `d62f0561529c81e5da65ad44c4c6db8b31dceca043ec2d9fcc4646aa15ed28f3`. The official five-pair confirmation `5e2bf263f51f0eba` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `de3a59833f71e25c07e3fd9fe614747caffa36a7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,848.2 points and Tank Royale averaged 3,651.2 points, for a −37.18% mean delta. The five pair deltas were −53.8%, −30.3%, −28.7%, −37.4%, and −35.7%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-292's preceding five-pair confirmation had a −36.54% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a slightly larger mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or CloudBot from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

CloudBot's Classic score advantage remains confirmed at −37.18%, compared with −36.54% in AN-292. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/deo.virtual.RainbowBot_1.0.jar` (`CONFIRMED (score)`).

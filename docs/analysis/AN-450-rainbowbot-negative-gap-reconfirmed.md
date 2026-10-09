---
id: AN-450
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-165, AN-293]
title: RainbowBot's Classic score advantage remains confirmed
provenance: inferred
reversal-cost: low
---

# AN-450 — RainbowBot's Classic score advantage remains confirmed

## Risk investigated

Whether `deo.virtual.RainbowBot_1.0.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-293.

## Evidence boundary

The read-only subject jar has SHA-256 `3854b1a74b34f26b4fccfc997b5faadb4db60177cd48857d8bdba79e63f5bba4`. The official five-pair confirmation `42fdcb3b699a3f0d` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `80b8f661c9f6785bfe8f26a9dbe9987dc72d2e82`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,444.2 points and Tank Royale averaged 3,966.8 points, for a −38.38% mean delta. The five pair deltas were −41.3%, −34.8%, −45.0%, −37.0%, and −33.8%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-293's preceding five-pair confirmation had a −50.68% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a smaller mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or RainbowBot from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

RainbowBot's Classic score advantage remains confirmed at −38.38%, compared with −50.68% in AN-293. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/dft.Calliope_5.6.jar` (`CONFIRMED (score)`).

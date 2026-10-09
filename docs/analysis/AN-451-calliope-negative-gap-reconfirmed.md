---
id: AN-451
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-166, AN-295]
title: Calliope's Classic score advantage persists at a slightly smaller gap
provenance: inferred
reversal-cost: low
---

# AN-451 — Calliope's Classic score advantage persists at a slightly smaller gap

## Risk investigated

Whether `dft.Calliope_5.6.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-295.

## Evidence boundary

The read-only subject jar has SHA-256 `dee2ec44341ba6c2574bf995c317b4f806d3189f19ab040f8565b487dab5248a`. The official five-pair confirmation `64da92b71e68ee68` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `80b8f661c9f6785bfe8f26a9dbe9987dc72d2e82`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 8,743.2 points and Tank Royale averaged 6,967.6 points, for a −20.28% mean delta. The five pair deltas were −13.7%, −20.1%, −29.7%, −14.4%, and −23.5%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-295's preceding five-pair confirmation had a −21.16% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a slightly smaller mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Calliope from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Calliope's Classic score advantage remains confirmed at −20.28%, compared with −21.16% in AN-295. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/dft.Freddie_1.32.jar` (`CONFIRMED (score)`).

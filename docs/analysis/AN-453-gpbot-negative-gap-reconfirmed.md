---
id: AN-453
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-169, AN-297]
title: gpBot's Classic score advantage persists with a slightly smaller gap
provenance: inferred
reversal-cost: low
---

# AN-453 — gpBot's Classic score advantage persists with a slightly smaller gap

## Risk investigated

Whether `dggp.haiku.gpBot_0_1.1.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-297.

## Evidence boundary

The read-only subject jar has SHA-256 `a19725573c6849e5f85f63fecf569745978d77dbefc73280f000c141a695e619`. The official five-pair confirmation `ba887680d551e74f` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `80b8f661c9f6785bfe8f26a9dbe9987dc72d2e82`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,112.4 points and Tank Royale averaged 2,325.4 points, for a −54.52% mean delta. The five pair deltas were −57.7%, −53.0%, −53.5%, −56.7%, and −51.7%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-297's preceding five-pair confirmation had a −55.78% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a slightly smaller mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or gpBot from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

gpBot's Classic score advantage remains confirmed at −54.52%, compared with −55.78% in AN-297. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/divineomega.DivineBot_1.9.5.jar` (`DISCREPANCY (errors)`).

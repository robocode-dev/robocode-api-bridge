---
id: AN-456
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-174, AN-300]
title: BlueBerry's Classic score advantage persists with a smaller gap
provenance: inferred
reversal-cost: low
---

# AN-456 — BlueBerry's Classic score advantage persists with a smaller gap

## Risk investigated

Whether `dmh.robocode.robot.BlueBerry_0.5.jar`'s confirmed Classic score advantage persists under the latest matched artifacts, and whether its magnitude has changed from AN-300.

## Evidence boundary

The read-only subject jar has SHA-256 `3739fb06f3f805c42e7afccd8b0cc17e02916021789c6e504c22688a1ae160fb`. The official five-pair confirmation `7b974e064aa3c422` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `80b8f661c9f6785bfe8f26a9dbe9987dc72d2e82`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,616.8 points and Tank Royale averaged 3,475.0 points, for a −38.16% mean delta. The five pair deltas were −38.4%, −37.8%, −38.6%, −40.5%, and −35.5%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-300's preceding five-pair confirmation had a −40.22% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces the Classic advantage with a smaller mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or BlueBerry from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

BlueBerry's Classic score advantage remains confirmed at −38.16%, compared with −40.22% in AN-300. All five pairs favored Classic, and no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/dmp.micro.Aurora_1.41.jar` (`CONFIRMED (score)`).

---
id: AN-440
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-151, AN-280]
title: NewBGank's score gap is confirmed and its callback error does not recur
provenance: inferred
reversal-cost: low
---

# AN-440 — NewBGank's score gap is confirmed and its callback error does not recur

## Risk investigated

Whether `da.NewBGank_1.4.jar`'s negative score gap persists under the latest matched artifacts, and whether the scan-callback exception that interrupted the prior confirmation recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `e47b2ea746b5bd9680b8dd884b82b822857acbd31071bbec0d7b1647c461b194`. The official five-pair confirmation `d255fabb61eba853` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `78039932ddf7bbb1474c68bd4ffe2df72514d329`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,169.8 points and Tank Royale averaged 4,096.0 points, for a −33.46% mean delta. The five pair deltas were −33.5%, −24.9%, −36.4%, −32.1%, and −40.4%. The registry status is `CONFIRMED (score)`. Neither engine reported errors, and no bridge-only signatures were recorded.

Skipped-turn telemetry was captured in all five attempts. Attempt 4 recorded one event for bot 1 at round 35, turn 272; the other four attempts recorded no events. AN-280's preceding confirmation completed only four score pairs before a `java.lang.ArrayIndexOutOfBoundsException` in `da.NewBGank.onScannedRobot` interrupted the fifth. That callback exception did not recur in this confirmation, though the single skipped-turn event is a separate observation. AN-280's bytecode follow-up identified an unclamped robot-side table index as the likely source of the earlier exception; this retest does not prove that path is permanently clear.

## What was not pursued

The current score difference was not attributed to the bridge, Tank Royale, or NewBGank from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

NewBGank's lower Tank Royale score remains confirmed at −33.46%. The earlier scan-callback exception did not recur across five attempts. One separate skipped-turn event was captured in attempt 4, with no events in the other four. The score-gap cause remains open.

## M-006 handoff

Continue in registry order with `roborumble/daemons.DizzyA_1.0.jar` (`CONFIRMED (score)`).
---
id: AN-409
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Horizon's null-recording exception recurs during the current retest
provenance: inferred
reversal-cost: low
---

# AN-409 — Horizon's null-recording exception recurs during the current retest

## Risk investigated

Whether `ar.horizon.Horizon_1.2.2.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether its earlier Tank Royale-only exception recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `2d8dee69f687581106100d53b46cea3571ed70c88e9d8601b69c48f850c20a70`. The official retest `7c9cfdb1a0ddd7f9` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `960b55b85e93eb90fcfebddf55eca4c38b618977`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The retest attempted two pairs, but only the first completed; the second produced no score. It therefore does not provide a five-pair score confirmation. The one completed pair is a single observation, not a population-wide estimate or deterministic acceptance proof.

## What was tried

The first completed pair scored 4,482 in Classic and 1,760 in Tank Royale, for −60.7%. The second attempted pair stopped before producing a result after the Tank Royale worker exited with code −1. Its log records `NullPointerException: Cannot invoke RobotRecording.getRoundTime() because myPastRecording is null`, originating at `ar.horizon.components.movement.SolarWindMovement.addWave` during `onScannedRobot`. The stack then passes through `BotPeer` callback dispatch and the Bot API event queue. The registry classifies the signature as bridge-only, but that label does not establish ownership. The direct dereference is in the subject robot's method; the available trace does not explain why `myPastRecording` was null or whether bridge/API behavior caused that state.

The same exception signature appeared in the 2026-09-11 observation `c2c7049ebf260676`, which recorded two Tank Royale errors and no score. The 2026-09-28 observation `65fd65676bd7db2f` instead completed with a −65.2% score delta and no recorded errors. The current run records one skipped-turn telemetry sample with zero events; telemetry for the aborted pair is incomplete.

## What was not pursued

The score from one completed pair was not treated as a confirmation, and the current error was not attributed to the bridge or the robot from its stack location alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Horizon remains unresolved. Its lower Tank Royale score was observed once at −60.7%, but the five-pair confirmation stopped after the same robot-method null-recording exception seen in an earlier run. The exception's immediate dereference is located, while the reason for the null state and its cross-engine owner remain open.

## M-006 handoff

Continue in registry order with `roborumble/arthord.micro.Muffin_0.6.1.jar` (`DISCREPANCY (score)`).

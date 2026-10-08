---
id: AN-299
type: analysis
status: active
links: [P-001, CAP-004, CAP-007, C-004]
title: BlackDeath's Tank Royale missing-file outcome repeats with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-299 — BlackDeath's Tank Royale missing-file outcome repeats with the latest artifacts

## Risk investigated

Whether BlackDeath's historical Tank Royale no-score outcome and missing enemy-stat file errors recur under the latest matched artifacts, and whether the current pair clarifies why the files are absent.

## Evidence boundary

The read-only subject jar has SHA-256 `5fc9438915d0beb53da001f7d452062375cbde35532567be7be968f459b4d4a1`. The official observation `706404a00e327f99` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1165900f02513a45d1135b61cc927875cea0eca4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 4,755 with no errors. Tank Royale produced no score and recorded two `java.io.FileNotFoundException` entries for `BlackDeath.data\2.txt` and `BlackDeath.data\1.txt`, plus the worker-without-result entry. One exception was attributed to `dmh.robocode.robot.CommandBasedRobot.loadEnemyStatsFromDataFile`; the other origin is unknown. The registry records the unmatched signature as bridge-only, and skipped-turn telemetry is incomplete because the run stopped before completion. The status is `DISCREPANCY (outcome)`.

AN-173's prior observation also had no Classic errors, no Tank Royale score, and the same missing-file signature. As AN-173 documented, the robot loader catches and prints the exception, then returns; C-004 stops the run when the unmatched signature is detected. The current observation repeats the outcome but does not explain why the files are absent only on the Tank Royale side.

## Finding

BlackDeath's Tank Royale no-score outcome and missing enemy-stat file errors recur. The current run again lacks a Tank Royale score, so no score comparison is available. The exception is visible in the robot file-loader frame, but the reason the files are absent remains unresolved; the evidence does not establish a bridge defect or a robot-owned data-state cause. No code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dmh.robocode.robot.BlueBerry_0.5.jar` (`score-review`).

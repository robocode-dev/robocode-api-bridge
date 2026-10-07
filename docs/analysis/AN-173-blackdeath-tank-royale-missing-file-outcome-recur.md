---
id: AN-173
type: analysis
status: active
links: [P-001, CAP-004, CAP-007, C-004]
title: BlackDeath's Tank Royale missing-file outcome recurs
provenance: inferred
reversal-cost: low
---

# AN-173 — BlackDeath's Tank Royale missing-file outcome recurs

## Risk investigated

Whether BlackDeath's historical Tank Royale no-score outcome recurs with current matched artifacts, and whether the missing enemy-stat file error is attributable to the bridge.

## Evidence boundary

The read-only subject jar `dmh.robocode.robot.BlackDeath_9.2.jar` has SHA-256 `5fc9438915d0beb53da001f7d452062375cbde35532567be7be968f459b4d4a1`. The official observation `bade34a62c6dfd14` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `36c5e2aeaba1f68a786e58077a678e1a0b2e8b93`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Classic completed with a score of 4,991 and no errors. Tank Royale produced no score and recorded two `java.io.FileNotFoundException` entries for `BlackDeath.data\2.txt` and `BlackDeath.data\1.txt`, plus the worker-without-result entry. The registry attributes one exception to the robot application frame `dmh.robocode.robot.CommandBasedRobot.loadEnemyStatsFromDataFile` and the other to `unknown`; the attributed unmatched signature is recorded as bridge-only, and the status is `DISCREPANCY (outcome)`. Tank Royale skipped-turn telemetry is incomplete because the run stopped before completion.

Read-only bytecode inspection of the subject jar shows that `loadEnemyStatsFromDataFile` catches `Exception`, prints the read-error message and stack trace, then returns. The harness detects the unmatched signature in the bot log and stops the battle under the existing C-004 rule. The evidence establishes that the requested files were absent at the reported Tank Royale paths; it does not establish why the corresponding read did not produce a Classic-side error.

The earlier official observation `5a66d8e6f151150b` on 2026-09-11 also recorded Classic score 4,969 without errors, Tank Royale no score, and the same `FileNotFoundException` origin. The outcome therefore recurred. A score comparison is unavailable because C-004 stopped the battle at the asymmetric error; no repeated score confirmation was run.

## Finding

BlackDeath's Tank Royale no-score outcome recurred with current matched artifacts. The visible exception originates in the robot's enemy-stat file loader, which catches and prints the missing-file error; C-004 then stops the asymmetric run before a score is recorded. The underlying reason the files are absent only on the Tank Royale side remains unresolved, so the evidence does not establish a bridge defect or a robot-owned data-state cause. No code or rumble jar change is indicated by this observation.

## M-006 handoff

Continue in registry order with `roborumble/dmh.robocode.robot.BlueBerry_0.5.jar` (`score-review`).

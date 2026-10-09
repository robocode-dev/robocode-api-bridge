---
id: AN-455
type: analysis
status: active
links: [P-001, CAP-004, CAP-007, C-004, AN-173, AN-299]
title: BlackDeath again produces no score from either engine
provenance: inferred
reversal-cost: low
---

# AN-455 — BlackDeath again produces no score from either engine

## Risk investigated

Whether `dmh.robocode.robot.BlackDeath_9.2.jar`'s Tank Royale missing-file outcome recurs under the latest matched artifacts, and whether the retry produces a comparable score from either engine.

## Evidence boundary

The read-only subject jar has SHA-256 `5fc9438915d0beb53da001f7d452062375cbde35532567be7be968f459b4d4a1`. The official retry `ae9af3e4b726f382` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `80b8f661c9f6785bfe8f26a9dbe9987dc72d2e82`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The retry ended after one attempt with zero score samples, so a five-pair score comparison was not available. Both engine records have `ok: false` and null scores; their engine-level error-text arrays are empty. The registry lists bridge-only signatures for `java.io.FileNotFoundException` in `dmh.robocode.robot.CommandBasedRobot.loadEnemyStatsFromDataFile` and `java.lang.ArithmeticException` in `dmh.robocode.robot.BlackDeath.getEnemyLikelyToBeAimingForUs`. Skipped-turn telemetry is incomplete because the run did not complete. The jar remained read-only.

## What was tried

The registry status is `DISCREPANCY (errors)`, with one attempt and no score samples. AN-299's preceding observation had a Classic score of 4,755 and no Classic errors, while Tank Royale had no score and recorded missing `BlackDeath.data` file exceptions. The current retry also produced no Tank Royale score and additionally produced no Classic score; the recorded signature set now includes an arithmetic exception in a BlackDeath method as well as the missing-file loader signature.

## What was not pursued

The current registry record does not retain engine-level error text explaining why both runners ended without scores. The signature locations do not establish why the files are absent or prove that the bridge caused either engine's no-score result. No controlled trace or code change was made during this retry, and the read-only subject jar was not modified.

## Finding

The Tank Royale no-score outcome recurred, and this retry also yielded no Classic score. The registry classifies it as `DISCREPANCY (errors)` after one attempt with zero samples; the previous Classic score cannot be compared with this run. Missing-file and arithmetic-exception signatures are recorded, but their cause remains unresolved.

## M-006 handoff

Continue in registry order with `roborumble/dmh.robocode.robot.BlueBerry_0.5.jar` (`CONFIRMED (score)`).

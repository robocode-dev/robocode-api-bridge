---
id: AN-460
type: analysis
status: active
links: [P-001, CAP-004, CAP-007, C-004, AN-177, AN-303]
title: ShinigamiKNN again produces no score from either engine
provenance: inferred
reversal-cost: low
---

# AN-460 — ShinigamiKNN again produces no score from either engine

## Risk investigated

Whether `doka.ShinigamiKNN_1.0.jar`'s Tank Royale array-bounds failure recurs under the latest matched artifacts, and whether the retry yields comparable scores from both engines.

## Evidence boundary

The read-only subject jar has SHA-256 `7ce1f15f0a454f7f0a29dffe867b728d1ebc56e98b4a9e629f31237a93b1695e`. The official retry `2aebb8eaa24e3e3a` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1bd9c767df8f1312a76bb41b31db858526cae426`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The retry ended after one attempt with zero score samples. Both engine records have `ok: false` and null scores, while their engine-level error-text arrays are empty. The registry lists `java.lang.ArrayIndexOutOfBoundsException` in `doka.ShinigamiKNN.onScannedRobot` as a bridge-only signature, but this retry record does not retain the exception message or occurrence count. Skipped-turn telemetry is incomplete because the run did not complete. The jar remained read-only.

## What was tried

The registry status is `DISCREPANCY (errors)`, with one attempt and no score samples. AN-303's preceding observation had a Classic score of 5,940 with no Classic errors and eight Tank Royale array-bounds error entries, including the same callback signature. Unlike that observation, this retry yielded no score from either engine. A score comparison is unavailable.

## What was not pursued

The current registry record does not retain a detailed exception message or engine-level error text for this attempt. No new source inspection or controlled trace was made, so the callback signature alone does not establish why the run stopped or whether the bridge caused the cross-engine difference. No code or rumble-jar change was made.

## Finding

The registry again records the ShinigamiKNN callback array-bounds signature, but the retry ended with no score from either engine and no samples. It is classified `DISCREPANCY (errors)`; the reason for the current no-score result remains unresolved.

## M-006 handoff

Continue in registry order with `roborumble/donjezza.Jezza_1.0.jar` (`CONFIRMED (score)`).

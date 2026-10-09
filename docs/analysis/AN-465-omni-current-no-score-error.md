---
id: AN-465
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004]
title: Omni still produces no score under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-465 — Omni still produces no score under current artifacts

## Risk investigated

Whether `e32.Omni_0.04.jar`'s historical Tank Royale no-score outcome persists under the current matched artifacts, and whether its earlier runtime failure recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `08f753a1256addbc2c54399fdfeee4e29ba61c04aba969886b38cdb842bf3afb`. The official retry `2aaa137503d409dc` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `89f1aa92da35b79287488a5a9ce63be021d752d7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

The retry ended after one attempt with zero score samples. Both engine records have `ok: false` and null scores; no engine-level error details were retained. The registry lists `java.lang.NoSuchMethodError` in `e32.Omni.run` as a bridge-only signature. Skipped-turn telemetry is incomplete because the run did not complete. The jar remained read-only.

## What was tried

The registry status is `DISCREPANCY (errors)`, with one attempt and no score samples. The earlier 2026-09-12 observation `93f7fce337a4cc1d` recorded a Classic score of 4,615 while Tank Royale produced no score and 809 `NullPointerException` entries associated with `execution.EventAdaptor.onStatus`; that observation used Bot API 1.2.0 and older bridge and runner artifacts. The current retry also produced no scores, but its retained signature differs from the earlier NPE pattern.

## What was not pursued

The current registry record does not retain detailed exception text or establish the cause of the missing method. No source or bytecode inspection was performed during this registry retest, and the new signature does not establish a bridge defect. No code or rumble-jar change was made.

## Finding

Omni's no-score outcome persists, now with no scores from either engine. The run is classified `DISCREPANCY (errors)` after one attempt; the earlier Tank Royale `NullPointerException` signature did not recur in the current record, which instead lists a `NoSuchMethodError` in `e32.Omni.run`. The cause remains unresolved.

## M-006 handoff

Continue in registry order with `roborumble/eem.awful_v1.2.jar` (`DISCREPANCY (no score)`).

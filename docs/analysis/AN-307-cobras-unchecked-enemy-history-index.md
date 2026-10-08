---
id: AN-307
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: CobraBora's enemy-history lookup can index past a shorter history list
provenance: inferred
reversal-cost: low
---

# AN-307 — CobraBora's enemy-history lookup can index past a shorter history list

## Risk investigated

Whether `drm.CobraBora_1.12.jar`'s historical Tank Royale score advantage persists under the latest matched artifacts, and whether its current no-score outcome identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `dcb1af76fd7509e2eeef13fb80a8b45a6184cd47c025315df67e000d37668b1e`. The official retry `bf3c09f87efa7f8a` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `49248a488cd62fcce5233fd0b0e3a32d4c218182`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The confirmation stopped after one attempt with no usable score from either engine, so no pair delta or five-pair mean was calculated. Tank Royale's bot log recorded `ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 10` in `drm.common3.Brain.getLogTraceEnemyImpactPoint`, called by `drm.parts.TricoloreDriver.aim` at line 1170. Tank Royale skipped-turn telemetry was incomplete because the run did not finish. The registry status is `DISCREPANCY (errors)`.

Read-only bytecode inspection mapped the failing `Brain` instruction to an `enemyStoreList.elementAt` lookup. Its loop guard checks an index derived from the first enemy history list, then dereferences the second enemy history list at `size - j - 1` without checking that list's size. `TricoloreDriver.aim` reaches this call with a previous-round enemy snapshot and the current enemy record, whose history lengths can differ. This identifies an unchecked bounds assumption in the robot's own bytecode; it does not establish why the two history lengths differ in this run.

AN-181's earlier five-pair confirmation scored a +39.98% mean Tank Royale advantage without errors, using older bridge and Tank Royale commits. The current failure prevents comparison against that score result.

## Finding

The current no-score run is interrupted by an unchecked index in the read-only CobraBora jar. The stack does not identify a bridge frame as the failure source, and the available evidence does not establish a bridge defect or explain the differing history lengths. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/aaa.r.ScalarR_0.005g.047.jar` (`DISCREPANCY (errors)`).

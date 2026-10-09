---
id: AN-356
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-020, AN-080, AN-129, AN-264]
title: Firestarter's empty-history index blocks current M-006 retest
provenance: inferred
reversal-cost: low
---

# AN-356 — Firestarter's empty-history index blocks current M-006 retest

## Risk investigated

Whether `cb.fire.Firestarter_2.0f.jar`'s historical Tank Royale no-score errors persist under the latest M-006 artifacts, and whether the current failure is the previously diagnosed bridge message-batch defect.

## Evidence boundary

The read-only subject jar SHA-256 is `de45410b59fea6b8fad9463ece161e1e1656960129f1b9bc84791137d788325f`. Observation `baf263e9cdc11760` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `17ea6230758302718d6dd4b2c996214759d1205d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The run stopped after one attempt with no score from either engine, zero completed confirmation samples, and incomplete skipped-turn telemetry. Tank Royale reported a bridge-only `java.lang.IndexOutOfBoundsException` at `C.L.B`; the registry status is `DISCREPANCY (errors)`.

The earlier official observation `2737fd38e58b8343` also produced no Tank Royale score and recorded three `IndexOutOfBoundsException` errors at `C.L.B`, along with a separate `NullPointerException` at `C.I.I`. AN-129's bytecode investigation tied `C.L.B` to an unchecked index-zero read from an empty robot history. The current signature preserves the exception type and origin but not the index value. The registry's earlier bridge diagnosis concerns the separate team-message batch-size defect; this observation adds a robot-owned diagnosis for the current `C.L.B` signature and preserves the prior diagnosis event.

## Finding

Firestarter's current retest is blocked by the previously identified robot-owned empty-history index error, not by the earlier bridge batching signature. No score parity result is available, and this observation does not identify a new bridge defect. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cb.nano.Insomnia_1.0.jar` (`DISCREPANCY (errors)`).

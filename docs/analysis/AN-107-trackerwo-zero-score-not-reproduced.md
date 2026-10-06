---
id: AN-107
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: TrackerWO's historical Classic zero score is not reproduced in the current pair
provenance: inferred
reversal-cost: low
---

# AN-107 — TrackerWO's historical Classic zero score is not reproduced in the current pair

## Risk investigated

Whether Grystrion.TrackerWO's historical no-score discrepancy persists with the current matched artifact pair.

## Evidence boundary

The read-only subject jar `Grystrion.TrackerWO_1.0.jar` has SHA-256 `b371a104375e94e4c8db29515528481779b1dba303b8042715c168a25a395416`. The current official observation `448ff4ff0dbd70ad` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `3d7e6c6cee678feee9a8d782dba84b37edac785e`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 6,400 in Classic and 4,848 in Tank Royale, a −24.2% delta, with zero errors on both engines and no skipped-turn events. The registry status is `PASS` under the existing 25% threshold. This is one pair and sits close to the threshold; no five-pair confirmation was run.

The historical observation `ac36cf3c058f2c58` scored zero in Classic and 5,022 in Tank Royale, with no reported errors. It used bridge commit `975ed11a8fa5482146838f0a66158ca83039c548`, Tank Royale commit `a553d8069e3f67e8711f0a3e976c339f389faec8`, Bot API 1.2.0, and the older examples runner. The historical artifact pair was not retested.

## Finding

The historical Classic zero-score result does not recur in the current official pair, which falls within the review threshold and has no runtime errors. Because the current pair's delta is close to the threshold, treat this as a current `PASS` observation rather than a repeated estimate of score variability. The evidence does not isolate why the older artifact pair produced zero in Classic. No code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/Krabb.sliNk.Garm_0.9u.jar`.

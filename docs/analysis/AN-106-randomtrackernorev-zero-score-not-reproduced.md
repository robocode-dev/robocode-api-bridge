---
id: AN-106
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: RandomTrackerNOREV's historical Classic zero score does not recur with current artifacts
provenance: inferred
reversal-cost: low
---

# AN-106 — RandomTrackerNOREV's historical Classic zero score does not recur with current artifacts

## Risk investigated

Whether Grystrion.RandomTrackerNOREV's historical no-score discrepancy persists with the current matched artifact pair.

## Evidence boundary

The read-only subject jar `Grystrion.RandomTrackerNOREV_1.0.jar` has SHA-256 `d60f5b48a2def5c439757387637014db8044a99b4679e0471b4041854944dab3`. The current official observation `9f4e59e2851f119a` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `7b1a35261f50991c103c4e1f63f04647956e7dba`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 7,372 in Classic and 6,557 in Tank Royale, a −11.1% delta, with zero errors on both engines and no skipped-turn events. The registry status is `PASS` under the existing 25% threshold.

The historical observation `a633af2142797af3` scored zero in Classic and 5,877 in Tank Royale, with no reported errors. It used bridge commit `975ed11a8fa5482146838f0a66158ca83039c548`, Tank Royale commit `a553d8069e3f67e8711f0a3e976c339f389faec8`, Bot API 1.2.0, and the older examples runner. The historical artifact pair was not retested.

## Finding

The historical Classic zero-score result does not recur in the current official pair, which is within the review threshold and has no runtime errors. The evidence does not isolate why the older artifact pair produced zero in Classic. Leave that historical cause unassigned; no code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/Grystrion.TrackerWO_1.0.jar`.

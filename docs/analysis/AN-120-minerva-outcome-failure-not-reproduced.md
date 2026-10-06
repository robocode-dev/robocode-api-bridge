---
id: AN-120
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Minerva's historical Tank Royale outcome failure does not recur
provenance: inferred
reversal-cost: low
---

# AN-120 — Minerva's historical Tank Royale outcome failure does not recur

## Risk investigated

Whether boe.Minerva's historical Tank Royale no-score failure and robot-frame exception recur with the current matched artifacts.

## Evidence boundary

The read-only subject jar `boe.Minerva_0.80.jar` has SHA-256 `79dd5b1ad699fe82fc423f2a35d3264fd460c787fef7c528a9f897d88365a5d6`. The current official observation `06a557a257f42259` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `dbca53cfcc5f81386d14edc73a5c975069a11720`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 6,460 in Classic and 6,352 in Tank Royale, a −1.7% delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

The earlier observation `06462108430f485c` from 2026-09-11 had no Tank Royale score and three Tank Royale `ArrayIndexOutOfBoundsException` errors with origin `boe.Minerva.antiGravCalculate`; Classic scored 6,261 without errors. An earlier observation `8205a7ec0f3730f8` from 2026-09-08 had no errors and a −9.3% delta. The current run did not reproduce the exception or no-score outcome.

## Finding

Minerva passes the current score and error checks, and its historical Tank Royale outcome failure does not recur with the current matched artifacts. The old exception's cause remains unassigned because its origin was not independently investigated. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bons.NanoStalker_1.2.jar` (`score-review`).

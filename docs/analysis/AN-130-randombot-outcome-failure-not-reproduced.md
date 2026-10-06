---
id: AN-130
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: RandomBot's historical Tank Royale failure does not recur
provenance: inferred
reversal-cost: low
---

# AN-130 — RandomBot's historical Tank Royale failure does not recur

## Risk investigated

Whether cb.mega.RandomBot's historical Tank Royale no-score failure recurs with current matched artifacts.

## Evidence boundary

The read-only subject jar `cb.mega.RandomBot_1.0.jar` has SHA-256 `34a1049d295fe3ae436785ef0ba1e74824f059c4b2478b9975522c1698816285`. The current official observation `3b670e9d9d0dcdbf` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `cd6bf4f5b5b1b4b70f7dc4d2eafa228697e5a2e9`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 5,972 in Classic and 5,449 in Tank Royale, a −8.8% delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

The earlier observation `9f919c351f22baa4` from 2026-09-11 had no Tank Royale score and three Tank Royale errors, including a `NullPointerException` originating at `cb.mega.ComponentRobot.run`; Classic scored 5,673 without errors. The earlier observation `55bade0feb6ac865` also had no Tank Royale score and three errors, but no signature. The current pair did not reproduce the prior exception or no-score outcome.

## Finding

RandomBot passes the current score and error checks, and its historical Tank Royale outcome failure does not recur with current matched artifacts. The recorded old exception origin names a robot class, but its cause was not independently investigated. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/cb.nano.Insomnia_1.0.jar` (`score-review`).

---
id: AN-113
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Ololobot's historical Tank Royale crash does not recur with current artifacts
provenance: inferred
reversal-cost: low
---

# AN-113 — Ololobot's historical Tank Royale crash does not recur with current artifacts

## Risk investigated

Whether `az.Ololobot 0.2.4`'s historical Tank Royale no-score failure recurs with the current matched artifacts, and whether the available evidence attributes it to the bridge.

## Evidence boundary

The read-only subject jar `az.Ololobot_0.2.4.jar` has SHA-256 `b6938411c048bde80bf0186892f03342e0c60dff05127052ed21932825765eb9`. The current official observation `694f876a9c4ba524` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `d2c2a0194193ab5fbdd99f7dafa09d4cdf7da656`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 10,161 in Classic and 10,194 in Tank Royale, a +0.3% delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

The earlier observation `90ed0c87e0e6d487` from 2026-09-11 had no Tank Royale score and 139 Tank Royale errors, with `ArrayIndexOutOfBoundsException` originating at `az.Ololobot.onScannedRobot`; Classic scored 9,982 with no errors. The earlier 2026-09-08 observation `5cf402dd473c1227` also had no Tank Royale score and 189 errors, but stored no error signature. The current run did not reproduce either the no-score outcome or the recorded exception.

## Finding

Ololobot passes the current single-pair score and error checks. The historical robot-origin exception does not recur under the current matched build pair; the evidence does not establish what changed or why it stopped recurring. Keep the old cause unassigned and use the registry's current `PASS` status; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bayen.UbaRamLT_1.0.jar` (`score-review`).

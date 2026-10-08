---
id: AN-264
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Firestarter's robot-owned Tank Royale errors recur with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-264 — Firestarter's robot-owned Tank Royale errors recur with the latest artifacts

## Risk investigated

Whether `cb.fire.Firestarter_2.0f.jar`'s Tank Royale no-score outcome and robot-frame errors recur under the latest matched artifacts, and whether they identify a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `de45410b59fea6b8fad9463ece161e1e1656960129f1b9bc84791137d788325f`. The official observation `2737fd38e58b8343` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 4,365 with no errors. Tank Royale produced no score and five errors: one worker-no-result message, three `IndexOutOfBoundsException: Index 0 out of bounds for length 0` messages from `C.L.B`, and one `NullPointerException` from `C.I.I` because the robot's `atan2` field was null. Skipped-turn telemetry was incomplete. The registry status remains `DISCREPANCY (outcome)`.

AN-129's disassembly linked the `C.L.B` access to an unchecked index-zero read on an empty robot list and the `C.I.I` access to the robot's uninitialized wall rectangle. Both error families recur with the latest matched artifacts. The registry's mechanical bridge-only signature field reflects that Classic did not report matching exceptions; the recorded frames and prior bytecode inspection place these failures in the robot itself.

## Finding

Firestarter's Tank Royale no-score outcome and both previously identified robot-owned error families recur. The current observation does not identify a bridge defect or explain why the robot reaches those unchecked states. No code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/cb.mega.RandomBot_1.0.jar`, whose current registry status is `PASS`, and `roborumble/cb.nano.Insomnia_1.0.jar`, whose current status is `MATCHED (score noise)`. Continue with `roborumble/cbot.agile.Nibbler_0.2.jar` (`DISCREPANCY (errors)`).

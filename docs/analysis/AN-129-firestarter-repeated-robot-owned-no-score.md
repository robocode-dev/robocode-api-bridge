---
id: AN-129
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Firestarter's repeated no-score outcome follows unchecked robot state
provenance: inferred
reversal-cost: low
---

# AN-129 — Firestarter's repeated no-score outcome follows unchecked robot state

## Risk investigated

Whether cb.fire.Firestarter's historical Tank Royale no-score failure recurs with current matched artifacts, and whether the errors identify a bridge defect.

## Evidence boundary

The read-only subject jar `cb.fire.Firestarter_2.0f.jar` has SHA-256 `de45410b59fea6b8fad9463ece161e1e1656960129f1b9bc84791137d788325f`. The current official observation `fa6b9b4c0ad0f91b` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `7e0d5840c38df36f21fab31b5007bc0fab57ab2b`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 4,397 with no errors. Tank Royale produced no score and five errors: a worker-no-result message and four `IndexOutOfBoundsException: Index 0 out of bounds for length 0` messages. The captured signatures include `C.L.B` and `unknown`; skipped-turn telemetry is incomplete because the worker did not produce a result. The registry status is `DISCREPANCY (outcome)`.

The earlier observations `2002e30ad998c641` and `89623ea5edf609f2` also had no Tank Royale score. They reported a `NullPointerException` when `Rectangle2D$Double.contains` was called while the robot's `atan2` field was null; the latter records origin `C.I.I`. Read-only disassembly of the same subject jar shows that `C.L.B(Point2D)` calls `ArrayList.get(0)` without checking whether the list is empty, matching the current exception at `C.L.B`. It also shows that `C.I.I` uses `atan2` before checking it, while a separate `I(double,double)` method initializes that rectangle. The default constructor leaves it unset. The registry records the current empty-list access as `robot-empty-list-index-zero` and the historical null rectangle as `robot-uninitialized-wall-rectangle`, both owned by the robot.

## Finding

Firestarter's Tank Royale no-score outcome recurs, and the current exception matches an unchecked index-zero read in its own bytecode. The earlier NPE also follows an unchecked robot field that is initialized only by a separate method. The evidence assigns both causes to robot code; it does not explain why those states occur during these battles. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/cb.mega.RandomBot_1.0.jar` (`DISCREPANCY (outcome)`).

---
id: AN-132
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Nibbler's recurring Classic-only errors come from dereferencing a null Pray value
provenance: inferred
reversal-cost: low
---

# AN-132 — Nibbler's recurring Classic-only errors come from dereferencing a null Pray value

## Risk investigated

Whether cbot.agile.Nibbler's historical Classic-only exceptions recur with current matched artifacts, and whether they indicate a bridge defect.

## Evidence boundary

The read-only subject jar `cbot.agile.Nibbler_0.2.jar` has SHA-256 `b1b79c3c45f03c2098bee75bd1c379e51b8e45f66418b0ac15a9e9747fef1689`. The current official observation `ed60f44fa4bdb311` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `bbfafd115cef4ba7da0f448763ded81c5d1d89ee`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 5,592 and reported four `NullPointerException` errors: `Cannot invoke "cbot.agile.Pray.getDistance()" because "pray" is null`. Tank Royale scored 6,535 with no errors and captured an empty skipped-turn event list. The score delta was +16.9%; the registry status is `DISCREPANCY (errors)`.

Earlier observations `50feb7c195d9d53e` and `a743104b456df95b` also reported multiple Classic NPEs with zero Tank Royale errors. The latter recorded origin `cbot.agile.driver.RandomPointDriver.getStopTicks`. Read-only disassembly of the subject jar shows `RandomPointDriver.getStopTicks(Pray)` invokes `Pray.getDistance()` directly without checking whether its argument is null. This matches the current exception message and historical origin. The registry records the cause as `robot-null-pray-in-get-stop-ticks`, owned by the robot.

## Finding

Nibbler's Classic-only errors recur, and the exception matches an unchecked nullable argument in the robot's `RandomPointDriver` bytecode. The evidence explains the null dereference but not why `pray` is null at that call site. Tank Royale had no errors in the current run; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/chickenfuego.UrChicken2_1.0.jar` (`score-review`).

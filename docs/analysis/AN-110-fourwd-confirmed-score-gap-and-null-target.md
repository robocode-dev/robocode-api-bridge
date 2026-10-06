---
id: AN-110
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: FourWD's score gap is confirmed while its Classic null-target exception is robot-owned
provenance: inferred
reversal-cost: low
---

# AN-110 — FourWD's score gap is confirmed while its Classic null-target exception is robot-owned

## Risk investigated

Whether ary.FourWD's score discrepancy persists with current matched artifacts and whether its repeated Classic-side NullPointerException indicates a bridge defect.

## Evidence boundary

The read-only subject jar `ary.FourWD_1.3d.jar` has SHA-256 `bfe8768af3401df35cf4d4ec8e3e1d47d779cc5dfc284b212fda3153dbd21537`. Current observations `bbbb7dda58f5e183` and `d809601a19bd3eed` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `d213a6f0c4659367f08ebdc3ec90460a7bc42cd2`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. Both used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The first current official pair scored 5,330 in Classic and 3,656 in Tank Royale, a −31.4% delta. Classic reported 67 NullPointerExceptions and Tank Royale reported one. Both sides produced the same exception class and robot frame, `ary.FourWD.run`; there were no bridge-only signatures. The historical observations also place the NPE in `ary.FourWD.run` during Classic runs.

The official five-pair score confirmation produced Classic/Tank Royale scores of 5,280/3,273, 5,282/3,251, 5,439/3,347, 5,262/3,568, and 5,117/3,310. The mean scores were 5,276 and 3,349.8, with a −36.5% mean delta. All five score pairs were valid, no skipped turns were captured, and the registry status is `CONFIRMED (score)`.

The jar bundles bytecode but no Java source. Disassembly shows the robot's `run()` loop calls `VirtualBullet.distance(tar)` without checking `tar`; `onDeath()` and `onWin()` call `endRound()`, which sets `tar` to null. That call path matches the logged `Point2D.getX()` failure when the distance method receives a null point. The registry records the exception as robot-owned under `robot-null-target-in-virtual-bullet-distance`.

## Finding

FourWD has a confirmed current score gap with no skipped-turn events. The repeated NPE is explained by the robot clearing `tar` and later using it as a point-distance argument without a null check; its matching error frame is present on both engines, although the one-pair error counts differ substantially. This robot defect does not explain the score gap in the five confirmation pairs. Keep the score cause open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/ary.Help_1.0.jar`.

---
id: AN-276
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Blur's large negative score gap is fully confirmed without the prior null-wave interruption
provenance: inferred
reversal-cost: low
---

# AN-276 — Blur's large negative score gap is fully confirmed without the prior null-wave interruption

## Risk investigated

Whether `cx.micro.Blur_0.2.jar`'s large negative score gap persists under the latest matched artifacts and whether the previous null-wave interruption recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `a1d62b084d88196ff938d3f189e0f7a84f89e7f034bd21f83a8afb0bc20ba979`. The official five-pair confirmation `09cdaa1dbaada460` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ad1b8b818512d466fdf419caf51c0887e86405d3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 7,569 in Classic and 1,905.6 in Tank Royale, a −74.78% mean delta. The five pair deltas were −77.6%, −76.1%, −73.2%, −70.8%, and −76.2%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-147 had three valid pairs averaging −75.27%; its fourth attempt ended with a robot-origin `NullPointerException` in `onHitByBullet`, leaving the confirmation incomplete. The current five-pair confirmation completes without that error and reproduces a similar large negative score gap.

## Finding

Blur's large negative score gap is fully confirmed under the latest matched artifacts. The prior null-wave interruption does not recur in this confirmation. No runtime errors or captured skipped turns occurred, and the behavioral cause of the score gap remains open. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cx.micro.Spark_0.6.jar` (`CONFIRMED (score)`).

---
id: AN-289
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DOne's Classic score advantage persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-289 — DOne's Classic score advantage persists with the latest artifacts

## Risk investigated

Whether `davv.DOne_b002.jar`'s historical Classic score advantage persists under the latest matched artifacts after correcting the wrapper's `JuniorRobot` handling.

## Evidence boundary

The read-only subject jar has SHA-256 `c4247ac7dade8278e7bdcb028af8c14d4850654318798c694a558df07d8b6a2e`. The official five-pair confirmation `0818c28ea7fff59b` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `70a5022e8292d1185c1a4cf15065869d55e282ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The wrapper, bridge API, Bot API, and runner artifacts were the local builds recorded in the registry; the subject jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,537.2 points and Tank Royale averaged 2,369.8, for a −57.26% mean delta. Pair deltas were −60.6%, −43.4%, −58.0%, −64.1%, and −60.2%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-159's post-wrapper-fix confirmation averaged 5,423.2 in Classic and 2,507.0 in Tank Royale, a −53.66% mean delta. The current run reproduces the Classic advantage with a slightly larger magnitude and without the earlier wrapper-generated failure.

## Finding

DOne's Classic score advantage persists under the latest matched artifacts after the `JuniorRobot` wrapper correction. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge API or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dcs.PM.Eater_of_Worlds_PM_1.2.jar` (`score-review`).

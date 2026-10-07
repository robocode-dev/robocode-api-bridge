---
id: AN-159
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DOne's Classic score advantage is confirmed after fixing JuniorRobot wrapping
provenance: inferred
reversal-cost: low
---

# AN-159 — DOne's Classic score advantage is confirmed after fixing JuniorRobot wrapping

## Risk investigated

Whether DOne's historical Classic score advantage persists with current matched artifacts, after separating a wrapper-generated failure from robot behavior.

## Evidence boundary

The read-only subject jar `davv.DOne_b002.jar` has SHA-256 `c4247ac7dade8278e7bdcb028af8c14d4850654318798c694a558df07d8b6a2e`. The successful official confirmation `9658210601e3d8d6` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `b729508c17d0ad2aba0a0e81ba39566793ef2137`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, and the corrected wrapper artifact SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The other matched artifacts are recorded in the registry. The jar remained read-only.

## What was tried

The first confirmation attempt `f4df9e2a94a4c672` stopped after one attempt with zero score samples and an incomplete skipped-turn capture. Its wrapper artifact was `839d3759bfc4d5a1171baa7b0acb252f1c340fc73aaceabcc556da2a5908e04f`; the generated `DOne.class` called `davv.DOne.getEnergy()`, which does not exist. DOne extends `JuniorRobot`, whose public `energy` field is set to −1 by `stopThread()` to end transformed loops. The wrapper's generic `getEnergy()` rewrite caused the `NoSuchMethodError`; this attempt was excluded from the score comparison. The rumble jar was not modified.

After rebuilding the wrapper to read `JuniorRobot.energy`, the five official score deltas were −43.3%, −68.6%, −34.5%, −35.1%, and −86.8%. Classic averaged 5,423.2 points and Tank Royale averaged 2,507.0, for a −53.66% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `8948876072cf5515` and `f60c95154076199f` recorded −53.3% and −39.9% single-pair deltas without errors, using older Tank Royale artifacts.

## Finding

DOne's Classic score advantage is confirmed with current matched artifacts and agrees with its earlier observations. The initial no-score result came from the wrapper's `JuniorRobot` loop rewrite, not from the read-only robot jar; the wrapper fix now uses the field that `stopThread()` sets to terminate the loop. The behavioral cause of the score gap remains unknown. No robot jar or bridge API change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dcs.PM.Eater_of_Worlds_PM_1.2.jar`, whose five-pair retest is recorded in AN-160.

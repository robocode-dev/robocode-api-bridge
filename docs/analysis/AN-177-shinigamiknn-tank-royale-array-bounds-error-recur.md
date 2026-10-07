---
id: AN-177
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ShinigamiKNN's Tank Royale array bounds error recurs
provenance: inferred
reversal-cost: low
---

# AN-177 — ShinigamiKNN's Tank Royale array bounds error recurs

## Risk investigated

Whether ShinigamiKNN's historical Tank Royale-only no-score outcome recurs with current matched artifacts, and whether the observed array bounds error identifies a bridge defect.

## Evidence boundary

The read-only subject jar `doka.ShinigamiKNN_1.0.jar` has SHA-256 `7ce1f15f0a454f7f0a29dffe867b728d1ebc56e98b4a9e629f31237a93b1695e`. The official observation `bb3fa0181618c564` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `bbb47ea2f13501813d829175a420055aa2c4e952`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Classic completed with a score of 5,799 and no errors. Tank Royale produced no score and recorded two `java.lang.ArrayIndexOutOfBoundsException` entries, both with message `Index 32 out of bounds for length 32`, plus the worker-without-result entry. The registry attributes one exception to `doka.ShinigamiKNN.onScannedRobot` and the other to `unknown`; the attributed unmatched signature is recorded as bridge-only, and the status is `DISCREPANCY (outcome)`. Tank Royale skipped-turn telemetry is incomplete because the run stopped before completion.

Read-only bytecode inspection shows that `onScannedRobot` creates an `int[32]`, computes a histogram bucket as `int((guessfactor + 1) / 2 * 32)`, and indexes the array without clamping that bucket. The exception is consistent with a computed index of 32. The runtime log does not include the input `guessfactor`, so it does not establish why the value reached or exceeded the upper endpoint. The later stack frames show the bridge dispatching the robot callback; they do not identify the bridge as the failing instruction.

The earlier official observation `234f9c8e91875812` on 2026-09-11 also recorded Classic score 6,040 without errors, Tank Royale no score, and the same `Index 32 out of bounds for length 32` error from `onScannedRobot`. The outcome therefore recurred. A score comparison is unavailable because C-004 stopped the battle at the asymmetric error; no repeated score confirmation was run.

## Finding

ShinigamiKNN's Tank Royale-only array bounds failure recurred. The direct failure is an unguarded histogram index in the robot's `onScannedRobot` method; the evidence does not determine whether an engine input difference exposed the endpoint or whether the robot would also reach it under Classic with another event sequence. This observation alone does not justify a bridge code or rumble jar change.

## M-006 handoff

Continue in registry order with `roborumble/donjezza.Jezza_1.0.jar` (`score-review`).

---
id: AN-701
type: analysis
status: active
links: [P-001, PDR-001, PDR-003, AN-015, C-007]
title: DNGEN's generated callback code throws invalid-bound and divide-by-zero failures
provenance: inferred
reversal-cost: low
---

# AN-701 — DNGEN's generated callback code throws invalid-bound and divide-by-zero failures

## Risk investigated

Whether the errors in `meleerumble/darwinsnightmare.DNGEN474UI85390C20S0R528_1.0.jar` identify a bridge mismatch or defects in the read-only generated robot code, and how much of the result comes from the fixed melee opponent pool.

## Evidence boundary

The read-only jar has SHA-256 `2b8d7ca823fb0b2400e822a1fa34074599768cc2493c6aa2031e2784f66a494b`. Its official MeleeRumble observation is `20c0889df641babe` with 35 rounds, ten participants, and a 1000×1000 arena. The registry pins the matched bridge and Tank Royale artifacts. The jar's metadata says source was included, but the archive contains only the class file and properties; bytecode was inspected without changing the jar.

## What was tried

Classic scored 114,924 and recorded 838 errors. Tank Royale produced no score and recorded 330 errors, including `IllegalArgumentException: bound must be positive` and `ArithmeticException` in the robot's `onHitByBullet` callback. Both engines also recorded the known `amk.guns.Aristocles.prepare` and `amk.ChumbaMini.saveData` errors from the fixed melee opponent pool documented in AN-015. Tank Royale additionally recorded incomplete process-tree cleanup, and the batch stopped after this case.

## Source review

Bytecode inspection shows `onHitWall` and `onHitByBullet` passing rounded velocity or bearing values to `Random.nextInt(int)`, which requires a positive bound. The `onHitByBullet` callback also divides one random integer by another without guarding against zero. These operations account for the robot-owned invalid-bound and divide-by-zero signatures; the same invalid-bound failure appears in both engines, and the zero divisor produced an additional `ArithmeticException` in Tank Royale.

## Finding

The subject-owned errors follow directly from unchecked random bounds and a possible zero divisor in the legacy robot. The remaining high error counts include the known opponent-pool failures. Tank Royale produced no score, so the result does not provide a clean score comparison or establish a bridge defect.

The registry causes are `robot-random-bound-and-zero-divisor`, owner `robot`, and `melee-opponent-pool-contamination`, owner `harness`. No bridge code changed and no rumble jar was modified.

## Implications for M-006

This case is diagnosed as robot-owned errors plus opponent contamination. Its parity result remains incomplete because Tank Royale returned no score.

## M-006 handoff

Continue collection coverage with the next unregistered MeleeRumble jar, `meleerumble/davidalves.net.DuelistMicroMelee_1.4.jar`.

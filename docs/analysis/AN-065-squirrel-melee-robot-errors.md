---
id: AN-065
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-064]
title: Squirrel's missing score repeats robot-owned indexing and null-scan errors
provenance: inferred
reversal-cost: low
---

# AN-065 — Squirrel's missing score repeats robot-owned indexing and null-scan errors

## Risk investigated

Whether the current `meleerumble/bayen.nut.Squirrel_1.615.jar` outcome discrepancy identifies the bridge, the subject robot, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `16e179f041de685fbe45ebcc94b8f2f57cde891552e364d1f58b7779067dc13c`. The current official one-pair observation `beacf992d9a2c81c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `a7a6b145902a977555a215884b16cd344c9266d6`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The current run scored 114,843 in Classic with 966 errors. Tank Royale produced no score and recorded 440 errors, so the registry remains `DISCREPANCY (outcome)`. The earlier observation `0e555108c857e84e` had the same missing-score outcome, with 1,372 Classic errors and 913 Tank Royale errors.

The current exception parser names `bayen.nut.GFTWave1.setSegmentations` for `ArrayIndexOutOfBoundsException` and `bayen.nut.Squirrel.onScannedRobot` for `NullPointerException`. Read-only source inspection shows `GFTWave1` allocates five distance segments, calculates `distanceIndex` from distance without clamping it, and indexes `statBuffers[distanceIndex]`; the same segmentation exception occurs on Classic and Tank Royale. `Squirrel.onScannedRobot` assigns `lastScan` only while `getTime() < 30`, then dereferences `lastScan` without checking for null. That null exception occurs on Tank Royale in both recorded runs and was not reported in the current Classic signatures. The observation does not capture the first scan time needed to explain why the field remained null, so this evidence does not establish a bridge timing defect.

Classic also reports the known fixed-pool `SecurityException` from `amk.ChumbaMini.saveData` and `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare`; these classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some error origins remain `unknown`. This matches the opponent-pool findings in AN-015 and AN-049 through AN-064.

## Finding

The missing-score outcome reproduces. The signatures identify unchecked array and null assumptions in the read-only robot source, plus the established Classic opponent-pool errors. The available evidence does not prove why the first qualifying scan arrives after the robot's `getTime() < 30` guard, so do not attribute that timing to the bridge. Tag the subject errors `robot-unchecked-wave-index-and-null-last-scan`, owner `robot`, and the fixture errors `melee-opponent-pool-contamination`, owner `harness`. Keep `DISCREPANCY (outcome)` and do not edit the rumble jars.

## M-006 handoff

Continue in registry order with `meleerumble/bigpete.Stewie_1.0.jar`.

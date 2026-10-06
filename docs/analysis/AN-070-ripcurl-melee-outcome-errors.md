---
id: AN-070
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-069]
title: RipCurl's missing score repeats a robot-origin negative index error
provenance: inferred
reversal-cost: low
---

# AN-070 — RipCurl's missing score repeats a robot-origin negative index error

## Risk investigated

Whether the current `meleerumble/bts.wiki.RipCurl_0.9b.jar` Tank Royale no-score outcome identifies the bridge, the subject robot, or only the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `1f9a52c3ea49c22e5755defaaf43f355801169537f7a9636d1708bd00064e4c9`. The current official one-pair observation `0bf6db46a5da3a18` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `1db331504c394b7100d891b3165a34e3c82bf458`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The current run scored 112,766 in Classic with 534 errors. Tank Royale produced no score and recorded two errors, leaving the registry at `DISCREPANCY (outcome)`. The earlier observation `8de5e7872b68c360` also had no Tank Royale score, with three errors.

Both Tank Royale observations name `us.bluetorch.robocode.movement.MinimumRisk.onScannedRobot` in an `ArrayIndexOutOfBoundsException`; the current error is `Index -1 out of bounds for length 2`. Read-only bytecode inspection confirms this method is in the subject jar and accesses the robot's movement-stat arrays. The bundled source is absent, so this finding does not claim which computed input becomes negative or rule out a future bridge-input investigation. The available stack evidence identifies the failing method as robot-owned.

Classic's current signatures also name `amk.ChumbaMini.saveData` for the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` for the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. The error mix matches the pinned-pool failures established in AN-015 and observed in AN-049 through AN-069.

## Finding

The Tank Royale no-score outcome reproduces with a negative index exception in the subject's `MinimumRisk.onScannedRobot`, alongside the fixed-pool errors in Classic. Tag the robot failure `robot-minimum-risk-negative-index`, owner `robot`, and the fixture errors `melee-opponent-pool-contamination`, owner `harness`. Keep `DISCREPANCY (outcome)`; no score delta can be computed. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.fnr.Fenrir_0.36l.jar`.

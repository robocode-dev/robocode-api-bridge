---
id: AN-073
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-072]
title: Micro Freya's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-073 — Micro Freya's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/bvh.micro.Freya_0.3.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `97868a0fa8ea022df4e19ae810f3a098da8c0d897ebd4564c1afe2b9f9002890`. The current official one-pair observation `bc060964bbc23284` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `8229fec89e3c09ce823aa5f5deff693da4b569c2`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 113,270 with 206 errors; Tank Royale scored 112,194 with zero errors. The single-pair score delta was −0.9%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-072. The earlier observation `ccc68151c7d9cdd0` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Micro Freya or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain `DISCREPANCY (errors)` while Classic still reports the fixture errors. The −0.9% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.mini.Fenrir_0.39.jar`.

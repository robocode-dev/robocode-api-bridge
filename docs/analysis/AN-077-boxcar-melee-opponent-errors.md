---
id: AN-077
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-076]
title: BoxCar's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-077 — BoxCar's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/bzdp.BoxCar_2.0.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `22e13c11b29fb3ed958288b38d67ab6ae02bd899f16ddcf96295f621e795ecb6`. The current official one-pair observation `b4d7385895ba5fac` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `6c1f65d0a98cfd91d236a49996d0637cf1001f53`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 115,214 with 984 errors; Tank Royale scored 112,782 with zero errors. The single-pair score delta was −2.1%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-076. The earlier observation `36c414b3475bc35a` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors. The registry already carries `melee-opponent-pool-contamination`, owner `harness`, for this subject.

## Finding

The current named Classic errors belong to the fixed opponent pool, not BoxCar or a Tank Royale exception. Retain `DISCREPANCY (errors)` while Classic still reports the fixture errors. The −2.1% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/bzdp.Pansy_2.1.jar`.

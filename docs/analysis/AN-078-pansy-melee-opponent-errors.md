---
id: AN-078
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-077]
title: Pansy's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-078 — Pansy's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/bzdp.Pansy_2.1.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `71057e19022678ba19a873854afb4bd32a986ae77c5105c79cb549ab664ff939`. The current official one-pair observation `71bc3037569ffa27` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `5901e9fc0c074e08a00a01e0cdb0e4e98c912685`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 113,472 with 356 errors; Tank Royale scored 107,954 with zero errors. The single-pair score delta was −4.9%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-077. The earlier observation `99b9848bba0aaf07` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors. The registry already carries `melee-opponent-pool-contamination`, owner `harness`, for this subject.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Pansy or a Tank Royale exception. Retain `DISCREPANCY (errors)` while Classic still reports fixture errors. The −4.9% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/caimano.Furia_Ceca_0.22.jar`.

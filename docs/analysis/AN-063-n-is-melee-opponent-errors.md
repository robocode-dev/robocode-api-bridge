---
id: AN-063
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-062]
title: N's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-063 — N's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/baal.nano.N_1.42.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `a653da798755fcbfe9b62af99b7dfaa37bef7495dc2dc19d908e97e95693bee0`. The current official one-pair observation `981b7fc2f6749d5c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `e9cf194d6f6d3f755b2d5d290f82f95bae004ed2`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 114,280 with 476 errors; Tank Royale scored 112,395 with zero errors. The single-pair score delta was −1.6%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. This matches the pinned-pool failures established in AN-015 and observed in AN-049 through AN-062. The earlier observation `584cd0f3ef575185` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not N or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain `DISCREPANCY (errors)` while Classic still reports the fixture errors. The −1.6% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/bayen.UbaMicro_1.4.jar`.

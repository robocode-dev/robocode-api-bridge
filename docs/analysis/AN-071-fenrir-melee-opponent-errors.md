---
id: AN-071
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-070]
title: Fenrir's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-071 — Fenrir's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/bvh.fnr.Fenrir_0.36l.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `986ed898d1fa2556d204321e33139543a61c6ddda15b419b877cc7009e91b5d9`. The current official one-pair observation `45eb4eef1a357641` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `e81e79641f968ffa2e78a41655d783edc4e0e028`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 114,157 with 680 errors; Tank Royale scored 112,102 with zero errors. The single-pair score delta was −1.8%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-070. The earlier observation `bf04da4787f958fa` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Fenrir or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain `DISCREPANCY (errors)` while Classic still reports the fixture errors. The −1.8% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.fry.Freya_0.82.jar`.

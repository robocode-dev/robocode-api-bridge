---
id: AN-079
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-078]
title: Furia Ceca's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-079 — Furia Ceca's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/caimano.Furia_Ceca_0.22.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `c1ce5bbf430fe9edc12da1768284be6f3d5bc35a9f48c84819f21edd32241616`. The current official one-pair observation `bc9800bb85670d2b` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `96efb046cbd54d39eb13bdceb2ae20e16b196a68`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 115,074 with 196 errors; Tank Royale scored 109,769 with zero errors. The single-pair score delta was −4.6%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-078. The earlier observation `ea8e5e13560f1498` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors. The registry already carries `melee-opponent-pool-contamination`, owner `harness`, for this subject.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Furia Ceca or a Tank Royale exception. Retain `DISCREPANCY (errors)` while Classic still reports fixture errors. The −4.6% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/cb.fire.Firestarter_2.0f.jar`.

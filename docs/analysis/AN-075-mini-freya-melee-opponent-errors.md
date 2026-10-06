---
id: AN-075
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-074]
title: Mini Freya's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-075 — Mini Freya's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/bvh.mini.Freya_0.55.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `4a3e45054c437df7ac073756d18ff8b6cf67ab4f9f686484a325afdd455c4452`. The current official one-pair observation `3d9bd321b6612bfa` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `aedfb9de02825ab56b55444d32d1b2be31a62701`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 113,397 with 464 errors; Tank Royale scored 111,181 with zero errors. The single-pair score delta was −2.0%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-074. The earlier observation `9d8c3fd37f10fca5` recorded 31 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Mini Freya or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain `DISCREPANCY (errors)` while Classic still reports the fixture errors. The −2.0% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/bwbaugh.nano.Tirunculus_0.0.0a.jar`.

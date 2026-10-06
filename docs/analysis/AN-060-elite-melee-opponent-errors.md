---
id: AN-060
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-059]
title: Elite's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-060 — Elite's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/awesomeness.Elite_1.0.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `4984536ad014022196926ae714ccd7df455cf37b4d5823a3b96dee3479a250b7`. The current official one-pair observation `c86a5acdcda5b3a4` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `5b766a899778c765c7b37f8654370b21b31571b6`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. Skipped-turn telemetry was captured. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 114,318 with 412 errors; Tank Royale scored 111,092 with zero errors. The single-pair score delta was −2.8%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some errors have no captured stack origin and remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-059. Tank Royale recorded no error signature in this current observation.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Elite or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain its `DISCREPANCY (errors)` status while Classic still reports the fixture errors. The −2.8% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/axeBots.HataMoto_3.09.jar`.

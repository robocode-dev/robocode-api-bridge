---
id: AN-058
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-057]
title: FrankTheTank's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-058 — FrankTheTank's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/as.FrankTheTank_1.3.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `fba4c679a952beddaf4248435b6432901e8111195b50b0201b16d4a0fcb7de38`. The current official one-pair observation `f0f45320bec4bfe4` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `8cdeac8f886b3b144bf1531ba86fccb95f9d466d`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. Skipped-turn telemetry was captured. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 115,080 with 562 errors; Tank Royale scored 113,683 with zero errors. The single-pair score delta was −1.2%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some errors have no captured stack origin and remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-057. The earlier observation `37024126101ec28f` also recorded the ChumbaMini stream error on Tank Royale; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not FrankTheTank or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain its `DISCREPANCY (errors)` status while Classic still reports the fixture errors. The −1.2% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/asd.Cthulhu_1.3.jar`.

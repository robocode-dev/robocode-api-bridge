---
id: AN-056
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-055]
title: NanoSatanMelee's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-056 — NanoSatanMelee's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/arthord.NanoSatanMelee_Beta.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `a0737bc6d25f6b89618c119a6df62ae89a44d9a2a7d662056dddefdde04f37fe`. The current official one-pair observation `d338a164bbc2e0a1` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `274031ddd6491bec5d3fb7221f539bc201185bbf`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. Skipped-turn telemetry was captured. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 114,440 with 92 errors; Tank Royale scored 111,829 with zero errors. The single-pair score delta was −2.3%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. Aristocles is in the selected `amk.ChumbaWumba_0.3.jar`; the ChumbaMini frame is in the selected `amk.ChumbaMini_0.2.jar`. Some errors have no captured stack origin and remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-055. The prior observation `9b446497f4946220` also recorded the ChumbaMini stream error on Tank Royale; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not NanoSatanMelee or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain its `DISCREPANCY (errors)` status while Classic still reports the fixture errors. The −2.3% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with the next unresolved `meleerumble/ary.Swarm_1.1.jar`.

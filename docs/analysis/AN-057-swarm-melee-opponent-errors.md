---
id: AN-057
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-056]
title: Swarm's current melee error comes from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-057 — Swarm's current melee error comes from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/ary.Swarm_1.1.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `4387f83a8810fbb3c99b684097c490a68c166d83d815cfe8f95f4415ce6cd45b`. The current official one-pair observation `4fca7d303ab293b5` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `9d7eedc767f08994c950a509361fc246fab3a3cd`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. Skipped-turn telemetry was captured. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 112,434 with 52 errors; Tank Royale scored 111,027 with zero errors. The single-pair score delta was −1.3%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException`; some errors have no captured stack origin and remain `unknown`. ChumbaMini is in the selected opponent set. This matches the pinned-pool failure established in AN-015 and observed in AN-049 through AN-056. The earlier observation `4df116958c48cf67` also recorded the ChumbaMini stream error on Tank Royale; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic error belongs to the fixed opponent pool, not Swarm or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain its `DISCREPANCY (errors)` status while Classic still reports the fixture error. The −1.3% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/as.FrankTheTank_1.3.jar`.

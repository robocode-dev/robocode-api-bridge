---
id: AN-081
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-079]
title: Insomnia's current score stays within band; Classic-only errors come from the pinned melee pool
provenance: inferred
reversal-cost: low
---

# AN-081 — Insomnia's current score stays within band; Classic-only errors come from the pinned melee pool

## Risk investigated

Whether Insomnia's current error discrepancy identifies the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `cb.nano.Insomnia_1.0.jar` has SHA-256 `dfaa0b7522185985faaaad2c8c8761b56ed6b2e31f9d256de0abbcf466863d30`. The current official one-pair observation `dc104bbcc0f75283` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `0f38b609c0151022835bfe672d58ba112a9fc10e`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the pinned opponent pool and locally built Tank Royale Bot API, runner, and wrapper artifacts; their hashes are recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Insomnia at official parameters. Classic scored 114,160 with 738 errors; Tank Royale scored 112,265 with zero errors. The single-pair score delta is −1.7%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named exceptions are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; the prior pool investigation identifies these classes with the selected ChumbaWumba and ChumbaMini fixtures (AN-015, AN-079). The registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

The earlier observation `abd63f20d915d381` used a different local bridge and Tank Royale pair and recorded 30 Tank Royale errors from `amk.ChumbaMini.saveData`. The current compatible local artifacts report zero Tank Royale errors, so that old exception does not reproduce in this pair.

## Finding

The current named Classic errors belong to the pinned opponent pool, not Insomnia or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The current score delta is within the review band and is not evidence of a confirmed score divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cf.RiO.RiOx_4.2.1.jar`.

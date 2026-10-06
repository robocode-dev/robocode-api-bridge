---
id: AN-093
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-092]
title: Wren's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-093 — Wren's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether Wren's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `cs.Wren_1.0.jar` has SHA-256 `89f30ea0d295bbc4516e9dad72426663fdf68707b2c733555e408c35679770ea`. The current official one-pair observation `d6682247ccce0d91` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `aefd4b53d5d95fb6b21784978f39bf63d95846db`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Wren at official parameters. Classic scored 113,381 with 102 errors; Tank Royale scored 112,764 with zero errors. The single-pair score delta is −0.5%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags Wren with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `59ba6dce50b10833` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 31 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not Wren or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cs.sheldor.Talon_1.1.jar`.

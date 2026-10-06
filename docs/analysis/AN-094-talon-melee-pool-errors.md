---
id: AN-094
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-093]
title: Talon's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-094 — Talon's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether Talon's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `cs.sheldor.Talon_1.1.jar` has SHA-256 `5309462a28f7ba9d36cf60aaf5b74efa130a9a4ba62fa426d281efb4f8b2bc92`. The current official one-pair observation `cf8014dc1274c8e8` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `82d2ef3857c94a11b3bb8605cd2e3a65759a75ff`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Talon at official parameters. Classic scored 113,496 with 1,048 errors; Tank Royale scored 110,362 with zero errors. The single-pair score delta is −2.8%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags Talon with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `05831324ef635cb1` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 30 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not Talon or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/css.Delitioner_0.11.jar`.

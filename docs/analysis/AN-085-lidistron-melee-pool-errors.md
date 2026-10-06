---
id: AN-085
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-084]
title: LidisTron's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-085 — LidisTron's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether LidisTron's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `co.edu.usb.rc.LidisTron_1.0.jar` has SHA-256 `1cb4a6827cfe40c553dd9d2464713ffef9f0bbe682cf0dd59e3b83c9d46a03e8`. The current official one-pair observation `15283ac581bc0f7c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `1b170a971d48abf5069daebcea653cdffad59270`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran LidisTron at official parameters. Classic scored 115,581 with 186 errors; Tank Royale scored 113,061 with zero errors. The single-pair score delta is −2.2%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `cf68d47889537d83` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 33 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not LidisTron or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.blogspot.malinkody.DestrobotMalin_1.0.jar`.

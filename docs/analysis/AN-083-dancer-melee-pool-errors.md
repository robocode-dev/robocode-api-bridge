---
id: AN-083
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-081, AN-082]
title: Dancer's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-083 — Dancer's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether Dancer's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `cli.Dancer_1.1.jar` has SHA-256 `6126e668dd994c5d3b47f50cad7e28313ad0b67ef1d5def7746846a35de8e07b`. The current official one-pair observation `16e4155095f28971` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `c07842b80896bef3f53fb9c80e2b2561b3abb261`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Dancer at official parameters. Classic scored 115,805 with 596 errors; Tank Royale scored 114,227 with zero errors. The single-pair score delta is −1.4%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags Dancer with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `6a47d423a68edb16` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 30 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not Dancer or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cli.WasteOfAmmo_1.0.jar`.

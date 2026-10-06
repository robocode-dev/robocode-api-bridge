---
id: AN-091
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-090]
title: Electron's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-091 — Electron's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether Electron's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `conscience.Electron_1.3g.jar` has SHA-256 `a8d12ab9530cd0f748593ac3d04cd2cf0b563713905457257c3395b7ab7aaf9b`. The current official one-pair observation `244529b8cc8da0fe` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `1a79a31c030acb546c343780a44ecb10c3df70d5`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Electron at official parameters. Classic scored 115,062 with 610 errors; Tank Royale scored 112,794 with zero errors. The single-pair score delta is −2.0%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `126d349d7c0fc8be` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 30 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not Electron or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cs.Grudge_1.0.jar`.

---
id: AN-089
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-088]
title: MostlyHarmless's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-089 — MostlyHarmless's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether MostlyHarmless's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `com.spp.robocode.MostlyHarmless_010.jar` has SHA-256 `79e3073e986fc6cdf59da91bb4f2b4c271770c7b31600fd2eafebb20bfca2710`. The current official one-pair observation `a8cc54533364c45f` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `6c4e17b69899a46ba83e0b692e40c946c312f35c`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran MostlyHarmless at official parameters. Classic scored 115,411 with 822 errors; Tank Royale scored 113,035 with zero errors. The single-pair score delta is −2.1%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `906407a51159bc9a` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 30 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not MostlyHarmless or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.syncleus.robocode.Dreadnaught_0.1.jar`.

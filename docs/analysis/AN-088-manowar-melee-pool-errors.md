---
id: AN-088
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-087]
title: ManOwaR's current Classic-only errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-088 — ManOwaR's current Classic-only errors belong to the pinned opponent pool

## Risk investigated

Whether ManOwaR's current error asymmetry belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `com.cohesiva.robocode.ManOwaR_1.0.jar` has SHA-256 `46519fab7d639e4d0a64afdd133618e92b7dcd923b8f252325098cc4a0e9031e`. The current official one-pair observation `da15e2667ea2cc20` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `e05af5e67822147c195241992e654bcd76465418`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran ManOwaR at official parameters. Classic scored 114,427 with 612 errors; Tank Royale scored 111,598 with zero errors. The single-pair score delta is −2.5%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `56d398157b8b2254` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it scored 114,384 in Classic and 111,412 in Tank Royale, with 54 and 30 errors respectively, all named as the shared ChumbaMini `SecurityException`. Its registry status was `PASS`. In the current run, Tank Royale has zero errors and Classic additionally reports the ChumbaWumba array error, so the current row is `DISCREPANCY (errors)` even though the score delta remains within the review band.

## Finding

The current Classic-only exceptions belong to the pinned opponent pool, not ManOwaR or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.spp.robocode.MostlyHarmless_010.jar`.

---
id: AN-087
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-086]
title: AdvancedTrackerII's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-087 — AdvancedTrackerII's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether AdvancedTrackerII's current asymmetric error count belongs to the subject, the bridge, or the fixed melee opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `com.cgarias.rc.AdvancedTrackerII_1.0.jar` has SHA-256 `a3da2e6251e317bbc767b8ac58ba3d37581a70a1597e0d2d10de8d961ade2ad3`. The current official one-pair observation `4c84d08e1bc4c3dc` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `0339e6edcdf38917ed249eeb3024288d9193bcc2`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran AdvancedTrackerII at official parameters. Classic scored 116,709 with 874 errors; Tank Royale scored 113,603 with zero errors. The single-pair score delta is −2.7%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies those classes with the selected ChumbaWumba and ChumbaMini fixtures. The registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

The older observation `110e8ce56bec5d9f` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 31 Tank Royale errors from `amk.ChumbaMini.saveData`. That fixture error does not reproduce with the current local artifacts.

## Finding

The current named Classic errors belong to the pinned opponent pool, not AdvancedTrackerII or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.cohesiva.robocode.ManOwaR_1.0.jar`.

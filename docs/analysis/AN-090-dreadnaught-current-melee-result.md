---
id: AN-090
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-089]
title: Dreadnaught's current pair scores; its old timeout and Tank Royale errors do not recur
provenance: inferred
reversal-cost: low
---

# AN-090 — Dreadnaught's current pair scores; its old timeout and Tank Royale errors do not recur

## Risk investigated

Whether Dreadnaught's prior timeout or opponent-origin Tank Royale errors still reproduce with the current compatible artifacts, and whether its current score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `com.syncleus.robocode.Dreadnaught_0.1.jar` has SHA-256 `4a2a9926375fca4cf32ea72bdc8ab43930223f2090bae428474eafb9a6dd823c`. The current official one-pair observation `e8f05311abdf7569` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `a6c8a4269aa2ad1dce3577f181235c466d3a63eb`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Dreadnaught at official parameters. Classic scored 116,796 with 776 errors; Tank Royale scored 115,492 with zero errors. The single-pair score delta is −1.1%, inside the 25% review threshold, so it does not confirm a score gap. Classic's named errors are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and `SecurityException` in `amk.ChumbaMini.saveData`, matching the pinned ChumbaWumba and ChumbaMini fixtures described in AN-015.

AN-015 records the original harness timeout, its bounded-process-supervision repair, and a later completed 35-round retest that still had 30 ChumbaMini errors on Tank Royale. The current pair has neither a timeout nor a Tank Royale error. The registry retains the historical `harness-timeout-overrun` diagnosis and the current `melee-opponent-pool-contamination` diagnosis.

## Finding

The previous timeout and Tank Royale fixture errors do not reproduce in the current pair. Classic still reports fixture-pool errors, so retain `DISCREPANCY (errors)` with `melee-opponent-pool-contamination` as the current cause. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/conscience.Electron_1.3g.jar`.

---
id: AN-277
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Spark's large positive score gap persists with one skipped-turn burst
provenance: inferred
reversal-cost: low
---

# AN-277 — Spark's large positive score gap persists with one skipped-turn burst

## Risk investigated

Whether `cx.micro.Spark_0.6.jar`'s large positive Tank Royale score gap persists with the latest matched artifacts, and whether skipped-turn telemetry remains empty.

## Evidence boundary

The read-only subject jar has SHA-256 `dfcbbb2bb08ae6028e68178c3d9f80c0b1d0058ced7ab90bab2cade92f615d18`. The official five-pair confirmation `fb18b8405b2642fd` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ad1b8b818512d466fdf419caf51c0887e86405d3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 4,824.6 points and Tank Royale averaged 12,770.8, for a +164.68% mean delta. Pair deltas were +169.7%, +164.4%, +172.5%, +160.0%, and +156.8%. Both engines completed without errors, and the registry status is `CONFIRMED (score)`.

Skipped-turn telemetry was captured for all five pairs. The first pair recorded 112 consecutive skipped turns for bot 1 across rounds 12 and 13; the remaining four pairs recorded no skipped turns. Earlier observations `4a48d9ed154a3163` and `33b8c433861acbd1`, and AN-148's 2026-10-06 confirmation, also showed large positive Tank Royale score gaps. AN-148's confirmation reported no skipped turns.

## Finding

Spark's large positive score gap persists under the latest matched artifacts, with an even larger mean delta than the previous five-pair confirmation. The first current pair also contains a substantial skipped-turn burst that did not recur in the next four pairs. This evidence confirms the score gap but does not establish its behavioral cause or the skipped-turn cause. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cx.mini.Nimrod_0.55.jar` (`score-review`).

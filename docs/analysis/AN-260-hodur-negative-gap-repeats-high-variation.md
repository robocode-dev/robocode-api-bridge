---
id: AN-260
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Hodur's negative score gap repeats with high variation
provenance: inferred
reversal-cost: low
---

# AN-260 — Hodur's negative score gap repeats with high variation

## Risk investigated

Whether `bvh.hdr.Hodur_0.4.jar`'s confirmed score gap persists under the latest matched artifacts and whether the mixed pair directions from AN-124 recur.

## Evidence boundary

The read-only subject jar has SHA-256 `2bd6a9804697badcfad118f8c84db756b33cd9154144da4b19166f9f7caea94e`. The official five-pair confirmation `adb2f9284090ba51` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `e5fab3e921b4a6f84b9c7043f52991443bdaeafe`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 1,849.8 in Classic and 914.2 in Tank Royale, a −42.8% mean delta. The five pair deltas were +15.5%, −63.9%, −62.7%, −74.6%, and −28.3%. Four pairs were negative. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-124's earlier confirmation had a −51.64% mean delta with one positive pair at +15.9%. The new confirmation again has one positive pair and four negative pairs; the mean remains beyond the 15-point confirmation band but is smaller in magnitude. Pair-to-pair variation remains substantial.

## Finding

Hodur retains a confirmed negative mean score gap under the latest matched artifacts, with high variation and one positive pair. No runtime errors or captured skipped turns occurred. The score cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/bvh.loki.Loki_0.5.jar` (`CONFIRMED (score)`).

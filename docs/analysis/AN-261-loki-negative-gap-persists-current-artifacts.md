---
id: AN-261
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Loki's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-261 — Loki's negative score gap persists with the latest artifacts

## Risk investigated

Whether `bvh.loki.Loki_0.5.jar`'s confirmed negative score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `46f15811b1cbdfa571eca677320bd0efca497cfb6ed6b672e82f8c27dfb3f0c6`. The official five-pair confirmation `a55ce96dfe4ea4be` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `e5fab3e921b4a6f84b9c7043f52991443bdaeafe`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 9,331.4 in Classic and 7,544.6 in Tank Royale, a −19.06% mean delta. The five pair deltas were −21.4%, −25.9%, −21.8%, −12.3%, and −13.9%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-125's earlier confirmation had a −16.66% mean delta, with all five pairs between −14.1% and −18.8%. The current result reproduces the negative direction with a somewhat larger mean difference; all five current pairs remain negative.

## Finding

Loki retains a confirmed negative score gap under the latest matched artifacts. Its current mean difference is slightly larger than in AN-125, and every pair favors Classic. No runtime errors or captured skipped turns occurred. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/bwbaugh.nano.Tirunculus_0.0.0a.jar` (`CONFIRMED (score)`).

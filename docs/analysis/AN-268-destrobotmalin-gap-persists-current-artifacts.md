---
id: AN-268
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DestrobotMalin's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-268 — DestrobotMalin's negative score gap persists with the latest artifacts

## Risk investigated

Whether `com.blogspot.malinkody.DestrobotMalin_1.0.jar`'s confirmed negative score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `d3205656d01007c800f5fa5549d247aa7030a40380277752ecd590e091339c27`. The official five-pair confirmation `93ffa72fc4b8df11` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 8,154 in Classic and 3,457.6 in Tank Royale, a −57.6% mean delta. The five pair deltas were −62.3%, −59.4%, −55.8%, −58.0%, and −52.5%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-136's earlier confirmation had a −55.02% mean delta. The latest run reproduces the same negative direction and similar magnitude under the newer matched artifacts.

## Finding

DestrobotMalin retains a large confirmed negative score gap under the latest matched artifacts. The current pairs are consistently negative, with no runtime errors or captured skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/com.sociesc.T1000_1.0.0.jar` (`CONFIRMED (score)`).

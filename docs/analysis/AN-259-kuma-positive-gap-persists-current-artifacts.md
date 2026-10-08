---
id: AN-259
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Kuma's positive score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-259 — Kuma's positive score gap persists with the latest artifacts

## Risk investigated

Whether `bp.Kuma_1.0.jar`'s confirmed positive score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `2646c78e817ac3701fbd23306259844fdea377b82ac7748f8d3cf6ea36b4825e`. The official five-pair confirmation `e042013c498f7fd6` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `e5fab3e921b4a6f84b9c7043f52991443bdaeafe`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 3,250 in Classic and 5,150.6 in Tank Royale, a +58.72% mean delta. The five pair deltas were +59.7%, +55.1%, +73.2%, +64.1%, and +41.5%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-122's earlier confirmation had a +53.12% mean delta. The latest run reproduces the positive direction and similar magnitude with the newer matched artifacts.

## Finding

Kuma retains a large confirmed positive score gap under the latest matched artifacts. The current pairs all favor Tank Royale, with no runtime errors or captured skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/bts.wiki.RipCurl_0.9b.jar`, whose current registry status is `PASS`. Continue in registry order with `roborumble/bvh.hdr.Hodur_0.4.jar` (`CONFIRMED (score)`).

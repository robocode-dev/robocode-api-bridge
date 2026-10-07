---
id: AN-253
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: UbaRamLT's score gap remains confirmed with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-253 — UbaRamLT's score gap remains confirmed with the latest artifacts

## Risk investigated

Whether `bayen.UbaRamLT_1.0.jar`'s confirmed score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `604d19bdcfa6caa05964df927bb24e237e8f7aaa1fcbd7d13aea4e4d9b5e028b`. The official confirmation `351ec7438a88bb7f` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `c01ce6d81756fa01f381215440f3fba0ea51b2e3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,886.2 in Classic and 13,866.8 in Tank Royale, a +27.4% mean delta. The five pair deltas were +28.7%, +26.9%, +26.4%, +27.0%, and +28.0%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

The previous official confirmation `f0b9374e244abc40` in AN-114 had a +19.88% mean delta under the earlier matched artifacts. The newer confirmation remains positive and exceeds both the registry's 25% single-pair review threshold and the harness's 15-point mean confirmation band.

## Finding

UbaRamLT's Tank Royale score advantage persists under the latest matched artifacts and is larger than in AN-114. The five current pair deltas are tightly grouped, with no runtime errors or captured skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/bayen.nut.Squirrel_1.621.jar` (`CONFIRMED (score)`).

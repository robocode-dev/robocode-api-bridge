---
id: AN-256
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Shooter's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-256 — Shooter's negative score gap persists with the latest artifacts

## Risk investigated

Whether `bk.Shooter_1.0.jar`'s confirmed negative score gap persists under the latest matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `0dfe57a84edd868ba0517b270c817bfdc9765139b6517fe4c7868110cf9c0e16`. The official confirmation `ac385a756b0f2146` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `c01ce6d81756fa01f381215440f3fba0ea51b2e3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 7,450.2 in Classic and 5,101.6 in Tank Royale, a −31.54% mean delta. The five pair deltas were −24.7%, −31.3%, −34.6%, −31.3%, and −35.8%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-118's prior confirmation had a −28.36% mean delta, with all five deltas between −24.1% and −34.4%. The latest result reproduces the same negative direction and similar magnitude with the newer matched artifacts.

## Finding

Shooter retains a consistent confirmed negative score gap under the latest matched artifacts. No runtime errors or captured skipped turns occurred. The cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/blir.nano.Cabbage_R1.0.1.jar` (`CONFIRMED (score)`).

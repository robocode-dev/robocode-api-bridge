---
id: AN-263
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Furia Ceca's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-263 — Furia Ceca's negative score gap persists with the latest artifacts

## Risk investigated

Whether `caimano.Furia_Ceca_0.22.jar`'s confirmed negative score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `c1ce5bbf430fe9edc12da1768284be6f3d5bc35a9f48c84819f21edd32241616`. The official five-pair confirmation `9f1e620ad662a53b` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 5,414.8 in Classic and 3,106.2 in Tank Royale, a −42.24% mean delta. The five pair deltas were −40.4%, −38.4%, −40.5%, −39.5%, and −52.4%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-127's earlier confirmation had a −43.38% mean delta, with all five deltas between −46.8% and −39.3%. The latest confirmation reproduces the same negative direction and similar magnitude with the newer matched artifacts.

## Finding

Furia Ceca retains a confirmed negative score gap under the latest matched artifacts. Four current pairs cluster near −40%, and one is more negative; no runtime errors or captured skipped turns occurred. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/cb.Domogled_1.2.jar`, whose current registry status is `PASS`. Continue with `roborumble/cb.fire.Firestarter_2.0f.jar` (`DISCREPANCY (outcome)`).

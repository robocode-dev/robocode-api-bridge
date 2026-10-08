---
id: AN-262
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Tirunculus's positive score gap grows with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-262 — Tirunculus's positive score gap grows with the latest artifacts

## Risk investigated

Whether `bwbaugh.nano.Tirunculus_0.0.0a.jar`'s confirmed positive score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `d1fcb6072a0110947df39ad0db284f0fa4f229e7ef0f0d2502553ca0b2acb7d4`. The official five-pair confirmation `7aa9ac29fe78b2f7` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,946.2 in Classic and 16,433 in Tank Royale, a +50.18% mean delta. The five pair deltas were +49.1%, +47.7%, +55.7%, +48.6%, and +49.8%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-126's earlier confirmation had a +24.28% mean delta, with pair deltas from +20.9% to +25.6%. The current gap is larger, but the positive direction remains consistent across all five pairs.

## Finding

Tirunculus retains a confirmed positive score gap under the latest matched artifacts, now at a larger mean magnitude than in AN-126. The current pairs are tightly grouped and report no runtime errors or skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/caimano.Furia_Ceca_0.22.jar` (`CONFIRMED (score)`).

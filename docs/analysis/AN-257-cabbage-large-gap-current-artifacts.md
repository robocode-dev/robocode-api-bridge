---
id: AN-257
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Cabbage's large positive score gap grows with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-257 — Cabbage's large positive score gap grows with the latest artifacts

## Risk investigated

Whether `blir.nano.Cabbage_R1.0.1.jar`'s large positive score gap persists under the latest matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `6048d93ca6c18440d2442930806a4edb783d6bc3e7b019f324fad7aebaccb222`. The official five-pair confirmation `3e06224b7006e720` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `e5fab3e921b4a6f84b9c7043f52991443bdaeafe`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 1,276 in Classic and 2,905.8 in Tank Royale, a +137.24% mean delta. The five pair deltas were +81.1%, +218.4%, +130.9%, +90.2%, and +165.6%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-119's earlier five-pair mean delta was +80.78%, with pair deltas between +62.1% and +101.6%. The current pairs all retain the positive direction, while the average gap and spread are larger. The underlying score differences remain unexplained.

## Finding

Cabbage retains a large confirmed Tank Royale score advantage under the latest matched artifacts. The result is consistently positive across all five pairs, with substantial variation in magnitude and no runtime errors or captured skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/boe.Minerva_0.80.jar`, whose current registry status is `PASS`. Continue in registry order with `roborumble/bons.NanoStalker_1.2.jar` (`CONFIRMED (score)`).

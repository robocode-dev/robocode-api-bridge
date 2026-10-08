---
id: AN-258
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: NanoStalker's large negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-258 — NanoStalker's large negative score gap persists with the latest artifacts

## Risk investigated

Whether `bons.NanoStalker_1.2.jar`'s confirmed negative score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `25169213aa301260dbe320aab5011618b95dd7283102fc6272cdd2e5afdc0936`. The official five-pair confirmation `0e40287063d26abe` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `e5fab3e921b4a6f84b9c7043f52991443bdaeafe`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 7,611.8 in Classic and 2,804.4 in Tank Royale, a −63.16% mean delta. The five pair deltas were −59.5%, −64.0%, −64.4%, −64.0%, and −63.9%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-121's earlier confirmation had a −71.98% mean delta. The latest result is smaller in magnitude but remains consistently negative, with all five current deltas within 4.9 percentage points of the mean.

## Finding

NanoStalker retains a large confirmed negative score gap under the latest matched artifacts. The gap is smaller than before but highly consistent across the five current pairs. No runtime errors or captured skipped turns occurred. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/bp.Kuma_1.0.jar` (`CONFIRMED (score)`).

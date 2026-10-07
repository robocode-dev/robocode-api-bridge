---
id: AN-254
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Squirrel's score gap remains confirmed but varies widely with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-254 — Squirrel's score gap remains confirmed but varies widely with the latest artifacts

## Risk investigated

Whether `bayen.nut.Squirrel_1.621.jar`'s current score gap remains confirmed with the latest matched artifacts, and whether the earlier outlier sensitivity persists.

## Evidence boundary

The read-only subject jar has SHA-256 `a5a1b82535752ee271c91cdec18f433e21dcfe1efc46ed4001bed116eeca1f76`. The official five-pair confirmation `913055fbe1b40f66` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `c01ce6d81756fa01f381215440f3fba0ea51b2e3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 5,088.6 in Classic and 3,726.4 in Tank Royale, a −25.9% mean delta. The five pair deltas were −62.3%, +2.1%, −13.5%, −0.2%, and −55.6%. The registry status is `CONFIRMED (score)`. No bridge-only signatures were recorded, and skipped-turn telemetry was unavailable in all five attempts.

AN-115's earlier five-pair confirmation had a −27.48% mean delta, with one −69.1% pair and four other deltas averaging −17.08%. In the current confirmation, two pairs have large negative deltas; the other three average −3.9%. The overall gap remains confirmed, but its magnitude depends on high-variance pairs. The missing telemetry limits diagnosis of the per-pair variation.

## Finding

Squirrel retains a confirmed negative score gap under the latest matched artifacts, with a −25.9% five-pair mean. The five results vary widely, and the three pairs outside the large negative runs average close to parity. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/bbo.RamboT_0.3.jar` (`CONFIRMED (score)`).

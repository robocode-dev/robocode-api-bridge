---
id: AN-255
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: RamboT's score gap remains confirmed without dependence on one pair
provenance: inferred
reversal-cost: low
---

# AN-255 — RamboT's score gap remains confirmed without dependence on one pair

## Risk investigated

Whether `bbo.RamboT_0.3.jar`'s score gap remains confirmed under the latest matched artifacts and whether one unusually high-delta pair still determines its classification.

## Evidence boundary

The read-only subject jar has SHA-256 `3c933f89750dfd882f03844f5fc83ae4bf36d5a6baca855251417b4a1457e6ed`. The official five-pair confirmation `a6c1692a3cac5b07` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `c01ce6d81756fa01f381215440f3fba0ea51b2e3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 9,402.2 in Classic and 12,696 in Tank Royale, a +35.7% mean delta. The five pair deltas were +33.9%, +28.9%, +24.4%, +57.7%, and +33.6%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-116's previous confirmation had a +20.74% mean delta, with its first four pairs averaging +13.58%; its fifth +49.4% pair was needed to cross the confirmation band. In this current confirmation, the four pairs excluding the largest delta average +30.2%. The positive gap therefore remains above the confirmation band without the single largest pair.

## Finding

RamboT retains a confirmed positive score gap under the latest matched artifacts. The score difference is larger than in AN-116, and the +57.7% pair no longer determines whether the mean clears the confirmation band. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/bing2.Melody_1.3.1.jar`, whose current status is `MATCHED (score noise)`. Continue with `roborumble/bk.Shooter_1.0.jar` (`CONFIRMED (score)`).

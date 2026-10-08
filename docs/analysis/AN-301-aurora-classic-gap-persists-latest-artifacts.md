---
id: AN-301
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Aurora's Classic score advantage persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-301 — Aurora's Classic score advantage persists with the latest artifacts

## Risk investigated

Whether `dmp.micro.Aurora_1.41.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `0550ff35cc6fd581a7469d3378ccf17ae5f3fb00888c56858ed3b54f515366e9`. The official five-pair confirmation `98f37e0587cbb865` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c9147546cb7d05628efc6cda11e32351bc4979e2`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 4,430.6 points and Tank Royale averaged 2,680.8, for a −39.3% mean delta. Pair deltas were −40.7%, −45.1%, −32.9%, −41.9%, and −35.9%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-175's prior five-pair confirmation averaged 4,457.2 in Classic and 2,806.6 in Tank Royale, a −37.02% mean delta. The current run reproduces the Classic advantage with a slightly larger mean magnitude.

## Finding

Aurora's Classic score advantage persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dmp.nano.Eve_3.41.jar` (`score-review`).

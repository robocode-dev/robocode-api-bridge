---
id: AN-272
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Suicidal's Classic zero score persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-272 — Suicidal's Classic zero score persists with the latest artifacts

## Risk investigated

Whether `conscience.Suicidal_1.1.jar`'s recurring Classic zero-score result persists under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `4650b8e8b5705e0fafb87fb4ffdcda77597f168c7c1c9272abae80691ad4f3d6`. The official observation `7bace3e897fc3ef4` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ad1b8b818512d466fdf419caf51c0887e86405d3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 0 and Tank Royale scored 2,080, with no errors on either engine. Tank Royale captured an empty skipped-turn event list. The registry status remains `DISCREPANCY (no score)`.

AN-140 also found a Classic score of 0 under the earlier matched artifacts, while Tank Royale scored around 2,100. The latest pair reproduces that result and does not clarify the cross-engine score difference.

## Finding

Suicidal's Classic zero-score result persists with the latest matched artifacts. Tank Royale continues to score around 2,000 without runtime errors or captured skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cre.Karolos_0.32.jar` (`DISCREPANCY (outcome)`).

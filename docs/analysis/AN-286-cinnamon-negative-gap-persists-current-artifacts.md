---
id: AN-286
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Cinnamon's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-286 — Cinnamon's negative score gap persists with the latest artifacts

## Risk investigated

Whether `dans.Cinnamon_1.2.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `cb352fa2412994d4160c5161124036464a48cc73b1725178a7ee048609c8b7da`. The official five-pair confirmation `7c103fa6dbb98693` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `70a5022e8292d1185c1a4cf15065869d55e282ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 8,435.6 points and Tank Royale averaged 4,385.2, for a −48.0% mean delta. Pair deltas were −41.7%, −50.2%, −47.6%, −53.7%, and −46.8%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-154's prior five-pair confirmation averaged 8,602.2 in Classic and 4,258.4 in Tank Royale, a −50.5% mean delta. The latest run reproduces the Classic advantage with a slightly smaller mean magnitude.

## Finding

Cinnamon's negative score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.Firebird_0.25.jar` (`score-review`).

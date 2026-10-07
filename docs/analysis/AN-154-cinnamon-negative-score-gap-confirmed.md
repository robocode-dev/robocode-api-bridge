---
id: AN-154
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Cinnamon's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-154 — Cinnamon's negative score gap is confirmed across five pairs

## Risk investigated

Whether Cinnamon's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dans.Cinnamon_1.2.jar` has SHA-256 `cb352fa2412994d4160c5161124036464a48cc73b1725178a7ee048609c8b7da`. The official confirmation `4f7ba23630a82b1a` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `0a99e0ac5279d113fc02bd6f7eb8d6f80993c120`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −51.7%, −50.8%, −48.5%, −49.7%, and −51.8%. Classic averaged 8,602.2 points and Tank Royale averaged 4,258.4, for a −50.5% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `4396cf2704a52d50` and `3ddba38f0713acc3` recorded −51.1% and −52.9% deltas with no runtime errors. They were single-pair observations and were not treated as five-pair confirmation.

## Finding

Cinnamon's negative score discrepancy is confirmed with current matched artifacts and agrees with both earlier observations. No runtime errors or skipped turns were observed. The score evidence does not identify the behavioral cause of the gap; source-level attribution was not attempted. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.Firebird_0.25.jar` (`score-review`).

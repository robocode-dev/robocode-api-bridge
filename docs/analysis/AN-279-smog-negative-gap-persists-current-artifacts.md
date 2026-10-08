---
id: AN-279
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Smog's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-279 — Smog's negative score gap persists with the latest artifacts

## Risk investigated

Whether `cx.nano.Smog_2.6.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `1bdea7a9d116c385e6d7dcd12014dc80270f619cf2b20046c5d85640aeda4bbf`. The official five-pair confirmation `aaa29eea6c1e1e43` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c0687e317ddc8775f40a7d8b4a88226cfedb2f6c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 4,080.2 points and Tank Royale averaged 2,669.2, for a −33.62% mean delta. Pair deltas were −46.8%, −21.8%, −37.0%, −25.4%, and −37.1%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-150's prior five-pair confirmation averaged 4,430.2 in Classic and 2,683 in Tank Royale, a −38.7% mean delta. The latest run reproduces the Classic advantage, with a smaller mean gap. Two of the current pair deltas are within the 25% per-pair threshold, while the five-pair mean exceeds the confirmation threshold.

## Finding

Smog's negative score gap persists under the latest matched artifacts, though its mean magnitude is smaller than in AN-150. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/da.NewBGank_1.4.jar` (`score-review`).

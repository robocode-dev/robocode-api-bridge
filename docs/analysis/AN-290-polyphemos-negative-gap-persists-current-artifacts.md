---
id: AN-290
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Polyphemos's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-290 — Polyphemos's negative score gap persists with the latest artifacts

## Risk investigated

Whether `de.erdega.robocode.Polyphemos_0.4.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `64765b6e60690f6889f1ef44ef0e8a566871eee38b280784e68900dff5d0e7be`. The official five-pair confirmation `05c8c9fcba4357a2` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `70a5022e8292d1185c1a4cf15065869d55e282ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,465.6 points and Tank Royale averaged 3,194.2, for a −41.54% mean delta. Pair deltas were −43.0%, −42.3%, −46.2%, −31.5%, and −44.7%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-161's prior five-pair confirmation averaged 5,454.6 in Classic and 3,324.8 in Tank Royale, a −39.14% mean delta. The current result reproduces the Classic advantage with a slightly larger mean magnitude.

## Finding

Polyphemos's negative score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Neutrino is `MATCHED (score noise)` in AN-162 and is skipped; continue with `roborumble/demetrix.nano.SledgeHammer_0.22.jar` (`score-review`).

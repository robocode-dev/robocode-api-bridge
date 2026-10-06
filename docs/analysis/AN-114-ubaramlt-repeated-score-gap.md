---
id: AN-114
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: UbaRamLT's repeated score gap clears the five-run confirmation band
provenance: inferred
reversal-cost: low
---

# AN-114 — UbaRamLT's repeated score gap clears the five-run confirmation band

## Risk investigated

Whether bayen.UbaRamLT's historical score discrepancy persists with the current matched artifacts and the official repeated-score confirmation.

## Evidence boundary

The read-only subject jar `bayen.UbaRamLT_1.0.jar` has SHA-256 `604d19bdcfa6caa05964df927bb24e237e8f7aaa1fcbd7d13aea4e4d9b5e028b`. The current official confirmation `f0b9374e244abc40` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `99f5d4bf685666828750a05b9b3bea3c8f2213a2`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,866.4 in Classic and 13,029.6 in Tank Royale, a +19.88% mean delta. The five pair deltas were +22.1%, +16.8%, +17.9%, +19.6%, and +23.0%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `13b1bb4843e9815b` and `34a30695ece53fb7` reported single-pair deltas of +29.8% and +34.5%, with no errors. They used older Tank Royale 1.2.0 artifacts. The current registry manifest's 25% threshold is used for the single-pair screen; the five-run confirmation uses the harness's 15-point regression band, so the +19.88% mean correctly classifies as `CONFIRMED (score)`.

## Finding

UbaRamLT has a repeatable current score difference: its five-run mean exceeds the confirmation band while remaining below the single-pair screening threshold. No runtime error or skipped turn was observed. The reason for the score gap remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bayen.nut.Squirrel_1.621.jar` (`score-review`).

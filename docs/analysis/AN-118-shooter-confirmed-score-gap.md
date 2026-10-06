---
id: AN-118
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Shooter's current score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-118 — Shooter's current score gap is confirmed across five runs

## Risk investigated

Whether bk.Shooter's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bk.Shooter_1.0.jar` has SHA-256 `0dfe57a84edd868ba0517b270c817bfdc9765139b6517fe4c7868110cf9c0e16`. The current official confirmation `6b24a095635bd032` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `5f888cf37a606f533fc793c8e737c790405d01a4`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 7,236 in Classic and 5,181.8 in Tank Royale, a −28.36% mean delta. The five pair deltas were −29.4%, −29.4%, −24.5%, −24.1%, and −34.4%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `17eb9124acd74e55` and `81e101672cb7f9c8` reported score deltas of −25.3% and −26.6%, also without errors. The current five-run confirmation reproduces the direction and similar magnitude of the gap.

## Finding

Shooter has a confirmed current score gap with no runtime errors or captured skipped turns. The cause of the difference remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/blir.nano.Cabbage_R1.0.1.jar` (`score-review`).

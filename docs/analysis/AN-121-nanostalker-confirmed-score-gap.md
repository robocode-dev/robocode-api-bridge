---
id: AN-121
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: NanoStalker's large negative score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-121 — NanoStalker's large negative score gap is confirmed across five runs

## Risk investigated

Whether bons.NanoStalker's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bons.NanoStalker_1.2.jar` has SHA-256 `25169213aa301260dbe320aab5011618b95dd7283102fc6272cdd2e5afdc0936`. The current official confirmation `5016c357e02add03` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `870bd9b8d2dcc4fda23236c1a1af6e8540a39e2c`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 7,728 in Classic and 2,161.4 in Tank Royale, a −71.98% mean delta. The five pair deltas were −73.3%, −72.2%, −74.5%, −71.3%, and −68.6%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `73559040c308ecc0` and `7fa967a48b2bdaa3` reported score deltas of −62.6% and −62.3%, also without errors. The current confirmation reproduces a similarly large negative score difference.

## Finding

NanoStalker has a large confirmed current score gap with no runtime errors or captured skipped turns. The reason for the difference remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bp.Kuma_1.0.jar` (`score-review`).

---
id: AN-127
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Furia Ceca's negative score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-127 — Furia Ceca's negative score gap is confirmed across five runs

## Risk investigated

Whether caimano.Furia_Ceca's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `caimano.Furia_Ceca_0.22.jar` has SHA-256 `c1ce5bbf430fe9edc12da1768284be6f3d5bc35a9f48c84819f21edd32241616`. The current official confirmation `02c8849c0009547c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `6b675f691d55548dfae7cdbeda3c60b31f8585e9`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 5,508.8 in Classic and 3,122.2 in Tank Royale, a −43.38% mean delta. The five pair deltas were −44.0%, −45.3%, −41.5%, −39.3%, and −46.8%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `99d4763f956857c8` and `4d5dd0574032c7bf` reported deltas of −45.5% and −47.3%, without errors. The current confirmation reproduces the direction and similar magnitude of the score difference.

## Finding

Furia Ceca has a stable confirmed current score gap with no runtime errors or captured skipped turns. The reason for the difference remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/cb.Domogled_1.2.jar` (`DISCREPANCY (outcome)`).

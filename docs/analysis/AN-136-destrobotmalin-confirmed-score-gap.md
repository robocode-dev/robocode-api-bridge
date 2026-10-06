---
id: AN-136
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DestrobotMalin's negative score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-136 — DestrobotMalin's negative score gap is confirmed across five runs

## Risk investigated

Whether com.blogspot.malinkody.DestrobotMalin's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `com.blogspot.malinkody.DestrobotMalin_1.0.jar` has SHA-256 `d3205656d01007c800f5fa5549d247aa7030a40380277752ecd590e091339c27`. The current official confirmation `ebabf39fe8cb6a4c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `fbff9bcb78214f8e8fe45f0a95ac2beecf466bcb`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 8,006.4 in Classic and 3,599 in Tank Royale, a −55.02% mean delta. The five pair deltas were −58.4%, −63.6%, −52.2%, −46.0%, and −54.9%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `41f11a8de1bd0c5b` and `5150f5d9febe75e6` reported deltas of −58.8% and −62.7%, without errors. The current confirmation reproduces the direction and similar magnitude of the gap.

## Finding

DestrobotMalin has a stable confirmed current score gap with no runtime errors or captured skipped turns. The reason for the gap remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/com.sociesc.T1000_1.0.0.jar` (`score-review`).

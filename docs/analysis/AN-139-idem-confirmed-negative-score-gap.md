---
id: AN-139
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Idem's negative score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-139 — Idem's negative score gap is confirmed across five runs

## Risk investigated

Whether conscience.Idem's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `conscience.Idem_1.0a.jar` has SHA-256 `1635ea6692d7c89d41d0e089fced55ac334f24f429380fb686590ca58cecfb2d`. The current official confirmation `1626e7b07d273141` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `c91e09ad323e998fd9320a164d1124af735bf073`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,699.8 in Classic and 5,614.6 in Tank Royale, a −47.28% mean delta. The five pair deltas were −45.5%, −40.9%, −43.7%, −56.5%, and −49.8%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `2c5f75820a136af0` and `eb8ec45277d96dfd` reported deltas of −19.8% and −35.1%, without errors. The current five-pair result reproduces a large negative gap consistently.

## Finding

Idem has a confirmed current score gap with no runtime errors or captured skipped turns. The difference is consistent across all five current pairs; its cause remains open. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/conscience.Suicidal_1.1.jar` (`DISCREPANCY (no score)`).

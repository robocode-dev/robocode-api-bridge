---
id: AN-125
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Loki's smaller current score gap still clears the confirmation band
provenance: inferred
reversal-cost: low
---

# AN-125 — Loki's smaller current score gap still clears the confirmation band

## Risk investigated

Whether bvh.loki.Loki's historical score difference persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bvh.loki.Loki_0.5.jar` has SHA-256 `46f15811b1cbdfa571eca677320bd0efca497cfb6ed6b672e82f8c27dfb3f0c6`. The current official confirmation `515864596d3c2c6e` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `52f5e818714952297bd6abfa1ba196c32e3a4ee3`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 9,060.2 in Classic and 7,548.6 in Tank Royale, a −16.66% mean delta. The five pair deltas were −14.1%, −17.2%, −16.5%, −18.8%, and −16.7%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)` because the mean gap exceeds the five-run 15-point confirmation band.

Earlier observations `c679e09780c18a52` and `baababf4d63efaf4` reported larger deltas of −47.3% and −49.8%, without errors. The current result has a smaller gap, but its five-run mean remains beyond the confirmation band.

## Finding

Loki retains a confirmed current score gap, substantially smaller than the earlier single-pair differences. The current pairs show a consistent negative direction and no runtime errors or captured skipped turns. The cause remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bwbaugh.nano.Tirunculus_0.0.0a.jar` (`score-review`).

---
id: AN-124
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Hodur's variable scores still yield a confirmed mean gap
provenance: inferred
reversal-cost: low
---

# AN-124 — Hodur's variable scores still yield a confirmed mean gap

## Risk investigated

Whether bvh.hdr.Hodur's historical score difference persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bvh.hdr.Hodur_0.4.jar` has SHA-256 `2bd6a9804697badcfad118f8c84db756b33cd9154144da4b19166f9f7caea94e`. The current official confirmation `a7ccc21ef530f064` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `67efb9bcb0a8bfc9db8bad92813876e683ea4b21`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 1,461 in Classic and 754 in Tank Royale, a −51.64% mean delta. The five pair deltas were −71.8%, −77.2%, +15.9%, −54.6%, and −70.5%. Four pairs had a negative delta, while the third pair reversed direction. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `bc89209e78a077fc` and `8a25b46b587e2378` reported deltas of −37.2% and −32.3%, without errors. The current mean reproduces the negative score direction, though the per-pair values vary substantially.

## Finding

Hodur has a confirmed current score gap with no runtime errors or captured skipped turns. One of five pairs reversed direction, but the other four and the overall mean remain below Classic. The score cause remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bvh.loki.Loki_0.5.jar` (`score-review`).

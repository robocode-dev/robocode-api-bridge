---
id: AN-169
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: gpBot's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-169 — gpBot's negative score gap is confirmed across five pairs

## Risk investigated

Whether gpBot's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dggp.haiku.gpBot_0_1.1.jar` has SHA-256 `a19725573c6849e5f85f63fecf569745978d77dbefc73280f000c141a695e619`. The official confirmation `e9099642a06d7564` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `04fd19ece84da3e2c9104e01321626851fbeceb7`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −54.1%, −53.5%, −53.8%, −46.5%, and −53.8%. Classic averaged 5,179.2 points and Tank Royale averaged 2,464.8, for a −52.34% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `8c9d984fdf0efc98` recorded a −55.8% single-pair delta without runtime errors. The current five-pair mean is consistent with it.

## Finding

gpBot's Classic score advantage is confirmed with current matched artifacts and agrees with its earlier observation. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dittman.BlindSquirl_Retired.jar` (`score-review`).

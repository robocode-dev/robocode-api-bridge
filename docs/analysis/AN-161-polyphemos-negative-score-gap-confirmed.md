---
id: AN-161
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Polyphemos's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-161 — Polyphemos's negative score gap is confirmed across five pairs

## Risk investigated

Whether Polyphemos's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `de.erdega.robocode.Polyphemos_0.4.jar` has SHA-256 `64765b6e60690f6889f1ef44ef0e8a566871eee38b280784e68900dff5d0e7be`. The official confirmation `f39aa7cda8ecb379` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `05d56b3125337d9034fed3533ba76fa956fe91d1`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −44.6%, −40.2%, −45.4%, −34.6%, and −30.9%. Classic averaged 5,454.6 points and Tank Royale averaged 3,324.8, for a −39.14% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `2b4de671d6c6e4bf` and `4b2a42fea45ce2a9` recorded −35.0% and −39.0% single-pair deltas. The current five-pair mean is consistent with both earlier observations.

## Finding

Polyphemos's Classic score advantage is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. The score difference's behavioral cause remains unassigned. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/demetrix.nano.Neutrino_0.27.jar` (`score-review`).

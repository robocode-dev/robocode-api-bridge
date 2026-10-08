---
id: AN-305
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Muncho's Classic score advantage persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-305 — Muncho's Classic score advantage persists with the latest artifacts

## Risk investigated

Whether `donjezza.Muncho_1.0.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `ca1863e988017d98a771812f983352f9b22daf33a195301f05d410b2fa26673d`. The official five-pair confirmation `511dd2d96dbfe90f` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `49248a488cd62fcce5233fd0b0e3a32d4c218182`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 6,744.0 points and Tank Royale averaged 5,274.0, for a −21.82% mean delta. Pair deltas were −22.9%, −22.1%, −22.1%, −20.8%, and −21.2%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-179's prior five-pair confirmation averaged 6,753.4 in Classic and 5,278.4 in Tank Royale, also a −21.82% mean delta. The current result reproduces the same score gap. Unlike AN-179, all five current telemetry captures were complete and empty.

## Finding

Muncho's Classic score advantage persists under the latest matched artifacts with the same mean magnitude as AN-179. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dragonbyte.Neutrino_4.jar` (`score-review`).

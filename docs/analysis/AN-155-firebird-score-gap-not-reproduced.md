---
id: AN-155
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Firebird's historical score gap is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-155 — Firebird's historical score gap is not reproduced

## Risk investigated

Whether Firebird's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `davidalves.Firebird_0.25.jar` has SHA-256 `ecf5e584ad4d70023baacc9bd4b2ce6c65520675c91f542e21951e20be1243c9`. The official confirmation `d6851b81019c356f` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `13a7f02d69178699d1281b94d6e7041e55d57bb5`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −10.5%, −21.4%, −16.4%, −12.8%, and −13.3%. Classic averaged 5,203.4 points and Tank Royale averaged 4,428.0, for a −14.88% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `MATCHED (score noise)`; the mean is just below the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

Earlier observations `fae39438b7bb4d8a` and `fbca028f7a468788` recorded +16.1% and +28.0% single-pair deltas without runtime errors. The earlier +28.0% result was not treated as a five-pair confirmation; the official confirmation protocol was used to assess whether it persisted.

## Finding

Firebird's earlier Tank Royale score advantage is not reproduced in the current five-pair mean, which instead shows a −14.88% difference within the score-noise band. No runtime errors or skipped turns were observed. The measurements do not identify the cause of the older result. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.PhoenixOS_1.1.jar` (`DISCREPANCY (outcome)`).

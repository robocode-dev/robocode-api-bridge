---
id: AN-359
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Dancer's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-359 — Dancer's prior melee errors clear in five current pairs

## Risk investigated

Whether `cli.Dancer_1.1.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `6126e668dd994c5d3b47f50cad7e28313ad0b67ef1d5def7746846a35de8e07b`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `59a556b7a5118a1e` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `a1c16d67b05995899c2ef033a6495b365f0361ad`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,533.6 points and Tank Royale averaged 113,354.2 points, for a −1.88% mean delta. Pair deltas were −1.9%, −1.9%, −2.0%, −2.6%, and −1.0%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `02d8559d6d924209` recorded 516 Classic errors and 30 Tank Royale errors, with a −1.5% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 5, 8, 0, 2, and 0. The registry records bot IDs, but this finding does not attribute those events to Dancer.

## Finding

Dancer remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cli.WasteOfAmmo_1.0.jar` (`DISCREPANCY (errors)`).

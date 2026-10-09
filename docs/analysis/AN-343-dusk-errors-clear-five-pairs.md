---
id: AN-343
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Dusk's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-343 — Dusk's prior melee errors clear in five current pairs

## Risk investigated

Whether `brainfade.melee.Dusk_0.44.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `a9ebabb60c05453723be3680d718a93a6b212ecf13d3fc408b4e2ab88f5d853b`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `7344ea01a3f1e60a` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d02611ff9f6d1c39c98f193dfefc8e4de9636fef`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,249.8 points and Tank Royale averaged 111,191.6 points, for a −2.66% mean delta. Pair deltas were −3.1%, −3.7%, −1.8%, −2.5%, and −2.2%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `b8bf42475f521679` recorded 632 Classic errors and 30 Tank Royale errors, with a −1.7% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 0, 0, 10, and 3. The registry records bot IDs, but this finding does not attribute those events to Dusk.

## Finding

Dusk remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/brainfade.melee.Genghis_0.36.jar` (`DISCREPANCY (errors)`).

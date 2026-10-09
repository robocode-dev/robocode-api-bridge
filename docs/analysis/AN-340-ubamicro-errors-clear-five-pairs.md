---
id: AN-340
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: UbaMicro's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-340 — UbaMicro's prior melee errors clear in five current pairs

## Risk investigated

Whether `bayen.UbaMicro_1.4.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `7691bd56732d5813079f65319f62a7bda392937bfe458528383fd2b89b1a7d11`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `b36c8d4f0b324a67` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d02611ff9f6d1c39c98f193dfefc8e4de9636fef`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,705.2 points and Tank Royale averaged 111,669.4 points, for a −2.64% mean delta. Pair deltas were −3.5%, −2.1%, −2.5%, −1.9%, and −3.2%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `441885d123c75ae0` recorded 544 Classic errors and 31 Tank Royale errors, with a −3.0% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 10, 3, 2, 7, and 0. The registry records bot IDs, but this finding does not attribute those events to UbaMicro.

## Finding

UbaMicro remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bayen.nut.Squirrel_1.615.jar` (`DISCREPANCY (errors)`).

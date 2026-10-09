---
id: AN-352
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Tirunculus's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-352 — Tirunculus's prior melee errors clear in five current pairs

## Risk investigated

Whether `bwbaugh.nano.Tirunculus_0.0.0a.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `d1fcb6072a0110947df39ad0db284f0fa4f229e7ef0f0d2502553ca0b2acb7d4`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `45597d1954330abb` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ade8306f4bf43cc52948f00bcb594a12169a7221`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 117,377.2 points and Tank Royale averaged 116,470.2 points, for a −0.78% mean delta. Pair deltas were −0.5%, −1.5%, +0.4%, −1.5%, and −0.8%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `597475e5ecd84fba` recorded 352 Classic errors and 30 Tank Royale errors, with a −1.0% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 5, 3, 2, and 0. The registry records bot IDs, but this finding does not attribute those events to Tirunculus.

## Finding

Tirunculus remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bzdp.BoxCar_2.0.jar` (`DISCREPANCY (errors)`).

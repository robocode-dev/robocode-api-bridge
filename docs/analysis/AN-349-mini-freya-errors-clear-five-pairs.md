---
id: AN-349
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Mini Freya's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-349 — Mini Freya's prior melee errors clear in five current pairs

## Risk investigated

Whether `bvh.micro.Freya_0.3.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `97868a0fa8ea022df4e19ae810f3a098da8c0d897ebd4564c1afe2b9f9002890`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `64f4bce30a1379d3` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ade8306f4bf43cc52948f00bcb594a12169a7221`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 113,578.8 points and Tank Royale averaged 111,724.4 points, for a −1.64% mean delta. Pair deltas were −1.8%, −1.3%, −1.6%, −1.8%, and −1.7%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `207aa1f649e4192c` recorded 318 Classic errors and 30 Tank Royale errors, with a −2.7% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 6, 14, 30, 0, and 9. The registry records bot IDs, but this finding does not attribute those events to Mini Freya.

## Finding

Mini Freya remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.mini.Fenrir_0.39.jar` (`DISCREPANCY (errors)`).

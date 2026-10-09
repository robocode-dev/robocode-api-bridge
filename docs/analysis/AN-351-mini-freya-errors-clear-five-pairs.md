---
id: AN-351
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Mini Freya's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-351 — Mini Freya's prior melee errors clear in five current pairs

## Risk investigated

Whether `bvh.mini.Freya_0.55.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `4a3e45054c437df7ac073756d18ff8b6cf67ab4f9f686484a325afdd455c4452`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `dcd82ace10b4580a` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ade8306f4bf43cc52948f00bcb594a12169a7221`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 113,665.4 points and Tank Royale averaged 110,930.0 points, for a −2.42% mean delta. Pair deltas were −2.7%, −2.0%, −2.9%, −2.6%, and −1.9%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `b289d4813ca36d4f` recorded 134 Classic errors and 30 Tank Royale errors, with a −2.8% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 4, 5, 10, 0, and 4. The registry records bot IDs, but this finding does not attribute those events to Mini Freya.

## Finding

Mini Freya remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bwbaugh.nano.Tirunculus_0.0.0a.jar` (`DISCREPANCY (errors)`).

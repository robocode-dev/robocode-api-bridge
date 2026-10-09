---
id: AN-348
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Freya's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-348 — Freya's prior melee errors clear in five current pairs

## Risk investigated

Whether `bvh.fry.Freya_0.82.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `2618ef3fb7cd8cb814b33be2531dc1e81661554362f93b29a80d1cce31f12fe5`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `bb37d5929e1cd5b3` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `546d7c330d13999567a07197916d7d8e199a153b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,065.0 points and Tank Royale averaged 111,554.0 points, for a −2.18% mean delta. Pair deltas were −2.7%, −2.9%, −1.8%, −1.6%, and −1.9%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `894e5b2bebf1e1bd` recorded 162 Classic errors and 30 Tank Royale errors, with a −2.0% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 0, 1, 3, and 4. The registry records bot IDs, but this finding does not attribute those events to Freya.

## Finding

Freya remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.micro.Freya_0.3.jar` (`DISCREPANCY (errors)`).

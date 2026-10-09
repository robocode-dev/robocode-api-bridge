---
id: AN-354
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Pansy's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-354 — Pansy's prior melee errors clear in five current pairs

## Risk investigated

Whether `bzdp.Pansy_2.1.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `71057e19022678ba19a873854afb4bd32a986ae77c5105c79cb549ab664ff939`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `1134bd5bc0cd2e7d` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `17ea6230758302718d6dd4b2c996214759d1205d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 113,588.0 points and Tank Royale averaged 109,048.6 points, for a −4.0% mean delta. Pair deltas were −4.5%, −3.6%, −4.4%, −4.5%, and −3.0%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `37caaf4769af3ed8` recorded 68 Classic errors and 30 Tank Royale errors, with a −4.1% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 3, 7, 5, 3, and 2. The registry records bot IDs, but this finding does not attribute those events to Pansy.

## Finding

Pansy remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/caimano.Furia_Ceca_0.22.jar` (`DISCREPANCY (errors)`).

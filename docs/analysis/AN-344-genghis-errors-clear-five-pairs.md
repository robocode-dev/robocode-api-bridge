---
id: AN-344
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Genghis's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-344 — Genghis's prior melee errors clear in five current pairs

## Risk investigated

Whether `brainfade.melee.Genghis_0.36.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `c23535c5ec25af4f0813bb8b56ffb7749228dd5af642aa5e9087d546681b718c`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `3a1ed3c2d1294556` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `546d7c330d13999567a07197916d7d8e199a153b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 113,670.0 points and Tank Royale averaged 110,897.4 points, for a −2.44% mean delta. Pair deltas were −2.8%, −2.4%, −1.9%, −1.4%, and −3.7%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `9c60c070e27ceb74` recorded 386 Classic errors and 30 Tank Royale errors, with a −2.4% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 2, 2, 7, 0, and 0. The registry records bot IDs, but this finding does not attribute those events to Genghis.

## Finding

Genghis remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bts.mega.Gnarly_1.4.jar` (`DISCREPANCY (errors)`).

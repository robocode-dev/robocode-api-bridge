---
id: AN-338
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ololobot's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-338 — Ololobot's prior melee errors clear in five current pairs

## Risk investigated

Whether `az.Ololobot_0.2.4.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `b6938411c048bde80bf0186892f03342e0c60dff05127052ed21932825765eb9`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `a7284c2a771993ef` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `23fc732088cb0a23d373c7e20a7f8f22357c6d48`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,001.8 points and Tank Royale averaged 111,962.0 points, for a −1.8% mean delta. Pair deltas were −2.1%, −2.6%, −1.1%, −1.3%, and −1.9%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `717e46e27dcdeb9a` recorded 378 Classic errors and 30 Tank Royale errors, with a −2.6% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all five pairs, with event counts 3, 5, 0, 4, and 0. The registry records bot IDs, but this finding does not attribute those events to Ololobot.

## Finding

Ololobot remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previously diagnosed opponent-pool contamination remains historical context; this run does not establish that the subject caused those earlier errors. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/baal.nano.N_1.42.jar` (`DISCREPANCY (errors)`).

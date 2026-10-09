---
id: AN-363
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: AdvancedTracker II's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-363 — AdvancedTracker II's prior melee errors clear in five current pairs

## Risk investigated

Whether `com.cgarias.rc.AdvancedTrackerII_1.0.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `a3da2e6251e317bbc767b8ac58ba3d37581a70a1597e0d2d10de8d961ade2ad3`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `5776133d1aaf465e` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `a1c16d67b05995899c2ef033a6495b365f0361ad`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 116,391.0 points and Tank Royale averaged 113,116.2 points, for a −2.8% mean delta. Pair deltas were −2.4%, −3.8%, −3.0%, −3.1%, and −1.7%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `9f844c4af2979bcd` recorded 818 Classic errors and 31 Tank Royale errors, with a −3.8% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 2, 5, 1, 0, and 0. The registry records bot IDs, but this finding does not attribute those events to AdvancedTracker II.

## Finding

AdvancedTracker II remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.cohesiva.robocode.ManOwaR_1.0.jar` (`DISCREPANCY (errors)`).

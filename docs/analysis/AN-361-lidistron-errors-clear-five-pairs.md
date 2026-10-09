---
id: AN-361
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: LidisTron's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-361 — LidisTron's prior melee errors clear in five current pairs

## Risk investigated

Whether `co.edu.usb.rc.LidisTron_1.0.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `1cb4a6827cfe40c553dd9d2464713ffef9f0bbe682cf0dd59e3b83c9d46a03e8`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `65d164c9474fbed3` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `a1c16d67b05995899c2ef033a6495b365f0361ad`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,180.8 points and Tank Royale averaged 112,284.8 points, for a −2.5% mean delta. Pair deltas were −2.3%, −2.3%, −3.4%, −2.4%, and −2.1%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `87184c23266d1ad2` recorded 404 Classic errors and 30 Tank Royale errors, with a −2.2% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 5, 1, 0, and 8. The registry records bot IDs, but this finding does not attribute those events to LidisTron.

## Finding

LidisTron remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.blogspot.malinkody.DestrobotMalin_1.0.jar` (`DISCREPANCY (errors)`).

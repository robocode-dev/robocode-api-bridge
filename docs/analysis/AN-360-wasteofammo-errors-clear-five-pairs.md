---
id: AN-360
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: WasteOfAmmo's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-360 — WasteOfAmmo's prior melee errors clear in five current pairs

## Risk investigated

Whether `cli.WasteOfAmmo_1.0.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `c899395217eb5b9a8c9bdcd3e8869616ec23576e787e4ce3e2c7c3263695b36f`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `ac04f3df48585a60` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `a1c16d67b05995899c2ef033a6495b365f0361ad`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,655.4 points and Tank Royale averaged 112,241.8 points, for a −2.94% mean delta. Pair deltas were −2.7%, −2.7%, −2.1%, −3.6%, and −3.6%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `04907df036f1704f` recorded 518 Classic errors and 30 Tank Royale errors, with a −3.0% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 3, 0, 0, 8, and 4. The registry records bot IDs, but this finding does not attribute those events to WasteOfAmmo.

## Finding

WasteOfAmmo remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/co.edu.usb.rc.LidisTron_1.0.jar` (`DISCREPANCY (errors)`).

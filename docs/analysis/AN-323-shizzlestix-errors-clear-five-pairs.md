---
id: AN-323
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ShizzleStiX's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-323 — ShizzleStiX's prior melee errors clear in five current pairs

## Risk investigated

Whether `amk.ShizzleStiX.ShizzleStiX_0.6.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `5c8796a2eec65cf0` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `f056b2427aa3b9ef0b1aab1f4d5c2abfee3482da`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,734.4 points and Tank Royale averaged 112,983.2, for a −1.52% mean delta. Pair deltas were −1.5%, −2.0%, −0.9%, −0.8%, and −2.4%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `31b7de98365662fb` recorded 478 Classic errors and 30 Tank Royale errors. That imbalance did not recur when ShizzleStiX was the subject in the current five-pair run. Its known `ConcurrentModificationException` was observed in AN-316 while ShizzleStiX was a selected opponent; it did not recur here. Skipped-turn telemetry was captured in all pairs, with event counts 0, 10, 5, 1, and 4; the registry records bot IDs but this finding does not attribute those events to ShizzleStiX.

## Finding

ShizzleStiX remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The opponent iterator exception from AN-316 also did not recur when ShizzleStiX was the measured subject. The skipped-turn records are preserved in the registry and are not attributed to ShizzleStiX by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amk.jointstrike.JointStrike_0.2.jar` (`DISCREPANCY (errors)`).

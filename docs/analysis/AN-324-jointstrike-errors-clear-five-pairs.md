---
id: AN-324
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: JointStrike's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-324 — JointStrike's prior melee errors clear in five current pairs

## Risk investigated

Whether `amk.jointstrike.JointStrike_0.2.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `a6471a48671d9cbc` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `f056b2427aa3b9ef0b1aab1f4d5c2abfee3482da`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,554.4 points and Tank Royale averaged 112,782.2, for a −1.52% mean delta. Pair deltas were −2.0%, −1.1%, −1.9%, −1.3%, and −1.3%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `8cf06c550a92e912` recorded 668 Classic errors and 30 Tank Royale errors, including ChumbaMini's data-file failure and Classic's `amk.guns.Aristocles.prepare` signature. The current five-pair run did not reproduce those errors. Skipped-turn telemetry was captured in all five pairs, with event counts 4, 1, 1, 1, and 0; the registry records bot IDs but this finding does not attribute the events to JointStrike.

## Finding

JointStrike remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to JointStrike by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amk.superstrike.SuperStrike_0.3.jar` (`DISCREPANCY (errors)`).

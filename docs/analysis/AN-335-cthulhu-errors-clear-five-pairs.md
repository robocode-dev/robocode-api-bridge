---
id: AN-335
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Cthulhu's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-335 — Cthulhu's prior melee errors clear in five current pairs

## Risk investigated

Whether `asd.Cthulhu_1.3.jar`'s historical error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `f316ea1aa53c7c67` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `23fc732088cb0a23d373c7e20a7f8f22357c6d48`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,153.4 points and Tank Royale averaged 112,148.0, for a −1.74% mean delta. Pair deltas were −2.2%, −3.6%, −0.9%, −0.1%, and −1.9%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `da0129bb6e373bf6` recorded 499 Classic errors and 66 Tank Royale errors, with the registry diagnosis `subject-owned-data-file-append-quota`. The current five-pair run did not reproduce that error discrepancy. Skipped-turn telemetry was captured in all pairs, with event counts 9, 0, 5, 4, and 0; the registry records bot IDs but this finding does not attribute the events to Cthulhu.

## Finding

Cthulhu remains within the score-noise band, and its prior file-quota and other melee errors did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Cthulhu by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/awesomeness.Elite_1.0.jar` (`DISCREPANCY (errors)`).

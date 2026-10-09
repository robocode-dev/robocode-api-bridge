---
id: AN-334
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: FrankTheTank's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-334 — FrankTheTank's prior melee errors clear in five current pairs

## Risk investigated

Whether `as.FrankTheTank_1.3.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `511042837f98bc62` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `23fc732088cb0a23d373c7e20a7f8f22357c6d48`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,469.0 points and Tank Royale averaged 112,352.8, for a −2.68% mean delta. Pair deltas were −2.7%, −2.5%, −3.7%, −1.9%, and −2.6%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `48ea9b2a04e896bb` recorded 774 Classic errors and 31 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 4, 0, 5, 4, and 1; the registry records bot IDs but this finding does not attribute the events to FrankTheTank.

## Finding

FrankTheTank remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to FrankTheTank by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/asd.Cthulhu_1.3.jar` (`DISCREPANCY (errors)`).

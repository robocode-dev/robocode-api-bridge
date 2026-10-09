---
id: AN-333
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Swarm's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-333 — Swarm's prior melee errors clear in five current pairs

## Risk investigated

Whether `ary.Swarm_1.1.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `0ff7f73cb9664da9` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `23fc732088cb0a23d373c7e20a7f8f22357c6d48`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 113,557.8 points and Tank Royale averaged 111,447.0, for a −1.82% mean delta. Pair deltas were −2.2%, −1.7%, −1.7%, −1.7%, and −1.8%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `2753872969b52c2b` recorded 686 Classic errors and 31 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all five pairs, with event counts 0, 0, 0, 0, and 6; the registry records bot IDs but this finding does not attribute the events to Swarm.

## Finding

Swarm remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Swarm by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/as.FrankTheTank_1.3.jar` (`DISCREPANCY (errors)`).

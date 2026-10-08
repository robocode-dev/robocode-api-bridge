---
id: AN-332
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: NanoSatanMelee's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-332 — NanoSatanMelee's prior melee errors clear in five current pairs

## Risk investigated

Whether `arthord.NanoSatanMelee_Beta.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `521a2cbd739b829f` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `773d1c97e17d3018b4f6816af475e6ba403bd232`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,018.6 points and Tank Royale averaged 111,971.6, for a −1.78% mean delta. Pair deltas were −1.9%, −2.0%, −1.5%, −2.3%, and −1.2%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `d923bb9c0f620792` recorded 374 Classic errors and 30 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 2, 0, 0, and 3; the registry records bot IDs but this finding does not attribute the events to NanoSatanMelee.

## Finding

NanoSatanMelee remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to NanoSatanMelee by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ary.Swarm_1.1.jar` (`DISCREPANCY (errors)`).

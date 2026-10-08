---
id: AN-328
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: BotM's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-328 — BotM's prior melee errors clear in five current pairs

## Risk investigated

Whether `apc.botM_3.0.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `a36c35147e6d1b4d` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `773d1c97e17d3018b4f6816af475e6ba403bd232`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,198.2 points and Tank Royale averaged 113,602.2, for a −0.50% mean delta. Pair deltas were −1.1%, +0.1%, −1.3%, +0.4%, and −0.6%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `87cb1a1013fc8287` recorded 182 Classic errors and 30 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all five pairs, with event counts 0, 0, 5, 5, and 5; the registry records bot IDs but this finding does not attribute the events to BotM.

## Finding

BotM remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to BotM by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/apollokidd.ApolloKidd_0.9.jar` (`DISCREPANCY (errors)`).

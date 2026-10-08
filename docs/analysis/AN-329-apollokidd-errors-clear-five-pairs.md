---
id: AN-329
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ApolloKidd's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-329 — ApolloKidd's prior melee errors clear in five current pairs

## Risk investigated

Whether `apollokidd.ApolloKidd_0.9.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `0c4d46544df00193` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `773d1c97e17d3018b4f6816af475e6ba403bd232`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 116,554.8 points and Tank Royale averaged 113,876.2, for a −2.30% mean delta. Pair deltas were −2.4%, −1.6%, −3.5%, −2.5%, and −1.5%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `f0b24f00b563a266` recorded 398 Classic errors and 30 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all five pairs, with event counts 0, 3, 0, 0, and 0; the registry records bot IDs but this finding does not attribute the events to ApolloKidd.

## Finding

ApolloKidd remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to ApolloKidd by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ara.Shera_0.88.jar` (`DISCREPANCY (errors)`).

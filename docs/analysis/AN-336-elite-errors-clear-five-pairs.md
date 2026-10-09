---
id: AN-336
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Elite's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-336 — Elite's prior melee errors clear in five current pairs

## Risk investigated

Whether `awesomeness.Elite_1.0.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `40ff0783679eadb4` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `23fc732088cb0a23d373c7e20a7f8f22357c6d48`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,870.0 points and Tank Royale averaged 111,350.4, for a −3.08% mean delta. Pair deltas were −2.7%, −4.2%, −3.8%, −2.1%, and −2.6%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `2170bf23718a05bc` recorded 584 Classic errors and 30 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 2, 4, 5, 0, and 3; the registry records bot IDs but this finding does not attribute the events to Elite.

## Finding

Elite remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Elite by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/axeBots.HataMoto_3.09.jar` (`DISCREPANCY (errors)`).

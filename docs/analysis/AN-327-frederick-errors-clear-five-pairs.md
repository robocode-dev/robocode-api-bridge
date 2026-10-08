---
id: AN-327
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Frederick's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-327 — Frederick's prior melee errors clear in five current pairs

## Risk investigated

Whether `ap.Frederick_1.1.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `b51bbd49bb3260fb` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `773d1c97e17d3018b4f6816af475e6ba403bd232`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,161.6 points and Tank Royale averaged 112,965.8, for a −1.04% mean delta. Pair deltas were −1.5%, −0.2%, −0.8%, −0.8%, and −1.9%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `9ebecc7107969680` recorded 526 Classic errors and 30 Tank Royale errors. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 0, 6, 0, and 6; the registry records bot IDs but this finding does not attribute the events to Frederick.

## Finding

Frederick remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Frederick by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/apc.botM_3.0.jar` (`DISCREPANCY (errors)`).

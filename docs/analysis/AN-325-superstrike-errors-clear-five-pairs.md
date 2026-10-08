---
id: AN-325
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: SuperStrike's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-325 — SuperStrike's prior melee errors clear in five current pairs

## Risk investigated

Whether `amk.superstrike.SuperStrike_0.3.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `3ac91ce55fdc3081` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `f056b2427aa3b9ef0b1aab1f4d5c2abfee3482da`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,580.4 points and Tank Royale averaged 111,477.0, for a −3.54% mean delta. Pair deltas were −4.0%, −3.1%, −4.3%, −3.3%, and −3.0%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `c1efcec566203c27` recorded 1,028 Classic errors and 31 Tank Royale errors. The registry's existing diagnosis attributes that result to melee opponent-pool contamination. Neither engine reported errors in the current five-pair run. Skipped-turn telemetry was captured in all five pairs, with event counts 0, 0, 0, 1, and 0; the registry records bot IDs but this finding does not attribute the event to SuperStrike.

## Finding

SuperStrike remains within the score-noise band, and its prior error discrepancy did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to SuperStrike by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amz.NanoClone_1.41.jar` (`DISCREPANCY (errors)`).

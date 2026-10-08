---
id: AN-326
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: NanoClone's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-326 — NanoClone's prior melee errors clear in five current pairs

## Risk investigated

Whether `amz.NanoClone_1.41.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `81b7c6e567ae195a` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `f056b2427aa3b9ef0b1aab1f4d5c2abfee3482da`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,403.6 points and Tank Royale averaged 108,770.8, for a −5.72% mean delta. Pair deltas were −5.4%, −6.1%, −6.2%, −5.4%, and −5.5%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `ab53268224441bab` recorded 596 Classic errors and 29 Tank Royale errors. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in every pair, with event counts 0, 1, 1, 0, and 5; the registry records bot IDs but this finding does not attribute the events to NanoClone.

## Finding

NanoClone's prior error discrepancy did not recur under the latest matched artifacts. The −5.72% score difference remains within the registry's `MATCHED (score noise)` classification. The skipped-turn records are preserved in the registry and are not attributed to NanoClone by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ap.Frederick_1.1.jar` (`DISCREPANCY (errors)`).

---
id: AN-345
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Gnarly's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-345 — Gnarly's prior melee errors clear in five current pairs

## Risk investigated

Whether `bts.mega.Gnarly_1.4.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `77d1f463511e268b1a020f8571fe932a03760447e30067846a3c1d6090368f81`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `5d0b10a000736054` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `546d7c330d13999567a07197916d7d8e199a153b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,226.4 points and Tank Royale averaged 111,255.4 points, for a −2.62% mean delta. Pair deltas were −2.0%, −2.2%, −3.7%, −3.0%, and −2.2%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `ce3b89f81e762176` recorded 616 Classic errors and 31 Tank Royale errors, with a −2.4% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 0, 5, 9, 0, and 2. The registry records bot IDs, but this finding does not attribute those events to Gnarly.

## Finding

Gnarly remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bts.wiki.RipCurl_0.9b.jar` (`DISCREPANCY (outcome)`).

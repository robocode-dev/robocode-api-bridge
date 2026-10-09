---
id: AN-357
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Insomnia's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-357 — Insomnia's prior melee errors clear in five current pairs

## Risk investigated

Whether `cb.nano.Insomnia_1.0.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `dfaa0b7522185985faaaad2c8c8761b56ed6b2e31f9d256de0abbcf466863d30`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `b32a6d34fb545e08` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `17ea6230758302718d6dd4b2c996214759d1205d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,392.2 points and Tank Royale averaged 111,911.8 points, for a −2.18% mean delta. Pair deltas were −2.3%, −2.2%, −2.0%, −2.4%, and −2.0%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `84e586ddb595dfd0` recorded 376 Classic errors and 30 Tank Royale errors, with a −2.4% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 6, 4, 5, 10, and 9. The registry records bot IDs, but this finding does not attribute those events to Insomnia.

## Finding

Insomnia remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cf.RiO.RiOx_4.2.1.jar` (`DISCREPANCY (outcome)`).

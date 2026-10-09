---
id: AN-355
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Furia Ceca's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-355 — Furia Ceca's prior melee errors clear in five current pairs

## Risk investigated

Whether `caimano.Furia_Ceca_0.22.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `c1ce5bbf430fe9edc12da1768284be6f3d5bc35a9f48c84819f21edd32241616`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `3ec414ac84060d6b` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `17ea6230758302718d6dd4b2c996214759d1205d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,921.4 points and Tank Royale averaged 109,094.4 points, for a −5.04% mean delta. Pair deltas were −6.0%, −5.7%, −4.7%, −4.5%, and −4.3%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `00d3cf985f8e3cea` recorded 374 Classic errors and 31 Tank Royale errors, with a −4.1% score delta. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all pairs, with event counts 2, 0, 0, 0, and 5. The registry records bot IDs, but this finding does not attribute those events to Furia Ceca.

## Finding

Furia Ceca remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cb.fire.Firestarter_2.0f.jar` (`DISCREPANCY (errors)`).

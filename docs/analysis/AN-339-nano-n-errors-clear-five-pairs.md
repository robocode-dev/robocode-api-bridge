---
id: AN-339
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Baal.nano.N's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-339 — Baal.nano.N's prior melee errors clear in five current pairs

## Risk investigated

Whether `baal.nano.N_1.42.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 is `a653da798755fcbfe9b62af99b7dfaa37bef7495dc2dc19d908e97e95693bee0`; selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `73b1c1840aacde45` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d02611ff9f6d1c39c98f193dfefc8e4de9636fef`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,137.6 points and Tank Royale averaged 112,653.0 points, for a −1.3% mean delta. Pair deltas were −0.2%, −2.1%, −0.2%, −2.2%, and −1.8%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `ca2b3b3a8bd537d6` recorded 292 Classic errors and 30 Tank Royale errors, with a score delta of approximately 0.0%. The current five-pair run did not reproduce the error discrepancy. Skipped-turn telemetry was captured in all pairs, with event counts 2, 3, 7, 0, and 2. The registry records bot IDs, but this finding does not attribute those events to Baal.nano.N.

## Finding

Baal.nano.N remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The previous opponent-pool contamination diagnosis remains attached to the historical observations; this run does not attribute those errors to the subject. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bayen.UbaMicro_1.4.jar` (`DISCREPANCY (errors)`).

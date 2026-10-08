---
id: AN-310
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ar1's prior melee error imbalance clears in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-310 — Ar1's prior melee error imbalance clears in five current pairs

## Risk investigated

Whether `adt.Ar1_2.1.jar`'s historical Classic-only error imbalance and score difference persist in the M-006 melee setup under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `37857b3fe3d082f121da8dd8ca81a5ea58edcab65fad3c552d71dfa65f58b14e`. The official five-pair confirmation `9ec2c336d8d76bbf` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `4bce9a2c312163631519e597d21f74f61f2f9d19`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,707.0 points and Tank Royale averaged 113,478.4, for a −1.1% mean delta. Pair deltas were −0.5%, −1.0%, −0.7%, −2.7%, and −0.6%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-184's earlier one-pair result had a −1.3% delta, no Tank Royale errors, and 74 Classic errors, including selected melee-opponent failures. None of those error imbalances recurred in the current five pairs. Skipped-turn telemetry was captured completely; event counts were 4, 2, 6, 4, and 0 across the runs. The records contain bot IDs but do not identify which robot owns each event here.

## Finding

Ar1 remains within the score-noise band, and the earlier Classic-only melee errors did not recur under the latest matched artifacts. The skipped-turn events are preserved in the registry and are not attributed to Ar1 by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/adt.Ar2_1.0.jar` (`DISCREPANCY (errors)`).

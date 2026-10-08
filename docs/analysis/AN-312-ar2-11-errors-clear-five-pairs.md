---
id: AN-312
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ar2 1.1's prior melee error imbalance clears in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-312 — Ar2 1.1's prior melee error imbalance clears in five current pairs

## Risk investigated

Whether `adt.Ar2_1.1.jar`'s historical Classic-only melee errors and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `56ce37e16ebf9257b337c9ca4cc670886bd795fdc424433f1ab5eda32b7fa3a6`. The official five-pair confirmation `7a57fed4fd42ca62` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `4bce9a2c312163631519e597d21f74f61f2f9d19`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,536.8 points and Tank Royale averaged 111,717.4, for a −2.46% mean delta. Pair deltas were −1.7%, −1.6%, −3.1%, −2.8%, and −3.1%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-186's earlier one-pair result had a −1.4% delta, no Tank Royale errors, and 562 Classic errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure. The current five-pair run did not reproduce the error imbalance. Skipped-turn telemetry was captured completely; event counts were 0, 0, 4, 0, and 1 across the runs. The records contain bot IDs but do not identify which robot owns each event here.

## Finding

Ar2 1.1 remains within the score-noise band, and the earlier Classic-only melee errors did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Ar2 1.1 by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/agd.Mooserwirt2_2.7.jar` (`DISCREPANCY (errors)`).

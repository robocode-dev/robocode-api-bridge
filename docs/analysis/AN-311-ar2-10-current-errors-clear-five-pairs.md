---
id: AN-311
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ar2's prior melee error imbalance clears in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-311 — Ar2's prior melee error imbalance clears in five current pairs

## Risk investigated

Whether `adt.Ar2_1.0.jar`'s historical Classic-only melee errors and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `2adf54760aefa12c1e79667c064a9529b85aed2218e8a7457c335fb91153394e`. The official five-pair confirmation `53a0ed9d128c019b` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `4bce9a2c312163631519e597d21f74f61f2f9d19`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,883.6 points and Tank Royale averaged 113,098.4, for a −1.56% mean delta. Pair deltas were −1.9%, −0.4%, −1.1%, −2.7%, and −1.7%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-185's earlier one-pair result had a −0.8% delta, no Tank Royale errors, and 754 Classic errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure. The current five-pair run did not reproduce the error imbalance. Skipped-turn telemetry was captured completely; event counts were 6, 3, 5, 2, and 4 across the runs. The records contain bot IDs but do not identify which robot owns each event here.

## Finding

Ar2 remains within the score-noise band, and the previous Classic-only melee errors did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Ar2 by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/adt.Ar2_1.1.jar` (`DISCREPANCY (errors)`).

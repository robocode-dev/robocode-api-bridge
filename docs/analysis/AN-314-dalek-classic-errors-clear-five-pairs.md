---
id: AN-314
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Dalek's prior Classic melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-314 — Dalek's prior Classic melee errors clear in five current pairs

## Risk investigated

Whether `agrach.Dalek_1.0.jar`'s historical Classic-only melee errors and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `1b594bd828661ec7fbeb11aba73bccf7567d16e78a9ff21114a1f430c7152836`. The official five-pair confirmation `2556e61118388713` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `4bce9a2c312163631519e597d21f74f61f2f9d19`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,933.0 points and Tank Royale averaged 113,172.2, for a −1.54% mean delta. Pair deltas were −1.7%, −2.1%, −1.4%, −1.6%, and −0.9%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-188's earlier one-pair result had a −1.8% delta, no Tank Royale errors, and 466 Classic errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure. The current five-pair run did not reproduce the error imbalance. Skipped-turn telemetry was captured completely; event counts were 0, 10, 0, 4, and 0 across the runs. The records contain bot IDs but do not identify which robot owns each event here.

## Finding

Dalek remains within the score-noise band, and the earlier Classic-only melee errors did not recur under the latest matched artifacts. The skipped-turn events are preserved in the registry and are not attributed to Dalek by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ags.Glacier_0.2.11.jar` (`DISCREPANCY (errors)`).

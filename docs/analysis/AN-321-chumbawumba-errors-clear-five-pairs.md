---
id: AN-321
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ChumbaWumba's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-321 — ChumbaWumba's prior melee errors clear in five current pairs

## Risk investigated

Whether `amk.ChumbaWumba_0.3.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `3be61e15a26a2e8f` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,263.6 points and Tank Royale averaged 113,277.0, for a −0.88% mean delta. Pair deltas were −1.6%, −0.9%, −1.3%, −0.1%, and −0.5%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `c2c9ad1070d4caa6` recorded 548 Classic errors and 29 Tank Royale errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure. The current five-pair run did not reproduce that imbalance. Skipped-turn telemetry was captured in all five pairs, with event counts 1, 5, 4, 0, and 0; the registry records bot IDs but this finding does not attribute the events to ChumbaWumba.

## Finding

ChumbaWumba remains within the score-noise band, and its prior melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to ChumbaWumba by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amk.Punbot.Punbot_0.01.jar` (`DISCREPANCY (errors)`).

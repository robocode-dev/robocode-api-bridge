---
id: AN-320
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ChumbaMini's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-320 — ChumbaMini's prior melee errors clear in five current pairs

## Risk investigated

Whether `amk.ChumbaMini_0.2.jar`'s historical Classic-only melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `6ee9a1185b69b57b` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,252.8 points and Tank Royale averaged 113,055.6, for a −1.06% mean delta. Pair deltas were −1.8%, −0.5%, −1.7%, −0.6%, and −0.7%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `a4eea4df8525940e` recorded 600 Classic errors and 30 Tank Royale errors, including `amk.ChumbaMini.saveData` and Classic's `amk.guns.Aristocles.prepare` signature. The current five-pair run did not reproduce those errors. Skipped-turn telemetry was captured in all five pairs, with event counts 1, 1, 0, 0, and 0; the registry records bot IDs but this finding does not attribute the events to ChumbaMini.

## Finding

ChumbaMini remains within the score-noise band, and its previous melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to ChumbaMini by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ChumbaWumba_0.3.jar` (`DISCREPANCY (errors)`).

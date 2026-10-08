---
id: AN-322
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Punbot's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-322 — Punbot's prior melee errors clear in five current pairs

## Risk investigated

Whether `amk.Punbot.Punbot_0.01.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `572c678f43f12cf1` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `f056b2427aa3b9ef0b1aab1f4d5c2abfee3482da`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. All jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,038.8 points and Tank Royale averaged 113,136.6, for a −0.78% mean delta. Pair deltas were −0.4%, −1.6%, −0.3%, −0.9%, and −0.7%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

The previous latest observation `af7f1ca38b4b8b25` recorded 764 Classic errors and 30 Tank Royale errors. Classic signatures included the selected opponent `amk.ChumbaMini_0.2.jar`'s data-file failure and `amk.guns.Aristocles.prepare`; Tank Royale also reported ChumbaMini's data-file failure. The current five-pair run did not reproduce those errors. Skipped-turn telemetry was captured in all five pairs, with event counts 8, 4, 0, 0, and 3; the registry records bot IDs but this finding does not attribute the events to Punbot.

## Finding

Punbot remains within the score-noise band, and its previous melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Punbot by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ShizzleStiX.ShizzleStiX_0.6.jar` (`DISCREPANCY (errors)`).

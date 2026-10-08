---
id: AN-318
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ice's prior Classic melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-318 — Ice's prior Classic melee errors clear in five current pairs

## Risk investigated

Whether `ahr.ice.Ice_1.0.2.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `46da6577681b39d9a472ff9559a8e29fa9fa0dde914b2854b22dde5c4d89a507`. The official five-pair confirmation `fbd4f382798b98fc` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifact hashes are recorded there; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,343.6 points and Tank Royale averaged 112,584.8, for a −1.54% mean delta. Pair deltas were −2.1%, −1.1%, −0.8%, −2.7%, and −1.0%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-192's earlier one-pair run had 606 Classic errors and no Tank Royale errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure. The latest prior observation still showed a 580-to-30 error imbalance. The current five-pair run did not reproduce either error pattern. Skipped-turn telemetry was captured in every pair, with event counts 13, 7, 9, 9, and 1; the registry records bot IDs but this finding does not attribute those events to Ice.

## Finding

Ice remains within the score-noise band, and its previous melee error imbalance did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Ice by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ak.Fermat_2.0.jar` (`DISCREPANCY (errors)`).

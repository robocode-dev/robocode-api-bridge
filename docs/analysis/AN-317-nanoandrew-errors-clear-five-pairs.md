---
id: AN-317
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: NanoAndrew's prior Classic melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-317 — NanoAndrew's prior Classic melee errors clear in five current pairs

## Risk investigated

Whether `ahf.NanoAndrew_.4.jar`'s historical Classic-only melee errors and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `667ad546b341badd93276d9de63623cf8ddd42918f46a2a04b23dbe114bacf2d`. The official five-pair confirmation `0494542588361931` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifact hashes are recorded there; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,427.2 points and Tank Royale averaged 112,871.0, for a −2.22% mean delta. Pair deltas were −2.9%, −1.6%, −1.9%, −2.7%, and −2.0%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-191's earlier one-pair run recorded 556 Classic errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure, and no Tank Royale errors. The current five-pair run did not reproduce that error imbalance. Skipped-turn telemetry was captured in all five runs, with event counts 0, 0, 6, 0, and 5; the registry records bot IDs but this finding does not attribute those events to NanoAndrew.

## Finding

NanoAndrew remains within the score-noise band, and the earlier Classic-only melee errors did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to NanoAndrew by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ahr.ice.Ice_1.0.2.jar` (`DISCREPANCY (errors)`).

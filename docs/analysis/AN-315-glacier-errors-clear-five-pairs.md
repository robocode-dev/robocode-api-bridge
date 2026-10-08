---
id: AN-315
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Glacier's prior Classic melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-315 — Glacier's prior Classic melee errors clear in five current pairs

## Risk investigated

Whether `ags.Glacier_0.2.11.jar`'s historical Classic-only melee errors and score difference persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `37122320b1f4f8572edd27b8fa5549af5b55b89738e855945cb69ff0713cb8b0`. The official five-pair confirmation `295268a47bfef84f` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 114,226.0 points and Tank Royale averaged 111,932.0, for a −2.0% mean delta. Pair deltas were −1.9%, −2.9%, −1.0%, −1.4%, and −2.8%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-189's earlier one-pair result had a −1.9% delta, no Tank Royale errors, and 680 Classic errors, including the selected melee opponent `amk.ChumbaMini_0.2.jar`'s data-file failure. The current five-pair run did not reproduce the error imbalance. Skipped-turn telemetry was captured completely; event counts were 0, 6, 4, 2, and 7 across the runs. The records contain bot IDs but do not identify which robot owns each event here.

## Finding

Glacier remains within the score-noise band, and the earlier Classic-only melee errors did not recur under the latest matched artifacts. The skipped-turn records are preserved in the registry and are not attributed to Glacier by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ags.surreptitious.MiniSurreptitious_0.0.1.jar` (`DISCREPANCY (errors)`).

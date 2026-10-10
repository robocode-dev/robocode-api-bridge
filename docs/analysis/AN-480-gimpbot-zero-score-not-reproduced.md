---
id: AN-480
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: GimpBot's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-480 — GimpBot's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gimp.GimpBot_0.1.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `e6ea808162e9504f8f6a9f97bfe55123269e30a2ab444991a104d46acffb43a6`. The official five-pair confirmation `48e17d7f501f2155` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `acc92002dd0ce91cde5c6770f081f258403042af`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 8,632.0 points and Tank Royale averaged 7,611.8 points, for a −11.68% mean delta. Pair deltas were −8.1%, −14.4%, −14.5%, −16.7%, and −4.7%. Neither engine reported errors, and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five Tank Royale attempts, with two events in attempt 5 at round 13, turn 39, for bots 1 and 2. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `971d83252cd783ae` recorded Classic scores of 0 and 0 and Tank Royale scores of 4,296 and 3,477, with no runtime errors. The current run produced scores from both engines in every attempt. Its −11.68% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

GimpBot's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −11.68%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gimp.GimpBot_0.1.jar` and `roborumble/gio.RealGioBot_1.0.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gjr.Cephalosporin_0.2.jar` (`DISCREPANCY (no score)`).

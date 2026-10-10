---
id: AN-478
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Grofvuil's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-478 — Grofvuil's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gh.nano.Grofvuil_0.2.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `93b2a73e350b902abfa4b615d1cb10f3350617e66ebafbf001f68b4557c4046c`. The official five-pair confirmation `0a0b9e779f49b529` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `acc92002dd0ce91cde5c6770f081f258403042af`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,566.6 points and Tank Royale averaged 3,957.4 points, for a −13.08% mean delta. Pair deltas were −11.2%, −22.3%, −10.7%, −9.9%, and −11.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `2d26131918c382a7` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,137 and 1,525, with no runtime errors. The current run produced scores from both engines in every attempt. Its −13.08% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Grofvuil's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −13.08%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gh.nano.Grofvuil_0.2.jar` (`MATCHED (score noise)`). Record `roborumble/ghent.ArthurPanzergon_1.0.0.jar` as `DISCREPANCY (errors)`. Continue in registry order with `roborumble/gimp.GimpBot_0.1.jar` (`DISCREPANCY (no score)`).

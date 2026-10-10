---
id: AN-481
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: RealGioBot's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-481 — RealGioBot's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gio.RealGioBot_1.0.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `d2c253079cafb361191c31ce3ca5f20dfe92a4c06179f9f3b9a4bf3e79b3d865`. The official five-pair confirmation `6a3edb974087b449` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6dbe57bf69937995ceddf5a8433fc0af3972ca29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,533.6 points and Tank Royale averaged 5,103.4 points, for a −7.68% mean delta. Pair deltas were −6.3%, −13.5%, −5.7%, −1.4%, and −11.5%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `5e1535e7c64aba59` recorded Classic scores of 0 and 0 and Tank Royale scores of 3,009 and 1,620, with no runtime errors. The current run produced scores from both engines in every attempt. Its −7.68% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

RealGioBot's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −7.68%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gio.RealGioBot_1.0.jar` and `roborumble/gjr.Cephalosporin_0.2.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/goblin.Bender_2.4.jar` (`DISCREPANCY (no score)`).

---
id: AN-483
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Bender's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-483 — Bender's earlier Classic zero score is not reproduced

## Risk investigated

Whether `goblin.Bender_2.4.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `8d07c7e25d7f1ab4fc98fae2df726866a7650be386db86daf6310d2f1c4bf7e0`. The official five-pair confirmation `0c1c46af75f0f591` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6dbe57bf69937995ceddf5a8433fc0af3972ca29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,818.2 points and Tank Royale averaged 6,345.0 points, for a +9.10% mean delta. Pair deltas were +16.0%, +5.5%, +12.7%, +5.3%, and +6.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `c974dc2f04983d30` recorded Classic scores of 0 and 0 and Tank Royale scores of 3,577 and 3,008, with no runtime errors. The current run produced scores from both engines in every attempt. Its +9.10% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Bender's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is +9.10%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/goblin.Bender_2.4.jar` and `roborumble/gre.svman4.Leonidas_1.3.2.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gre.svman4.Morfeas_1.4.3.jar` (`DISCREPANCY (outcome)`).

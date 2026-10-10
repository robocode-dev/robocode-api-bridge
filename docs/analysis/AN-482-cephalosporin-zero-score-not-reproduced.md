---
id: AN-482
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Cephalosporin's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-482 — Cephalosporin's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gjr.Cephalosporin_0.2.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `8133810e4ce3b00dd583b3344dd942607a2666861d160d8d2d422e11953d6369`. The official five-pair confirmation `c24d1cb6c982e2b6` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6dbe57bf69937995ceddf5a8433fc0af3972ca29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 9,795.6 points and Tank Royale averaged 9,908.0 points, for a +1.54% mean delta. Pair deltas were +4.5%, −1.1%, +15.2%, −15.2%, and +4.3%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was unavailable in attempts 1 and 2 and incomplete in attempts 3, 4, and 5. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `db03f6ffc0fe3874` recorded Classic scores of 0 and 0 and Tank Royale scores of 5,409 and 5,088, with no runtime errors. The current run produced scores from both engines in every attempt. Its +1.54% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. Skipped-turn telemetry was not fully captured, and no controlled trace or source comparison was made. The score difference was not attributed to either engine or the bridge, and no code or rumble-jar change was made.

## Finding

Cephalosporin's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is +1.54%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gjr.Cephalosporin_0.2.jar` and `roborumble/goblin.Bender_2.4.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gre.svman4.Leonidas_1.3.2.jar` (`DISCREPANCY (no score)`).

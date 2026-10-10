---
id: AN-484
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Leonidas's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-484 — Leonidas's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gre.svman4.Leonidas_1.3.2.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `0e4775eaa84ab348168b94c608810a0430353fcc3cafcf052ad3a7a78b2a53a8`. The official five-pair confirmation `a6e727109c432d29` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6dbe57bf69937995ceddf5a8433fc0af3972ca29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,604.4 points and Tank Royale averaged 5,127.0 points, for a −8.48% mean delta. Pair deltas were −10.7%, −9.1%, −5.4%, −10.8%, and −6.4%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five attempts, with four events in attempt 3 for bots 1 and 2 at rounds 15, turns 1 and 2. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `58b0a10a9eac6c89` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,852 and 2,749, with no runtime errors. The current run produced scores from both engines in every attempt. Its −8.48% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Leonidas's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −8.48%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gre.svman4.Leonidas_1.3.2.jar` (`MATCHED (score noise)`). Record `roborumble/gre.svman4.Morfeas_1.4.3.jar` as `DISCREPANCY (errors)`. Continue in registry order with `roborumble/grybgoofy.GoofyBot_0.10.jar` (`DISCREPANCY (no score)`).

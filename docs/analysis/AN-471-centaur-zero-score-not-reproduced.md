---
id: AN-471
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Centaur's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-471 — Centaur's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gf.Centaur.Centaur_0.6.7.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `2ca75c3191793499e863c6715a640744585770ca80493fc8433b5889f6277616`. The official five-pair confirmation `c74a61dc683e4a20` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `52a2e464caed1629dedc59319f42ab7686fab425`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,113.2 points and Tank Royale averaged 4,582.2 points, for a −10.34% mean delta. Pair deltas were −11.4%, −11.1%, −7.8%, −13.4%, and −8.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `64630a3acc5ad08d` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,897 and 2,586, with no runtime errors. The current run produced scores from both engines in every attempt. Its −10.34% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Centaur's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −10.34%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gf.Centaur.Centaur_0.6.7.jar` and `roborumble/gg.Squaraus_0.6.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gg.Wolverine_2.0.jar` (`DISCREPANCY (no score)`).

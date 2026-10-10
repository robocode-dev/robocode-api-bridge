---
id: AN-472
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Squaraus's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-472 — Squaraus's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gg.Squaraus_0.6.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `8134f4c2d9cf82b19059f201aa2655e71c5d5fac45fa27830620e039edad96d2`. The official five-pair confirmation `9bbe6ec81e06507d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `52a2e464caed1629dedc59319f42ab7686fab425`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,170.4 points and Tank Royale averaged 5,804.8 points, for a −5.74% mean delta. Pair deltas were −1.1%, −15.1%, −9.8%, +5.6%, and −8.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `af5d371fd0d8ebc8` recorded Classic scores of 0 and 0 and Tank Royale scores of 3,333 and 2,500, with no runtime errors. The current run produced scores from both engines in every attempt. Its −5.74% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Squaraus's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is −5.74%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gg.Squaraus_0.6.jar` (`MATCHED (score noise)`). Record `roborumble/gg.Wolverine_2.0.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/gh.GresSuffurd_0.4.13.jar` (`DISCREPANCY (no score)`).

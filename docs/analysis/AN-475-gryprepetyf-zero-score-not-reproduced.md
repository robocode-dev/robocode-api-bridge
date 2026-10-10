---
id: AN-475
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: GrypRepetyf's earlier Classic zero score is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-475 — GrypRepetyf's earlier Classic zero score is not reproduced

## Risk investigated

Whether `gh.GrypRepetyf_0.13.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether current five-pair scores show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `cec04c487a3da61b047ba4a0661b9fbf8ec9ad78aa93efafdb702f5f57a0416a`. The official five-pair confirmation `da8a7a95529c0412` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `52a2e464caed1629dedc59319f42ab7686fab425`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,854.2 points and Tank Royale averaged 5,015.0 points, for a +3.40% mean delta. Pair deltas were −2.9%, +15.1%, +6.4%, −2.1%, and +0.5%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `43c8214c070a00fd` recorded Classic scores of 0 and 0 and Tank Royale scores of 3,751 and 2,494, with no runtime errors. The current run produced scores from both engines in every attempt. Its +3.40% mean delta is within the 15-point five-pair classification band; the manifest's `threshold: 25.0` is the regular single-pair review setting.

## What was not pursued

The current scores do not explain why the earlier Classic result was zero. No controlled trace or source comparison was made, and the difference was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

GrypRepetyf's earlier Classic zero-score result did not recur under current matched artifacts. All five pairs produced scores, the mean delta is +3.40%, and the registry classifies it as `MATCHED (score noise)`. The cause of the earlier zero score remains unknown.

## M-006 handoff

Skip `roborumble/gh.GrypRepetyf_0.13.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gh.micro.Grinnik_1.0.jar` (`DISCREPANCY (no score)`).

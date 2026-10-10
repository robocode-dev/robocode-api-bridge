---
id: AN-476
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Grinnik's earlier no-score result is replaced by a confirmed Classic advantage
provenance: inferred
reversal-cost: low
---

# AN-476 — Grinnik's earlier no-score result is replaced by a confirmed Classic advantage

## Risk investigated

Whether `gh.micro.Grinnik_1.0.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether the current five-pair run confirms a score difference.

## Evidence boundary

The read-only subject jar has SHA-256 `60eed7a095bd7c74c944467920bdbd6d5598f9e038e7f3d6a690edda37d8504b`. The official five-pair confirmation `69601c2cf35d8711` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `acc92002dd0ce91cde5c6770f081f258403042af`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,093.2 points and Tank Royale averaged 4,027.6 points, for a −33.42% mean delta. Pair deltas were −45.5%, −19.8%, −42.1%, −35.7%, and −24.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `e524b4d483d0635e` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,220 and 2,156, with no runtime errors. The current run produced scores from both engines in every attempt. Its −33.42% mean delta confirms a Classic score advantage under the current matched artifacts.

## What was not pursued

The current run does not explain why the earlier Classic result was zero or what caused the score gap. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Grinnik's earlier Classic zero-score outcome did not recur. The current five pairs all produced scores and confirmed a −33.42% mean delta in Classic's favor; the registry classifies it as `CONFIRMED (score)`. The earlier zero-score cause and the score gap's cause remain unknown.

## M-006 handoff

Record `roborumble/gh.micro.Grinnik_1.0.jar` and `roborumble/gh.mini.Grimmig_0.3.6.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/gh.nano.Grofvuil_0.2.jar` (`DISCREPANCY (no score)`).

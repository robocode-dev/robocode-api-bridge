---
id: AN-477
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Grimmig's earlier no-score result is replaced by a confirmed Classic advantage
provenance: inferred
reversal-cost: low
---

# AN-477 — Grimmig's earlier no-score result is replaced by a confirmed Classic advantage

## Risk investigated

Whether `gh.mini.Grimmig_0.3.6.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether the current five-pair run confirms a score difference.

## Evidence boundary

The read-only subject jar has SHA-256 `1d65ae25c123ff86779c33f26374f0aa72ade6a5f4497196aa3c6f7be23b6ffa`. The official five-pair confirmation `683ca4da925939e8` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `acc92002dd0ce91cde5c6770f081f258403042af`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,249.6 points and Tank Royale averaged 4,308.2 points, for a −30.96% mean delta. Pair deltas were −30.8%, −32.3%, −42.5%, −29.3%, and −19.9%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `5f21e7bfd82d02e0` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,811 and 1,694, with no runtime errors. The current run produced scores from both engines in every attempt. Its −30.96% mean delta confirms a Classic score advantage under the current matched artifacts.

## What was not pursued

The current run does not explain why the earlier Classic result was zero or what caused the score gap. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Grimmig's earlier Classic zero-score outcome did not recur. The current five pairs all produced scores and confirmed a −30.96% mean delta in Classic's favor; the registry classifies it as `CONFIRMED (score)`. The earlier zero-score cause and the score gap's cause remain unknown.

## M-006 handoff

Record `roborumble/gh.mini.Grimmig_0.3.6.jar` as `CONFIRMED (score)`. Skip `roborumble/gh.nano.Grofvuil_0.2.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/ghent.ArthurPanzergon_1.0.0.jar` (`DISCREPANCY (outcome)`).

---
id: AN-470
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: TheMind's earlier no-score result is replaced by a confirmed score gap
provenance: inferred
reversal-cost: low
---

# AN-470 — TheMind's earlier no-score result is replaced by a confirmed score gap

## Risk investigated

Whether `germ.TheMind_.2.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether the current five-pair run confirms a score difference.

## Evidence boundary

The read-only subject jar has SHA-256 `f4f5e112e785354806cc13b5cfc5232213204194654259a7c86a9404b767bf8c`. The official five-pair confirmation `f8d2937ab9528993` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `f1e3d67ca92799db2af4a4eb06ffffa16ab0e022`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,321.2 points and Tank Royale averaged 2,415.0 points, for a −61.64% mean delta. Pair deltas were −63.2%, −61.5%, −58.8%, −66.4%, and −58.3%. Neither engine reported errors, and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five Tank Royale attempts, with one event in attempt 2 at bot 1, round 14, turn 721. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `f6001e8b572533b4` recorded Classic scores of 0 and 0 and Tank Royale scores of 1,198 and 1,145, with no runtime errors. The current run produced scores from both engines in every attempt. Its −61.64% mean delta confirms a large Classic score advantage under the current matched artifacts.

## What was not pursued

The current run does not explain why the earlier Classic result was zero or what caused the large current score gap. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

TheMind's earlier Classic zero-score outcome did not recur. The current five pairs all produced scores and confirmed a −61.64% mean delta in Classic's favor; the registry classifies it as `CONFIRMED (score)`. The earlier zero-score cause and the score gap's cause remain unknown.

## M-006 handoff

Record `roborumble/germ.TheMind_.2.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/gf.Centaur.Centaur_0.6.7.jar` (`DISCREPANCY (no score)`).

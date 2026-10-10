---
id: AN-493
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: T3 0.2's Classic score advantage persists
provenance: inferred
reversal-cost: low
---

# AN-493 — T3 0.2's Classic score advantage persists

## Risk investigated

Whether `ha2.T3_0.2.jar`'s historical large Classic score advantage persists under current matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `67a27f0941ceeaf22041c6497011dead138b8923a95dc5cb131b567ff46c9ce6`. The official five-pair confirmation `74e526c4b8b9225d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea7f653a103424543763ad493b0b24ed9e940409`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 7,373.0 points and Tank Royale averaged 5,032.8 points, for a −31.64% mean delta. Pair deltas were −35.1%, −34.5%, −33.0%, −31.9%, and −23.7%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `d16bed013eca821a` recorded Classic scores of 7,674 and Tank Royale scores of 4,747, for a −38.1% delta. The current mean delta remains a large Classic advantage at −31.64%; the registry confirms the score difference across all five pairs.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

T3 0.2's Classic score advantage persists under current matched artifacts. The mean delta is −31.64%, and the registry classifies it as `CONFIRMED (score)`; its cause remains unknown.

## M-006 handoff

Skip `roborumble/ha2.T3_0.1.jar` (`PASS`). Record `roborumble/ha2.T3_0.2.jar` as `CONFIRMED (score)` and `roborumble/hfgrobots.HFG_1.0.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/hirataatsushi.Trinity_0.003.jar` (`score-review`).

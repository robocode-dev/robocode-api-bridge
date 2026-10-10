---
id: AN-494
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: HFG's Classic score advantage is confirmed again at a smaller gap
provenance: inferred
reversal-cost: low
---

# AN-494 — HFG's Classic score advantage is confirmed again at a smaller gap

## Risk investigated

Whether `hfgrobots.HFG_1.0.jar`'s earlier Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `ba26b277f95c9e06b2bb5f2cc54d9a2cb9e9c292c2b74920c41261a172c183ed`. The official five-pair confirmation `e38a09008f3f491e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea7f653a103424543763ad493b0b24ed9e940409`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,060.0 points and Tank Royale averaged 4,873.4 points, for a −18.78% mean delta. Pair deltas were −29.1%, −19.4%, −4.7%, −23.8%, and −16.9%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five attempts, with one event in attempt 2 for bot 2 at round 27, turn 1,136. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `6421835506f37155` recorded Classic scores of 6,203 and Tank Royale scores of 4,625, for a −25.4% delta. The current five-pair mean remains beyond the 15-point classification band at −18.78%, though its magnitude is smaller than the earlier single-pair result; the registry still confirms a Classic score advantage.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

HFG's Classic score advantage persists under current matched artifacts. The mean delta is −18.78%, smaller than the earlier −25.4% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Skip `roborumble/hirataatsushi.Neo_1.6.jar` (`PASS`). Record `roborumble/hfgrobots.HFG_1.0.jar` and `roborumble/hirataatsushi.Trinity_0.003.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/hlavko.micro.Flex_1.5.jar` (`score-review`).

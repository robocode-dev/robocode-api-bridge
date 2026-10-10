---
id: AN-497
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Ringo 2.0's Classic score advantage is confirmed at a larger gap
provenance: inferred
reversal-cost: low
---

# AN-497 — Ringo 2.0's Classic score advantage is confirmed at a larger gap

## Risk investigated

Whether `hlavko.nano.Ringo_2.0.jar`'s earlier Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `ba30da9626ae7b3c83c57d65b121edad8d9c7c8342c7a688fdb2dbae33899b8b`. The official five-pair confirmation `2beb568a28576490` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0e4ae538692b96d2afe7a8480a00193496c7c0c1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,002.6 points and Tank Royale averaged 2,799.8 points, for a −30.02% mean delta. Pair deltas were −26.8%, −30.3%, −31.5%, −31.9%, and −29.6%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `9924522cdf6df090` recorded Classic scores of 3,745 and Tank Royale scores of 2,778, for a −25.8% delta. The current five-pair mean confirms the same Classic advantage at −30.02%, a larger gap than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Ringo 2.0's Classic score advantage persists under current matched artifacts. The mean delta is −30.02%, larger than the earlier −25.8% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/hlavko.nano.Ringo_2.0.jar` as `CONFIRMED (score)`. Skip `roborumble/homerbots.h1_1.0.jar` and `roborumble/hp.Athena_0.1.jar` (`PASS`). Record `roborumble/hs.SimpleHBot_1.3.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/hvilela.HVilela_0.9.jar` (`score-review`).

---
id: AN-514
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: JGAP6139's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-514 — JGAP6139's Classic score advantage is confirmed again

## Risk investigated

Whether `jgap.JGAP6139_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `09ec73cda63468a296b51573c7ba9fa3afaa54c0c6967d3aaedecfaa29e14163`. The official five-pair confirmation `3944712c56995b56` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `12002b76dec90190d7a49f885f95db3621982767`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 3,865 points and Tank Royale averaged 2,295 points, for a −40.48% mean delta. Pair deltas were −43.1%, −35.8%, −35.8%, −44.5%, and −43.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `9d36b13a51ac844a` recorded Classic scores of 3,852 and Tank Royale scores of 2,184, for a −43.3% delta. The current five-pair mean confirms the same Classic advantage at −40.48%, close to the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

JGAP6139's Classic score advantage persists under current matched artifacts. The mean delta is −40.48%, close to the earlier −43.3% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jgap.JGAP6139_1.0.jar` as `CONFIRMED (score)`. The intervening `roborumble/jgap.JGAP7247_2_1.0.jar` remains `DISCREPANCY (errors)` and is outside this score-review sequence. Record `roborumble/jgap.JGAP7958_1.0.jar` as `CONFIRMED (score)`. Continue with `roborumble/jje.BagPuss_1.2.jar` (`score-review`).

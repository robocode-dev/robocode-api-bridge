---
id: AN-513
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: JGAP23423's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-513 — JGAP23423's Classic score advantage is confirmed again

## Risk investigated

Whether `jgap.JGAP23423_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `9ae213cbc4423af56e0326674570a2f56a4a9efefc52f17b3b0d884fa009d0d6`. The official five-pair confirmation `05fc142df3a2c192` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `12002b76dec90190d7a49f885f95db3621982767`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,287.8 points and Tank Royale averaged 1,934.6 points, for a −54.6% mean delta. Pair deltas were −60.7%, −48.0%, −57.0%, −55.9%, and −51.4%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `898e764b76e36455` recorded Classic scores of 4,581 and Tank Royale scores of 2,046, for a −55.3% delta. The current five-pair mean confirms the same Classic advantage at −54.6%, almost unchanged from the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

JGAP23423's Classic score advantage persists under current matched artifacts. The mean delta is −54.6%, close to the earlier −55.3% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jgap.JGAP23423_1.0.jar` as `CONFIRMED (score)`. Record `roborumble/jgap.JGAP6139_1.0.jar` as `CONFIRMED (score)`. The intervening `roborumble/jgap.JGAP7247_2_1.0.jar` remains `DISCREPANCY (errors)` and is outside this score-review sequence. Continue with `roborumble/jgap.JGAP7958_1.0.jar` (`score-review`).

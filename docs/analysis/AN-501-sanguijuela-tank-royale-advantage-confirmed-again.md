---
id: AN-501
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Sanguijuela's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-501 — Sanguijuela's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `jab.micro.Sanguijuela_0.8.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `f4a0381c0d57dc69ed41162117c417f07e8ca6b35812ddfacec8b8273c41129c`. The official five-pair confirmation `63d4d8d8d9f9792f` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea18af69323cf197eb1bd46489e6cf7296f5d3f8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 10,499 points and Tank Royale averaged 16,684.4 points, for a +58.92% mean delta. Pair deltas were +57.2%, +58.0%, +60.8%, +57.4%, and +61.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `ebb7d7c721bd2a3d` recorded Classic scores of 10,701 and Tank Royale scores of 16,828, for a +57.3% delta. The current five-pair mean confirms the same Tank Royale advantage at +58.92%.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Sanguijuela's Tank Royale score advantage persists under current matched artifacts. The mean delta is +58.92%, close to the earlier +57.3% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jab.micro.Sanguijuela_0.8.jar` as `CONFIRMED (score)`. Skip `roborumble/jaemcrb.nano.M1Abrams_1.0.jar`, `roborumble/jam.RaikoMX_0.32.jar`, and `roborumble/jam.micro.RaikoMicro_1.44.jar` (`PASS`). Record `roborumble/jam.mini.Raiko_0.43.jar` as `CONFIRMED (score)`. Skip `roborumble/janm.Jammy_1.0.jar` (`PASS`). Continue in registry order with `roborumble/japs.Serenity_1.0.jar` (`score-review`).

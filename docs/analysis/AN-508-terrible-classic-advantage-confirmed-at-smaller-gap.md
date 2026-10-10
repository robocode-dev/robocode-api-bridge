---
id: AN-508
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Terrible's Classic score advantage is confirmed at a smaller gap
provenance: inferred
reversal-cost: low
---

# AN-508 — Terrible's Classic score advantage is confirmed at a smaller gap

## Risk investigated

Whether `jep.Terrible_0.4.1.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `95c3e3bc8585109ee1a4b8c839baf53fd7c459382e3c0e01efef65806599a405`. The official five-pair confirmation `5c346080c3a4aaf6` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `580260f11da9c63e1702b10ea979aa94bcc3a040`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,032.6 points and Tank Royale averaged 4,364.2 points, for a −27.64% mean delta. Pair deltas were −27.4%, −28.7%, −32.0%, −25.8%, and −24.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `610b809670c3c242` recorded Classic scores of 6,157 and Tank Royale scores of 4,078, for a −33.8% delta. The current five-pair mean confirms the same Classic advantage at −27.64%, a smaller gap than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Terrible's Classic score advantage persists under current matched artifacts. The mean delta is −27.64%, smaller than the earlier −33.8% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jep.Terrible_0.4.1.jar` as `CONFIRMED (score)`. Skip `roborumble/jep.nano.Hawkwing_0.4.1.jar` and `roborumble/jep.nano.Hotspur_0.1.jar` (`PASS`). Record `roborumble/jeremyreeder.Bully_1.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/jeremyreeder.Vincent_2011.12.09.jar` (`score-review`).

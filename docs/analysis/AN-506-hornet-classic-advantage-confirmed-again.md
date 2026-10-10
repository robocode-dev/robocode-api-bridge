---
id: AN-506
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Hornet's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-506 — Hornet's Classic score advantage is confirmed again

## Risk investigated

Whether `jdw.Hornet_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `651391483a93e46fa71150aeb678d8d4737532c440a5375f86d0c7cc5805d305`. The official five-pair confirmation `926d9ddd20d1fece` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `580260f11da9c63e1702b10ea979aa94bcc3a040`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,449.2 points and Tank Royale averaged 2,939.4 points, for a −45.82% mean delta. Pair deltas were −47.1%, −50.3%, −36.3%, −49.7%, and −45.7%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `f6b616930407a978` recorded Classic scores of 5,578 and Tank Royale scores of 2,811, for a −49.6% delta. The current five-pair mean confirms the same large Classic advantage at −45.82%, somewhat smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Hornet's large Classic score advantage persists under current matched artifacts. The mean delta is −45.82%, smaller than the earlier −49.6% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jdw.Hornet_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/jekl.DarkHallow_.90.9.jar` and `roborumble/jekl.Jekyl_.70.jar` (`PASS`). Record `roborumble/jekl.mini.BlackPearl_.91.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/jep.Terrible_0.4.1.jar` (`score-review`).

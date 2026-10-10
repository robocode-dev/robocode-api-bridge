---
id: AN-510
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Vincent's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-510 — Vincent's Classic score advantage is confirmed again

## Risk investigated

Whether `jeremyreeder.Vincent_2011.12.09.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `85bb1d87fa647576e9e31a0d12406762dd23b7b94a46c7c6ccf8ed7b0eece19b`. The official five-pair confirmation `d98d82188fcb5e34` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `580260f11da9c63e1702b10ea979aa94bcc3a040`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 8,768.6 points and Tank Royale averaged 5,526.8 points, for a −36.92% mean delta. Pair deltas were −31.1%, −36.6%, −41.9%, −38.4%, and −36.6%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `5937f2ce8280ffed` recorded Classic scores of 8,862 and Tank Royale scores of 5,372, for a −39.4% delta. The current five-pair mean confirms the same Classic advantage at −36.92%, somewhat smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Vincent's Classic score advantage persists under current matched artifacts. The mean delta is −36.92%, slightly smaller than the earlier −39.4% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jeremyreeder.Vincent_2011.12.09.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/jeremyreeder.collective.Prophet_5.jar` (`score-review`).

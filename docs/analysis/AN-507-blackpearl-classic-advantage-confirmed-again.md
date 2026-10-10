---
id: AN-507
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: BlackPearl's Classic score advantage is confirmed at a smaller gap
provenance: inferred
reversal-cost: low
---

# AN-507 — BlackPearl's Classic score advantage is confirmed at a smaller gap

## Risk investigated

Whether `jekl.mini.BlackPearl_.91.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `13f1937a31acf417ef27db87ed2930bf0cdc08e488bbebbbb46373e1c83bd1ef`. The official five-pair confirmation `03df9c6af2f761f1` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `580260f11da9c63e1702b10ea979aa94bcc3a040`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,989.4 points and Tank Royale averaged 2,365.2 points, for a −52.68% mean delta. Pair deltas were −35.2%, −65.4%, −50.4%, −55.4%, and −57.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `f83b97c8b3cce583` recorded Classic scores of 5,312 and Tank Royale scores of 1,750, for a −67.1% delta. The current five-pair mean confirms the same Classic advantage at −52.68%, smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

BlackPearl's Classic score advantage persists under current matched artifacts. The mean delta is −52.68%, smaller than the earlier −67.1% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jekl.mini.BlackPearl_.91.jar` as `CONFIRMED (score)`. Record `roborumble/jep.Terrible_0.4.1.jar` as `CONFIRMED (score)`. Skip `roborumble/jep.nano.Hawkwing_0.4.1.jar` and `roborumble/jep.nano.Hotspur_0.1.jar` (`PASS`). Continue in registry order with `roborumble/jeremyreeder.Bully_1.jar` (`score-review`).

---
id: AN-509
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Bully's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-509 — Bully's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `jeremyreeder.Bully_1.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `8e36fc4b0f0342c1e456edf026a6f884607e733d2362878fa34b7da13be9fa62`. The official five-pair confirmation `d01aac574464e6b4` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `580260f11da9c63e1702b10ea979aa94bcc3a040`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 10,869.8 points and Tank Royale averaged 16,581.8 points, for a +52.6% mean delta. Pair deltas were +55.0%, +52.2%, +54.1%, +51.7%, and +50.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `cfd4f9ecc5b1e9c5` recorded Classic scores of 10,630 and Tank Royale scores of 16,286, for a +53.2% delta. The current five-pair mean confirms the same Tank Royale advantage at +52.6%, almost unchanged from the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Bully's Tank Royale score advantage persists under current matched artifacts. The mean delta is +52.6%, close to the earlier +53.2% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jeremyreeder.Bully_1.jar` as `CONFIRMED (score)`. Record `roborumble/jeremyreeder.Vincent_2011.12.09.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/jeremyreeder.collective.Prophet_5.jar` (`score-review`).

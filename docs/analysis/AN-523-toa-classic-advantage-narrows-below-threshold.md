---
id: AN-523
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Kid.Toa's Classic score advantage narrows below the threshold
provenance: inferred
reversal-cost: low
---

# AN-523 — Kid.Toa's Classic score advantage narrows below the threshold

## Risk investigated

Whether `kid.Toa_.0.5.jar`'s historical Classic score advantage persists under current matched artifacts, and whether it remains above the recorded threshold.

## Evidence boundary

The read-only subject jar has SHA-256 `50de2526df0b43e1b883bb9c4cb0ea18a7dae5689dc44745c2e2c64082f6e711`. The official five-pair confirmation `499c535ef9f66477` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `da338c14f624a1ffbb6b41d700617c7c7a113576`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,445.4 points and Tank Royale averaged 3,600 points, for a −18.06% mean delta. Pair deltas were −10.2%, −22.8%, −1.7%, +2.2%, and −57.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured in all five Tank Royale attempts. Attempt 5 recorded a skipped-turn event for bot 2 at round 4, turn 575 and bot 1 at round 4, turn 699; the other attempts recorded none. The registry status is `CONFIRMED (score)`; the mean gap is below the recorded 25% threshold.

## What was tried

The earlier observation `fba256d882f5f68e` recorded Classic scores of 4,769 and Tank Royale scores of 1,957, for a −59.0% delta. The current five-pair mean confirms a Classic advantage at −18.06%, a much smaller gap below the recorded threshold. Four current pair deltas were within 22.8% of parity, while the fifth was −57.8%.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause or the large spread between the fifth pair and the first four. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Kid.Toa's historical Classic score advantage narrows from −59.0% to a five-pair mean of −18.06%, below the recorded 25% threshold. The registry classifies the five-pair result as `CONFIRMED (score)`. The cause of the score difference and its pair-to-pair variation remain unknown.

## M-006 handoff

Record `roborumble/kid.Toa_.0.5.jar` as `CONFIRMED (score)`. Skip `roborumble/kinsen.melee.Angsaichmophobia_1.8c.jar` (`PASS`). Record `roborumble/kinsen.nano.Charp_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/kinsen.nano.Hoplomachy_1.6.jar` (`PASS`). Continue with `roborumble/kinsen.nano.Quarrelet_1.0.jar` (`score-review`).

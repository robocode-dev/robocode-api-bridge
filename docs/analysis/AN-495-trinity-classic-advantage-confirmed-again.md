---
id: AN-495
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Trinity's large Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-495 — Trinity's large Classic score advantage is confirmed again

## Risk investigated

Whether `hirataatsushi.Trinity_0.003.jar`'s historical large Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `5b6d12ac830c29781d82ac489b1997427fe6b9ae1601c4dc1b22134ef4a6dbb2`. The official five-pair confirmation `f47bb42d3849a190` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea7f653a103424543763ad493b0b24ed9e940409`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 10,320.4 points and Tank Royale averaged 4,002.2 points, for a −61.18% mean delta. Pair deltas were −63.8%, −59.0%, −59.3%, −63.9%, and −59.9%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `acff8e25afd66c29` recorded Classic scores of 10,388 and Tank Royale scores of 4,365, for a −58.0% delta. The current five-pair mean remains a large Classic advantage at −61.18%; the registry confirms the score difference across all five pairs.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Trinity's large Classic score advantage persists under current matched artifacts. The mean delta is −61.18%, slightly larger than the earlier −58.0% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Skip `roborumble/hirataatsushi.Neo_1.6.jar` (`PASS`). Record `roborumble/hirataatsushi.Trinity_0.003.jar` as `CONFIRMED (score)`. Record `roborumble/hlavko.micro.Flex_1.5.jar` as `DISCREPANCY (errors)`. Skip `roborumble/hlavko.nano.Phoenix_1.0.jar` and `roborumble/hlavko.nano.Ringo_1.0d.jar` (`PASS`). Continue in registry order with `roborumble/hlavko.nano.Ringo_2.0.jar` (`score-review`).

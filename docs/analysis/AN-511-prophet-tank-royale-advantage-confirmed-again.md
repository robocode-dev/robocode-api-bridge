---
id: AN-511
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Prophet's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-511 — Prophet's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `jeremyreeder.collective.Prophet_5.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `4d0665640244ddceffa1e1c37dea5698c3496329819ee85319a35a4329613387`. The official five-pair confirmation `ba6b774a264b4ef9` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `12002b76dec90190d7a49f885f95db3621982767`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,374.2 points and Tank Royale averaged 8,046.8 points, for a +50.2% mean delta. Pair deltas were +62.5%, +42.1%, +43.9%, +65.2%, and +37.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `184353f9c23e961e` recorded Classic scores of 4,818 and Tank Royale scores of 7,360, for a +52.8% delta. The current five-pair mean confirms the same Tank Royale advantage at +50.2%, slightly smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Prophet's Tank Royale score advantage persists under current matched artifacts. The mean delta is +50.2%, close to the earlier +52.8% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jeremyreeder.collective.Prophet_5.jar` as `CONFIRMED (score)`. Record `roborumble/jf.Dodger_1.3.jar` as `MATCHED (score noise)`. Skip `roborumble/jgap.JGAP12584_1.0.jar` and `roborumble/jgap.JGAP130166_1.0.jar` (`PASS`). Continue in registry order with `roborumble/jgap.JGAP23423_1.0.jar` (`score-review`).

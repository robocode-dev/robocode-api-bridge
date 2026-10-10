---
id: AN-515
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: JGAP7958's Classic score advantage is confirmed at a smaller gap
provenance: inferred
reversal-cost: low
---

# AN-515 — JGAP7958's Classic score advantage is confirmed at a smaller gap

## Risk investigated

Whether `jgap.JGAP7958_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `fd5b10d89d3f4e9f206018a23e65a3a64e433c62b78918271977915294d276e1`. The official five-pair confirmation `803007ad223f04a3` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `12002b76dec90190d7a49f885f95db3621982767`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 3,794.8 points and Tank Royale averaged 2,267 points, for a −40.14% mean delta. Pair deltas were −33.9%, −39.5%, −43.0%, −41.5%, and −42.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `68d33e4907c12469` recorded Classic scores of 4,169 and Tank Royale scores of 2,236, for a −46.4% delta. The current five-pair mean confirms the same Classic advantage at −40.14%, smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

JGAP7958's Classic score advantage persists under current matched artifacts. The mean delta is −40.14%, smaller than the earlier −46.4% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jgap.JGAP7958_1.0.jar` as `CONFIRMED (score)`. Record `roborumble/jje.BagPuss_1.2.jar` as `MATCHED (score noise)`. Skip `roborumble/jk.mega.DrussGT_3.1.7.jar`, `roborumble/jk.melee.Neuromancer_7.12.jar`, `roborumble/jk.micro.Cotillion_0.8.jar`, `roborumble/jk.mini.CunobelinDC_1.2.jar`, `roborumble/jk.nano.Machete_2.0.jar`, `roborumble/jk.precise.EnergyDome_1.6.jar`, `roborumble/jk.precise.Wintermute_0.8.jar`, `roborumble/jk.sheldor.nano.Yatagan_1.2.3.jar`, `roborumble/jmcd.BeoWulf_2.8.jar`, `roborumble/joe.ADinosaur_1.0.jar`, `roborumble/josago.Jorgito_0.16.jar`, `roborumble/jp.Perpy_16.0.jar`, and `roborumble/jp.SineWall_1.0.jar` (`PASS`). Continue in registry order with `roborumble/jrm.Test0_1.0.jar` (`score-review`).

---
id: AN-516
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: BagPuss's earlier score difference is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-516 — BagPuss's earlier score difference is not reproduced

## Risk investigated

Whether `jje.BagPuss_1.2.jar`'s historical Classic score advantage persists under current matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `5c7dfd94b4430cc03c9c794ca0b3db992f74daaa44604c516c7033aa76ee3a3a`. The official five-pair confirmation `f5d91ece533643ae` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aeb1b655a4c46662ad3425f01749e269e87d3710`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 8,311 points and Tank Royale averaged 7,585.4 points, for a −8.64% mean delta. Pair deltas were −9.5%, −4.9%, −13.2%, −14.9%, and −0.7%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `48caa85e1e1f702b` recorded Classic scores of 7,554 and Tank Royale scores of 5,226, for a −30.8% delta. The current five-pair mean is −8.64%, and the registry classifies the result as `MATCHED (score noise)`; the earlier score difference was not reproduced at the same magnitude.

## What was not pursued

The current run resolves the score-review outcome but does not explain why the earlier score gap appeared. No controlled trace or source comparison was made, and the earlier gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

BagPuss's earlier Classic advantage is not reproduced at its former magnitude under current matched artifacts. The five-pair mean is −8.64%, and the registry now classifies the outcome as `MATCHED (score noise)`.

## M-006 handoff

Record `roborumble/jje.BagPuss_1.2.jar` as `MATCHED (score noise)`. Skip `roborumble/jk.mega.DrussGT_3.1.7.jar`, `roborumble/jk.melee.Neuromancer_7.12.jar`, `roborumble/jk.micro.Cotillion_0.8.jar`, `roborumble/jk.mini.CunobelinDC_1.2.jar`, `roborumble/jk.nano.Machete_2.0.jar`, `roborumble/jk.precise.EnergyDome_1.6.jar`, `roborumble/jk.precise.Wintermute_0.8.jar`, `roborumble/jk.sheldor.nano.Yatagan_1.2.3.jar`, `roborumble/jmcd.BeoWulf_2.8.jar`, `roborumble/joe.ADinosaur_1.0.jar`, `roborumble/josago.Jorgito_0.16.jar`, `roborumble/jp.Perpy_16.0.jar`, and `roborumble/jp.SineWall_1.0.jar` (`PASS`). Record `roborumble/jrm.Test0_1.0.jar` as `CONFIRMED (score)`. The intervening `roborumble/js.PinBall_1.6.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Skip `roborumble/jsal.Jsalbot_1.0.jar` (`PASS`). Continue with `roborumble/jt.SpearmintCT_Alpha.jar` (`score-review`).

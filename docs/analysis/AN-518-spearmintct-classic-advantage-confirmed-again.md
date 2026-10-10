---
id: AN-518
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Spearmint CT's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-518 — Spearmint CT's Classic score advantage is confirmed again

## Risk investigated

Whether `jt.SpearmintCT_Alpha.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `0e7e0319dd336ee4491377653295ec98345e2eb8c065c80c47e57d0a1c287a57`. The official five-pair confirmation `8d3ce88b96df7424` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aeb1b655a4c46662ad3425f01749e269e87d3710`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,992 points and Tank Royale averaged 3,664.8 points, for a −47.54% mean delta. Pair deltas were −47.5%, −44.2%, −49.3%, −44.5%, and −52.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `e663bf152188ba08` recorded Classic scores of 6,596 and Tank Royale scores of 3,611, for a −45.3% delta. The current five-pair mean confirms the same Classic advantage at −47.54%, slightly larger than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Spearmint CT's Classic score advantage persists under current matched artifacts. The mean delta is −47.54%, close to the earlier −45.3% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jt.SpearmintCT_Alpha.jar` as `CONFIRMED (score)`. The intervening `roborumble/justin.DemonicRage_3.20.jar` and `roborumble/jw.Booring_1.11.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Skip `roborumble/jwst.DAD.DarkAndDarker_1.1.jar`, `roborumble/kanishk.Fr0z3n_1.1.jar`, `roborumble/kano.gamma.KanoGamma_1.8.jar`, `roborumble/kawam.kmBot9_1.0.jar`, and `roborumble/kawigi.f.FhqwhgadsMicro_1.0.jar` (`PASS`). Record `roborumble/kawigi.micro.Shiz_1.1.jar` as `CONFIRMED (score)`. The intervening `roborumble/kawigi.mini.Coriantumr_1.1.jar` and `roborumble/kawigi.mini.Fhqwhgads_1.1.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Skip `roborumble/kawigi.nano.FunkyChicken_1.1.jar`, `roborumble/kawigi.nano.ThnikkaBot_0.9.jar`, `roborumble/kawigi.robot.Girl_1.2.jar`, `roborumble/kawigi.sbf.Barracuda_1.0.jar`, and `roborumble/kawigi.sbf.FloodHT_0.9.2.jar` (`PASS`). Continue with `roborumble/kawigi.sbf.FloodMicro_1.5.jar` (`score-review`).

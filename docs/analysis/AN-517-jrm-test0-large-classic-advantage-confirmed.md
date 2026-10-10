---
id: AN-517
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: JRM Test0's very large Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-517 — JRM Test0's very large Classic score advantage is confirmed again

## Risk investigated

Whether `jrm.Test0_1.0.jar`'s historical very large Classic score advantage persists under current matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `04af9455c981df31eb0e00685f0695394231dcf4943206462d32eb727e683f74`. The official five-pair confirmation `8781ff243de2d4d2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aeb1b655a4c46662ad3425f01749e269e87d3710`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,651 points and Tank Royale averaged 324.8 points, for a −95.14% mean delta. Pair deltas were −93.6%, −94.9%, −94.6%, −96.3%, and −96.3%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 1 recorded events for both bots at round 1, turns 12 and 13, attempt 2 recorded events for both bots at round 5, turn 600, and attempts 3–5 recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `87280748930f85ca` recorded Classic scores of 6,549 and Tank Royale scores of 360, for a −94.5% delta. The current five-pair mean confirms the same exceptionally large Classic advantage at −95.14%.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

JRM Test0's exceptionally large Classic score advantage persists under current matched artifacts. The mean delta is −95.14%, close to the earlier −94.5% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jrm.Test0_1.0.jar` as `CONFIRMED (score)`. The intervening `roborumble/js.PinBall_1.6.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Skip `roborumble/jsal.Jsalbot_1.0.jar` (`PASS`). Record `roborumble/jt.SpearmintCT_Alpha.jar` as `CONFIRMED (score)`. The intervening `roborumble/justin.DemonicRage_3.20.jar` and `roborumble/jw.Booring_1.11.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Skip `roborumble/jwst.DAD.DarkAndDarker_1.1.jar`, `roborumble/kanishk.Fr0z3n_1.1.jar`, `roborumble/kano.gamma.KanoGamma_1.8.jar`, `roborumble/kawam.kmBot9_1.0.jar`, and `roborumble/kawigi.f.FhqwhgadsMicro_1.0.jar` (`PASS`). Continue with `roborumble/kawigi.micro.Shiz_1.1.jar` (`score-review`).

---
id: AN-500
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: MobyNano's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-500 — MobyNano's Classic score advantage is confirmed again

## Risk investigated

Whether `ins.MobyNano_0.8.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `44edef1a56ef812ee1a7adddae4bcde2ad90350b466e72976fa8ba6acdf7e09f`. The official five-pair confirmation `7f494fe14c54f109` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0e4ae538692b96d2afe7a8480a00193496c7c0c1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,559 points and Tank Royale averaged 2,965 points, for a −34.9% mean delta. Pair deltas were −31.6%, −35.6%, −39.5%, −31.4%, and −36.4%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `41a4925ee8539ff3` recorded Classic scores of 4,443 and Tank Royale scores of 3,016, for a −32.1% delta. The current five-pair mean confirms the Classic advantage at −34.9%, somewhat larger than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

MobyNano's Classic score advantage persists under current matched artifacts. The mean delta is −34.9%, larger than the earlier −32.1% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/ins.MobyNano_0.8.jar` as `CONFIRMED (score)`. Skip `roborumble/intruder.PrairieWolf_2.61.jar`, `roborumble/is.fon.rs.FonDestroyer3084_1.0.jar`, `roborumble/is.fon.rs.Kamikaza_1.0.jar`, and `roborumble/jab.DiamondStealer_5.jar` (`PASS`). The intervening `roborumble/jaara.LambdaBot_1.1.jar` and `roborumble/jab.avk.ManuelGallegus_0.6.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Record `roborumble/jab.micro.Sanguijuela_0.8.jar` as `CONFIRMED (score)`. Skip `roborumble/jaemcrb.nano.M1Abrams_1.0.jar`, `roborumble/jam.RaikoMX_0.32.jar`, `roborumble/jam.micro.RaikoMicro_1.44.jar`, and `roborumble/janm.Jammy_1.0.jar` (`PASS`). Continue with `roborumble/jam.mini.Raiko_0.43.jar` (`score-review`).

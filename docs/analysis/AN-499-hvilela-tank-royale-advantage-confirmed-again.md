---
id: AN-499
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: HVilela's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-499 — HVilela's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `hvilela.HVilela_0.9.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `819a94baaf051b689da9226a4939da6c14cff0d61d882cb86c6e5a8b4d9b1830`. The official five-pair confirmation `ed26b8f3d91a78f2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0e4ae538692b96d2afe7a8480a00193496c7c0c1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,847.4 points and Tank Royale averaged 10,805.6 points, for a +57.98% mean delta. Pair deltas were +58.5%, +66.7%, +61.7%, +53.8%, and +49.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `5e7d6ba86fc06b0e` recorded Classic scores of 6,525 and Tank Royale scores of 10,654, for a +63.3% delta. The current five-pair mean confirms the Tank Royale advantage at +57.98%, somewhat smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

HVilela's Tank Royale score advantage persists under current matched artifacts. The mean delta is +57.98%, smaller than the earlier +63.3% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/hvilela.HVilela_0.9.jar` as `CONFIRMED (score)`. Skip `roborumble/infovk.s_schwarzm16.silverbird_1.0.jar` (`PASS`). Record `roborumble/ins.MobyNano_0.8.jar` as `CONFIRMED (score)`. Skip `roborumble/intruder.PrairieWolf_2.61.jar`, `roborumble/is.fon.rs.FonDestroyer3084_1.0.jar`, `roborumble/is.fon.rs.Kamikaza_1.0.jar`, `roborumble/jab.DiamondStealer_5.jar`, `roborumble/jam.RaikoMX_0.32.jar`, and `roborumble/jam.micro.RaikoMicro_1.44.jar` (`PASS`). The intervening `roborumble/jaara.LambdaBot_1.1.jar` and `roborumble/jab.avk.ManuelGallegus_0.6.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Continue with `roborumble/jab.micro.Sanguijuela_0.8.jar` (`score-review`).

---
id: AN-519
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Shiz's Classic score advantage is confirmed at a smaller gap
provenance: inferred
reversal-cost: low
---

# AN-519 — Shiz's Classic score advantage is confirmed at a smaller gap

## Risk investigated

Whether `kawigi.micro.Shiz_1.1.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `c8f4d694bfc08c8f1634f3cc60efb7e5083163892447d824ac7873f1368823f0`. The official five-pair confirmation `6b91a284f4e4ecf7` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aeb1b655a4c46662ad3425f01749e269e87d3710`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,775 points and Tank Royale averaged 2,979.2 points, for a −37.54% mean delta. Pair deltas were −35.1%, −41.2%, −36.4%, −36.5%, and −38.5%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 1 recorded events for both bots at round 15, turn 597, plus bot 2 at turn 598 and bot 1 at turn 605, and bot 2 at turn 606; attempt 2 recorded bot 1 at round 4, turn 167, and both bots at turn 584. Attempts 3–5 recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `e2df7a9d6c23797e` recorded Classic scores of 5,231 and Tank Royale scores of 2,933, for a −43.9% delta. The current five-pair mean confirms the same Classic advantage at −37.54%, smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Shiz's Classic score advantage persists under current matched artifacts. The mean delta is −37.54%, smaller than the earlier −43.9% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/kawigi.micro.Shiz_1.1.jar` as `CONFIRMED (score)`. The intervening `roborumble/kawigi.mini.Coriantumr_1.1.jar` and `roborumble/kawigi.mini.Fhqwhgads_1.1.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Skip `roborumble/kawigi.nano.FunkyChicken_1.1.jar`, `roborumble/kawigi.nano.ThnikkaBot_0.9.jar`, `roborumble/kawigi.robot.Girl_1.2.jar`, `roborumble/kawigi.sbf.Barracuda_1.0.jar`, and `roborumble/kawigi.sbf.FloodHT_0.9.2.jar` (`PASS`). Record `roborumble/kawigi.sbf.FloodMicro_1.5.jar` as `CONFIRMED (score)`. Skip `roborumble/kawigi.sbf.FloodMini_1.4.jar` (`PASS`). Continue with `roborumble/kawigi.sbf.FloodNano_1.2.jar` (`score-review`).

---
id: AN-502
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Raiko's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-502 — Raiko's Classic score advantage is confirmed again

## Risk investigated

Whether `jam.mini.Raiko_0.43.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `48479846001b8805b1915bae68686f2ef8455248deac87d1c31ca6593a74d086`. The official five-pair confirmation `052af9dcebe8e7e9` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea18af69323cf197eb1bd46489e6cf7296f5d3f8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,815.2 points and Tank Royale averaged 1,950 points, for a −59.34% mean delta. Pair deltas were −60.8%, −57.8%, −63.2%, −54.2%, and −60.7%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 2 recorded one skipped-turn event for each bot at round 9, turn 472, and the other attempts recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `6e101daed7168592` recorded Classic scores of 5,125 and Tank Royale scores of 2,090, for a −59.2% delta. The current five-pair mean confirms the same large Classic advantage at −59.34%.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Raiko's large Classic score advantage persists under current matched artifacts. The mean delta is −59.34%, close to the earlier −59.2% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jam.mini.Raiko_0.43.jar` as `CONFIRMED (score)`. Skip `roborumble/janm.Jammy_1.0.jar` (`PASS`). Record `roborumble/japs.Serenity_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/japs.Sjonniebot_0.9.1.jar`, `roborumble/jasolo.Sonda_0.55.jar`, `roborumble/jaw.KarenCain_0.11.jar`, `roborumble/jaw.Mouse_0.11.jar`, `roborumble/jaybot.adv.bots.JayBot_2.0.jar`, and `roborumble/jbot.Rabbit2_1.1.jar` (`PASS`). The intervening `roborumble/jaybot.bots.Oddball_4.0.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Continue with `roborumble/jcs.AutoBot_4.2.1.jar` (`score-review`).

---
id: AN-521
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: FloodNano's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-521 — FloodNano's Classic score advantage is confirmed again

## Risk investigated

Whether `kawigi.sbf.FloodNano_1.2.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `6472039bac12030822589dd0dc4e0f2a2d657bc8cefdaec85c2b2142e6c638bb`. The official five-pair confirmation `77d03b46f1a6dbc2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `da338c14f624a1ffbb6b41d700617c7c7a113576`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,610.2 points and Tank Royale averaged 2,910.8 points, for a −36.84% mean delta. Pair deltas were −35.0%, −43.4%, −34.5%, −35.4%, and −35.9%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `117a6d40be35854c` recorded Classic scores of 4,557 and Tank Royale scores of 2,814, for a −38.2% delta. The current five-pair mean confirms the same Classic advantage at −36.84%, close to the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

FloodNano's Classic score advantage persists under current matched artifacts. The mean delta is −36.84%, close to the earlier −38.2% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/kawigi.sbf.FloodNano_1.2.jar` as `CONFIRMED (score)`. The intervening `roborumble/kawigi.sbf.FloodSonnet_0.9.jar`, `roborumble/kc.mega.BeepBoop_1.21.jar`, and `roborumble/kc.micro.WaveShark_0.4.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Skip `roborumble/kawigi.sbf.Teancum_1.3.jar`, `roborumble/kawigi.spare.SpareParts_0.7.6nosnd.jar`, `roborumble/kc.micro.Needle_0.101.jar`, and `roborumble/kc.micro.Thorn_1.252.jar` (`PASS`). Record `roborumble/kc.micro.rammer.MaxRisk_0.6.jar` as `CONFIRMED (score)`. Skip `roborumble/kc.mini.Vyper_0.311.jar`, `roborumble/kc.nano.Splinter_1.2.jar`, `roborumble/kc.serpent.Hydra_0.21.jar`, `roborumble/kc.serpent.WaveSerpent_2.11.jar`, `roborumble/kcn.percept.PerceptBot_2.3.jar`, `roborumble/kcn.unnamed.Unnamed_1.21.jar`, `roborumble/kenran.Bakko_v1.0.1.jar`, `roborumble/kenran.mega.Pantheist_1.1.jar`, and `roborumble/kid.Gladiator_.7.2.jar` (`PASS`). Continue with `roborumble/kid.Toa_.0.5.jar` (`score-review`).

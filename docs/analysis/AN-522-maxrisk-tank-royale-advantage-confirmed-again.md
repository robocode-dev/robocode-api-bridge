---
id: AN-522
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: MaxRisk's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-522 — MaxRisk's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `kc.micro.rammer.MaxRisk_0.6.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `5e3f9d6a66e4741fea1c0c26ee14e1bd7aa99bacc7bcb2a73a9b10d41df384fa`. The official five-pair confirmation `54e43201c25fe3cf` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `da338c14f624a1ffbb6b41d700617c7c7a113576`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 10,537.8 points and Tank Royale averaged 16,743.4 points, for a +58.92% mean delta. Pair deltas were +61.4%, +59.0%, +58.0%, +54.6%, and +61.6%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `1d7f127258dd610c` recorded Classic scores of 10,431 and Tank Royale scores of 17,011, for a +63.1% delta. The current five-pair mean confirms the same Tank Royale advantage at +58.92%, somewhat smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

MaxRisk's Tank Royale score advantage persists under current matched artifacts. The mean delta is +58.92%, smaller than the earlier +63.1% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/kc.micro.rammer.MaxRisk_0.6.jar` as `CONFIRMED (score)`. Skip `roborumble/kc.mini.Vyper_0.311.jar`, `roborumble/kc.nano.Splinter_1.2.jar`, `roborumble/kc.serpent.Hydra_0.21.jar`, `roborumble/kc.serpent.WaveSerpent_2.11.jar`, `roborumble/kcn.percept.PerceptBot_2.3.jar`, `roborumble/kcn.unnamed.Unnamed_1.21.jar`, `roborumble/kenran.Bakko_v1.0.1.jar`, `roborumble/kenran.mega.Pantheist_1.1.jar`, and `roborumble/kid.Gladiator_.7.2.jar` (`PASS`). Record `roborumble/kid.Toa_.0.5.jar` as `CONFIRMED (score)`. Skip `roborumble/kinsen.melee.Angsaichmophobia_1.8c.jar` (`PASS`). Continue with `roborumble/kinsen.nano.Charp_1.0.jar` (`score-review`).

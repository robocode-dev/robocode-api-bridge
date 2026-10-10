---
id: AN-503
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Serenity's Classic score advantage persists at a narrower gap
provenance: inferred
reversal-cost: low
---

# AN-503 — Serenity's Classic score advantage persists at a narrower gap

## Risk investigated

Whether `japs.Serenity_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `e2fba2dc830ba402135252034f90e6967f89c16a5792edd949ddf6255382e72d`. The official five-pair confirmation `bd7cdf86440fd5c9` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea18af69323cf197eb1bd46489e6cf7296f5d3f8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 8,997.8 points and Tank Royale averaged 6,788.4 points, for a −24.52% mean delta. Pair deltas were −20.8%, −16.7%, −33.0%, −27.5%, and −24.6%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`; the mean gap is slightly below the recorded 25% threshold.

## What was tried

The earlier observation `f3a42e32015e55aa` recorded Classic scores of 9,239 and Tank Royale scores of 5,561, for a −39.8% delta. The current five-pair mean confirms the same Classic advantage at −24.52%, a substantially narrower gap that falls just below the recorded threshold.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Serenity's Classic score advantage persists under current matched artifacts, but the mean gap narrowed from −39.8% to −24.52%. The registry classifies the repeated score difference as `CONFIRMED (score)` even though the current mean is slightly below the recorded 25% threshold. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/japs.Serenity_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/japs.Sjonniebot_0.9.1.jar`, `roborumble/jasolo.Sonda_0.55.jar`, `roborumble/jaw.KarenCain_0.11.jar`, `roborumble/jaw.Mouse_0.11.jar`, `roborumble/jaybot.adv.bots.JayBot_2.0.jar`, and `roborumble/jbot.Rabbit2_1.1.jar` (`PASS`). The intervening `roborumble/jaybot.bots.Oddball_4.0.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Record `roborumble/jcs.AutoBot_4.2.1.jar` as `MATCHED (score noise)`. Skip `roborumble/jcs.Decepticon_2.5.3.jar` and `roborumble/jcs.Megatron_1.2.jar` (`PASS`). The intervening `roborumble/jcs.Seth_1.8.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Continue with `roborumble/jcw.ArcherOne_1.0.jar` (`score-review`).

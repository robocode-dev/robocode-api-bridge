---
id: AN-520
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: FloodMicro's Classic score gap narrows below the threshold
provenance: inferred
reversal-cost: low
---

# AN-520 — FloodMicro's Classic score gap narrows below the threshold

## Risk investigated

Whether `kawigi.sbf.FloodMicro_1.5.jar`'s historical Classic score advantage persists under current matched artifacts, and whether it remains above the recorded threshold.

## Evidence boundary

The read-only subject jar has SHA-256 `4f3549b99f11f1899b90e2d74460d245f3a79078444470824ecc03b5565900a8`. The official five-pair confirmation `9ecc1bff459df2f2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aeb1b655a4c46662ad3425f01749e269e87d3710`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,191.8 points and Tank Royale averaged 4,194.6 points, for a −19.1% mean delta. Pair deltas were −12.6%, −17.9%, −19.8%, −25.0%, and −20.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`; the mean gap is below the recorded 25% threshold.

## What was tried

The earlier observation `c9c40370dcf10011` recorded Classic scores of 5,129 and Tank Royale scores of 3,765, for a −26.6% delta. The current five-pair mean confirms a Classic advantage at −19.1%, smaller than the earlier gap and below the recorded threshold.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

FloodMicro's Classic advantage persists under current matched artifacts, but its mean gap narrowed from −26.6% to −19.1%, below the recorded 25% threshold. The registry classifies the five-pair result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/kawigi.sbf.FloodMicro_1.5.jar` as `CONFIRMED (score)`. Skip `roborumble/kawigi.sbf.FloodMini_1.4.jar` (`PASS`). Record `roborumble/kawigi.sbf.FloodNano_1.2.jar` as `CONFIRMED (score)`. The intervening `roborumble/kawigi.sbf.FloodSonnet_0.9.jar`, `roborumble/kc.mega.BeepBoop_1.21.jar`, and `roborumble/kc.micro.WaveShark_0.4.jar` remain `DISCREPANCY (outcome)` and are outside this score-review sequence. Skip `roborumble/kawigi.sbf.Teancum_1.3.jar`, `roborumble/kawigi.spare.SpareParts_0.7.6nosnd.jar`, `roborumble/kc.micro.Needle_0.101.jar`, and `roborumble/kc.micro.Thorn_1.252.jar` (`PASS`). Continue with `roborumble/kc.micro.rammer.MaxRisk_0.6.jar` (`score-review`).

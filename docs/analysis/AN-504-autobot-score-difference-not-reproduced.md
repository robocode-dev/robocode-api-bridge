---
id: AN-504
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: AutoBot's earlier score difference is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-504 — AutoBot's earlier score difference is not reproduced

## Risk investigated

Whether `jcs.AutoBot_4.2.1.jar`'s historical score discrepancy persists under current matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `21598f5d66d81b55f831aab11b1639432ba24a42605f55b3c68a5cf148977841`. The official five-pair confirmation `5ee2e2bab0ec6d12` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea18af69323cf197eb1bd46489e6cf7296f5d3f8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,651.6 points and Tank Royale averaged 6,727 points, for a +1.8% mean delta. Pair deltas were +2.5%, −15.5%, −9.0%, +6.5%, and +24.5%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `d52f242b7cf667d0` recorded Classic scores of 6,295 and Tank Royale scores of 3,924, for a −37.7% delta. The current five-pair mean is +1.8%, and the registry classifies the result as `MATCHED (score noise)`; the earlier score difference was not reproduced.

## What was not pursued

The current run resolves the score-review outcome but does not explain why the earlier score gap appeared. No controlled trace or source comparison was made, and the earlier gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

AutoBot's earlier 37.7% Classic advantage is not reproduced under current matched artifacts. The five-pair mean is +1.8%, with mixed pair directions, and the registry now classifies the outcome as `MATCHED (score noise)`.

## M-006 handoff

Record `roborumble/jcs.AutoBot_4.2.1.jar` as `MATCHED (score noise)`. Skip `roborumble/jcs.Decepticon_2.5.3.jar` and `roborumble/jcs.Megatron_1.2.jar` (`PASS`). The intervening `roborumble/jcs.Seth_1.8.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Record `roborumble/jcw.ArcherOne_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/jcz.linio.Linio_2.0.H.jar` (`PASS`). Continue in registry order with `roborumble/jdw.Hornet_1.0.jar` (`score-review`).

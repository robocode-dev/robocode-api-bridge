---
id: AN-512
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Dodger's earlier score difference is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-512 — Dodger's earlier score difference is not reproduced

## Risk investigated

Whether `jf.Dodger_1.3.jar`'s historical Classic score advantage persists under current matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `e51914d777c827045d3f415975ec1908f67f5f95cfd4f434430f6bec99cdd660`. The official five-pair confirmation `1e77bf3c9fd5c9f9` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `12002b76dec90190d7a49f885f95db3621982767`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 7,349.8 points and Tank Royale averaged 6,340.2 points, for a −13.4% mean delta. Pair deltas were −24.0%, −15.4%, −7.4%, −11.9%, and −8.3%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `92b831520dd0868f` recorded Classic scores of 7,769 and Tank Royale scores of 5,588, for a −28.1% delta. The current five-pair mean is −13.4%, and the registry classifies the result as `MATCHED (score noise)`; the earlier score difference is not reproduced at the same magnitude.

## What was not pursued

The current run resolves the score-review outcome but does not explain why the earlier score gap appeared. No controlled trace or source comparison was made, and the earlier gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Dodger's earlier Classic advantage is not reproduced at its former magnitude under current matched artifacts. The five-pair mean is −13.4%, and the registry now classifies the outcome as `MATCHED (score noise)`.

## M-006 handoff

Record `roborumble/jf.Dodger_1.3.jar` as `MATCHED (score noise)`. Skip `roborumble/jgap.JGAP12584_1.0.jar` and `roborumble/jgap.JGAP130166_1.0.jar` (`PASS`). Record `roborumble/jgap.JGAP23423_1.0.jar` as `CONFIRMED (score)`. Continue in registry order with `roborumble/jgap.JGAP6139_1.0.jar` (`score-review`).

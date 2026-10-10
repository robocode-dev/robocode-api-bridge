---
id: AN-491
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: T2's earlier Classic zero score is replaced by a confirmed Classic advantage
provenance: inferred
reversal-cost: low
---

# AN-491 — T2's earlier Classic zero score is replaced by a confirmed Classic advantage

## Risk investigated

Whether `ha2.T2_0.2.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether the current five-pair run confirms a score difference.

## Evidence boundary

The read-only subject jar has SHA-256 `b78c42b9d141f769d3b31180fad786e32627175d114b87bc12d223eb7734dcb9`. The official five-pair confirmation `301d91ff252d7fe7` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea7f653a103424543763ad493b0b24ed9e940409`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 6,074.0 points and Tank Royale averaged 4,920.8 points, for a −18.74% mean delta. Pair deltas were −12.9%, −10.4%, −24.0%, −29.6%, and −16.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `1bece4ef2e0c4719` recorded Classic scores of 0 and 0 and Tank Royale scores of 2,866 and 2,066, with no runtime errors. The current run produced scores from both engines in every attempt. Its −18.74% mean delta confirms a Classic score advantage under the current matched artifacts.

## What was not pursued

The current run does not explain why the earlier Classic result was zero or what caused the score gap. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

T2's earlier Classic zero-score outcome did not recur. The current five pairs all produced scores and confirmed a −18.74% mean delta in Classic's favor; the registry classifies it as `CONFIRMED (score)`. The earlier zero-score cause and the score gap's cause remain unknown.

## M-006 handoff

Record `roborumble/ha2.T2_0.2.jar` as `CONFIRMED (score)`. Skip `roborumble/ha2.T2b_0.2b.jar` (`MATCHED (score noise)`) and `roborumble/ha2.T3_0.1.jar` (`PASS`). Continue in registry order with `roborumble/ha2.T3_0.2.jar` (`score-review`).

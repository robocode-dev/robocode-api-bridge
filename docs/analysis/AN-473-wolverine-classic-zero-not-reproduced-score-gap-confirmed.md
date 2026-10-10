---
id: AN-473
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Wolverine's Classic zero score does not recur and its Tank Royale advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-473 — Wolverine's Classic zero score does not recur and its Tank Royale advantage is confirmed

## Risk investigated

Whether `gg.Wolverine_2.0.jar`'s historical Classic zero-score result persists under current matched artifacts, and whether the large Tank Royale score advantage recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `74759ddb81c6551707d86946b407ce387fc03f44660e0f46a05d2ed0d8ebbd2d`. The official five-pair confirmation `0532eb6413763975` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `52a2e464caed1629dedc59319f42ab7686fab425`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,255.4 points and Tank Royale averaged 12,940.8 points, for a +146.32% mean delta. Pair deltas were +150.9%, +136.8%, +141.0%, +147.9%, and +155.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `5a1633d8ea1a8831` recorded Classic scores of 0 and 0 and Tank Royale scores of 6,506 and 6,432, with no runtime errors. The current run produced scores from both engines in every attempt. The current Tank Royale mean of 12,940.8 is close to the earlier aggregate score of 12,938, while current Classic averaged 5,255.4 instead of zero; the registry confirms the large Tank Royale score advantage across all five pairs.

## What was not pursued

The current run does not explain why the earlier Classic result was zero or what causes the large score gap. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Wolverine's earlier Classic zero-score outcome did not recur. The current five pairs confirm a +146.32% mean Tank Royale score advantage, and the registry classifies it as `CONFIRMED (score)`. The earlier zero-score cause and the score gap's cause remain unknown.

## M-006 handoff

Record `roborumble/gg.Wolverine_2.0.jar` as `CONFIRMED (score)`. Skip `roborumble/gh.GresSuffurd_0.4.13.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gh.GrypRepetyf_0.13.jar` (`DISCREPANCY (no score)`).

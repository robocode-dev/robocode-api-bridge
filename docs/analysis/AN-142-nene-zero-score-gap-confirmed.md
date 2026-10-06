---
id: AN-142
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Nene's all-five Tank Royale zero-score gap is confirmed
provenance: inferred
reversal-cost: low
---

# AN-142 — Nene's all-five Tank Royale zero-score gap is confirmed

## Risk investigated

Whether `cs.Nene 1.0.5`'s historical Tank Royale zero-score result persists with current matched artifacts, and whether available evidence identifies its cause.

## Evidence boundary

The read-only subject jar `cs.Nene_1.0.5.jar` has SHA-256 `9615be8f8f2bdf4f42a38f74b4b81d0944e86f06311582915a78af06d529e856`. The current official confirmation `f66575d199e45a40` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `7e29a29fdbe09376294744f259013a7639e6a7e5`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, Classic scored 4,528, 4,661, 4,761, 4,429, and 4,631 points; Tank Royale scored 0 in every pair. The mean Classic score was 4,602, the mean Tank Royale score was 0, and every delta was −100%. Neither engine reported errors. Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `77f6703bbf53891a` and `0beaabd2346d9730` also had Tank Royale scores of 0 without errors, with Classic scores of 4,672 and 4,614. Those runs used older bridge and Tank Royale artifacts.

## Finding

The Tank Royale zero-score discrepancy is repeatable across the current five-pair confirmation and the two historical observations. The bundled robot source describes a runless execution design: an always-true custom event calls `onTurnEnded()`, which calls `execute()`. The bridge maps legacy custom events and `execute()` to Tank Royale custom-event dispatch and `bot.go()`. The source also updates `lastScan` before calculating elapsed time since the prior scan, making that calculation zero at that location. These are robot-side behavior leads; the available runs do not establish why Tank Royale scores zero, so cause remains unassigned. No bridge code or rumble jar change is indicated by this observation alone.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/csp.Eagle_3.30.jar` (`score-review`).

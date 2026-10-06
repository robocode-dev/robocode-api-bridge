---
id: AN-115
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Squirrel's historical no-score outcome changes to a confirmed current score gap
provenance: inferred
reversal-cost: low
---

# AN-115 — Squirrel's historical no-score outcome changes to a confirmed current score gap

## Risk investigated

Whether bayen.nut.Squirrel's historical Tank Royale zero-score result persists with current matched artifacts, and whether the current score difference is stable across the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bayen.nut.Squirrel_1.621.jar` has SHA-256 `a5a1b82535752ee271c91cdec18f433e21dcfe1efc46ed4001bed116eeca1f76`. The current official confirmation `94e3d75e650a463c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `44e755e78113ab5d47220986aa2e1ef37075d2bb`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 5,097 in Classic and 3,702.6 in Tank Royale, a −27.48% mean delta. The five pair deltas were −21.4%, −19.3%, −11.6%, −69.1%, and −16.0%. Four pairs averaged −17.08%; the remaining pair had the unusually low Tank Royale score of 1,545. All five score pairs completed without errors or bridge-only failures. Skipped-turn telemetry captured empty event lists for attempts 1–3 and was unavailable for attempts 4–5. The registry status is `CONFIRMED (score)`.

Historical observations `517b1c7cec309414` and `5853982276128593` scored zero in Tank Royale while Classic scored 5,175 and 5,361, respectively; neither run reported errors. Those observations used older Tank Royale 1.2.0 artifacts. The current five-pair run did not reproduce the zero-score outcome, though it confirms a current score gap.

## Finding

Squirrel's historical no-score result does not recur, but its current score difference is confirmed by the five-run mean. The four non-outlier pair deltas also average beyond the harness's 15-point confirmation band; the one low-scoring Tank Royale run increases the overall gap. The reason for the score difference remains open, and skipped-turn telemetry was unavailable for two attempts. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bbo.RamboT_0.3.jar` (`score-review`).

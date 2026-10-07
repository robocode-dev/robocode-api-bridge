---
id: AN-150
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Smog's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-150 — Smog's negative score gap is confirmed across five pairs

## Risk investigated

Whether Smog's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `cx.nano.Smog_2.6.jar` has SHA-256 `1bdea7a9d116c385e6d7dcd12014dc80270f619cf2b20046c5d85640aeda4bbf`. The current official confirmation `d3030c83714ebd06` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `9f412f655a93e8120797b924c2708d5cdf635a6a`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, the score deltas were −40.5%, −25.3%, −50.1%, −30.8%, and −46.8%. Classic averaged 4,430.2 points and Tank Royale averaged 2,683, for a −38.7% mean delta. Both engines were error-free, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `c37a0d6ebee94fdf` and `bc706ecbeb69a224` also showed a Classic score advantage: 4,138 versus 2,998 and 3,384 versus 2,481, respectively. Those runs used older bridge and Tank Royale artifacts.

## Finding

Smog's negative score discrepancy is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/da.NewBGank_1.4.jar` (`score-review`).

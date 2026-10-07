---
id: AN-151
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: NewBGank's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-151 — NewBGank's negative score gap is confirmed across five pairs

## Risk investigated

Whether NewBGank's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `da.NewBGank_1.4.jar` has SHA-256 `e47b2ea746b5bd9680b8dd884b82b822857acbd31071bbec0d7b1647c461b194`. The current official confirmation `d27087a8b3f6cf55` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `36659a44636d32b0056b6fbd370743e366cc4cd5`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, the score deltas were −35.2%, −41.9%, −41.1%, −51.5%, and −39.0%. Classic averaged 6,343.6 points and Tank Royale averaged 3,708.4, for a −41.74% mean delta. Both engines were error-free, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `a25291b7dea73bfb` and `8b25ee1671f08bbe` also showed a Classic score advantage: 6,054 versus 4,001 and 5,984 versus 4,148, respectively. Those runs used older bridge and Tank Royale artifacts.

## Finding

NewBGank's negative score discrepancy is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/daemons.DizzyA_1.0.jar` (`score-review`).

---
id: AN-152
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: DizzyA's negative score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-152 — DizzyA's negative score gap is confirmed across five pairs

## Risk investigated

Whether DizzyA's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `daemons.DizzyA_1.0.jar` has SHA-256 `aeba11ca1cd50603c4b74166f04dea30238c77faead6f6830874571bea4091ad`. The current official confirmation `7e9f830806ecd028` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `51f696608834d714b939ca9e95fff5d10d525aa4`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, the score deltas were −64.7%, −62.9%, −62.5%, −66.4%, and −73.5%. Classic averaged 11,223.2 points and Tank Royale averaged 3,813, for a −66.0% mean delta. Both engines were error-free, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `4fb778b08e7b30a5` and `fce67ed0d1b04c77` also showed a Classic score advantage: 12,263 versus 3,000 and 13,103 versus 4,694, respectively. Those runs used older bridge and Tank Royale artifacts.

## Finding

DizzyA's negative score discrepancy is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dam.MogBot_2.9.jar` (`DISCREPANCY (outcome)`).

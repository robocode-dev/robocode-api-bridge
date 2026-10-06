---
id: AN-148
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Spark's large positive score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-148 — Spark's large positive score gap is confirmed across five pairs

## Risk investigated

Whether Spark's historical Tank Royale score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `cx.micro.Spark_0.6.jar` has SHA-256 `dfcbbb2bb08ae6028e68178c3d9f80c0b1d0058ced7ab90bab2cade92f615d18`. The current official confirmation `cc6837f8fd8e254d` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `a210fd34d18f6a523da423b5ebdca78212893feb`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, the score deltas were +149.1%, +136.1%, +150.7%, +152.2%, and +140.2%. Classic averaged 4,774.4 points and Tank Royale averaged 11,723.6, for a +145.66% mean delta. Both engines were error-free, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `4a48d9ed154a3163` and `33b8c433861acbd1` also showed a large Tank Royale advantage: 13,104 versus 5,050 and 12,373 versus 4,877, respectively. Those runs used older bridge and Tank Royale artifacts.

## Finding

Spark's large positive score discrepancy is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the advantage. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cx.mini.Nimrod_0.55.jar` (`score-review`).

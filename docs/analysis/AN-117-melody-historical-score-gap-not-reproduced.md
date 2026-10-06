---
id: AN-117
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Melody's historical score gaps are not reproduced in the current confirmation
provenance: inferred
reversal-cost: low
---

# AN-117 — Melody's historical score gaps are not reproduced in the current confirmation

## Risk investigated

Whether bing2.Melody's historical large score differences recur in the current matched pair and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bing2.Melody_1.3.1.jar` has SHA-256 `3a63316b12e61f4810fdb73e18bb068fb5bc0a78f82a2c9865be1aa22611137a`. The current official confirmation `02a6d347ffc7c3d8` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `414103e34f6ff05a0191c4751880766455027697`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 3,122.8 in Classic and 3,232.6 in Tank Royale, a +3.96% mean delta. The pair deltas were +1.3%, +8.8%, −19.7%, +14.6%, and +14.8%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `MATCHED (score noise)`.

Earlier observations `6d5c62b55bd1282c` and `5b3b35ee945403ba` recorded score deltas of +190.3% and +145.3%, without reported errors. The current confirmation did not reproduce those large single-pair differences; its mean delta is below the harness's 15-point confirmation band.

## Finding

Melody's earlier large score differences do not recur in the current five-pair confirmation. The historical cause remains unassigned because the available measurements do not show what changed. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bk.Shooter_1.0.jar` (`score-review`).

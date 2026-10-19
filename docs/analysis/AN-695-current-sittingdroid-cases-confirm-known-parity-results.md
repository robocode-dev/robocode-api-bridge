---
id: AN-695
type: analysis
status: active
links: [P-001, PDR-001, PDR-003, AN-015, AN-031, CAP-005, CAP-006, CAP-007]
title: Current SittingDroid cases confirm known parity results
provenance: inferred
reversal-cost: low
---

# AN-695 — Current SittingDroid cases confirm known parity results

## Risk investigated

Whether the current SittingDroid cases expose a new bridge defect, or reproduce the standalone droid result, known melee opponent failures, and the previously confirmed team survival-score gap.

## Evidence boundary

The three read-only jars were measured with Classic Robocode 1.11.1 and a matched local Tank Royale pair at commit `b952543aa604de43545f003d62488ef07271e08d`. The bridge commit was `f2a8d66c10c6e75cdd1f8a07d46c2c442b29758d`; the Bot API SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper SHA-256 was `30672d56c45d70b3ba3189681522a6a7cfd30626fa580d042b5847ad3da90dd0`.

The official setups were 800×600, 35 rounds, and two participants for Roborumble; 1000×1000, 35 rounds, and ten participants for MeleeRumble; and 1200×1200, ten rounds, and two teams for TeamRumble. The source jars and opponent jars remained unmodified.

## What was tried

The official filter `--only SittingDroid --retry-unresolved --capture-skipped-turns` selected three jars. The standalone Roborumble observation was `e5b4367763d790d7`; the MeleeRumble observation was `d4d685f491e15ea1`; and the TeamRumble sample was followed by the five-repeat confirmation `8e0dbf52523fefa2`.

The standalone and team jars contain `logiblocs.SittingDroid`, which extends `AdvancedRobot` and implements `Droid`. The team descriptor contains five copies of that droid. The current class and team identity match the prior inspection in AN-031.

## Findings

The standalone droid scored zero in both engines with no errors. Its class is a droid and issues no movement or fire commands in `run()`, so this observation does not indicate an asymmetric bridge result; it is a no-score case for a droid measured outside a team.

The MeleeRumble subject scored 118,170 in Classic and 113,990 in Tank Royale. Classic recorded 484 errors and Tank Royale recorded 30; the normalized origins were `amk.guns.Aristocles.prepare` and `amk.ChumbaMini.saveData`. Both jars are in the pinned opponent pool. This reproduces the opponent contamination already documented in AN-015 and does not identify a SittingDroid or bridge failure.

The team case returned 4,000 points in Classic and zero in Tank Royale in all five official repeats, with no runtime errors. It remains `CONFIRMED (score)`. Skipped-turn telemetry was captured, but the run did not capture per-bot death or disabled turns, so the cause remains unresolved as described in AN-031.

## Implications for M-006

This batch found no new bridge defect and made no code change. The standalone droid result and the pinned melee opponent failures have named causes; the team score gap remains an open Tank Royale scoring or lifecycle discrepancy requiring the focused death-order evidence requested by AN-031.

## M-006 handoff

Continue in registry order with `teamrumble/lxx.ConceptATeam_0.8.jar`.

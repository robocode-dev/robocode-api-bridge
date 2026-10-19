---
id: AN-698
type: analysis
status: active
links: [P-001, PDR-001, PDR-003, AN-046, CAP-001, CAP-005, CAP-006, CAP-007]
title: TMNR's null death lookup recurs on both engines
provenance: inferred
reversal-cost: low
---

# AN-698 — TMNR's null death lookup recurs on both engines

## Risk investigated

Whether the current TMNR team failure indicates a bridge event or identity defect, or repeats the robot-owned null lookup diagnosed in AN-046.

## Evidence boundary

The read-only team jar `tmnr.TMNR_1.01.jar` has SHA-256 `6ba527fe5cd0f98ebba9dd4ccb2cdba8b8e6da4ef60f0d69b0b4a65b24bd6e47`; it contains five team members. The current official observation `a8ef6cae075ba644` used TeamRumble settings (1200×1200, ten rounds, two teams), Classic Robocode 1.11.1, and the matched local bridge and Tank Royale artifacts pinned in the registry. The jar was not modified.

## What was tried

Classic completed with a score of 19,639 and two error records. Its null dereference originated in `tmnr.Michelangelo$Robot.access$6`. Tank Royale produced no score and two error records; its null dereference originated in `tmnr.Leonardo$Robot.access$6`. Both errors reported `Cannot assign field "alive" because "<parameter1>" is null`.

## Finding

The same null-unsafe death lookup recurred in different team members on both engines. AN-046 traces that behavior to the robot's assumption that each death victim was previously inserted by a scan. The current run therefore reinforces the robot-owned diagnosis and does not show a bridge event or name-mapping defect.

Tank Royale produced no score, so this remains an `DISCREPANCY (outcome)` and supplies no usable score comparison. The existing diagnosis `robot-death-event-assumes-prior-scan`, owner `robot`, remains attached to the registry subject. No code changed.

## Implications for M-006

The team failure is diagnosed as robot-owned, but the parity case remains incomplete because the current Tank Royale run has no result. No bridge repair is indicated by this evidence.

## M-006 handoff

Continue with the first undiagnosed discrepancy in registry order: `roborumble/Krabb.sliNk.Garm_0.9u.jar`.

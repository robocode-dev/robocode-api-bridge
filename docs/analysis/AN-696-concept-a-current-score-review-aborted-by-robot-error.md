---
id: AN-696
type: analysis
status: active
links: [P-001, PDR-001, PDR-003, AN-032, CAP-005, CAP-006, CAP-007]
title: ConceptATeam's current score review stopped on its known robot error
provenance: inferred
reversal-cost: low
---

# AN-696 — ConceptATeam's current score review stopped on its known robot error

## Risk investigated

Whether the current ConceptATeam score difference is a stable bridge discrepancy or an effect of the team's known null dereference.

## Evidence boundary

The read-only team jar `lxx.ConceptATeam_0.8.jar` has SHA-256 `8ed86d05ddbe64e77cc93352da90d53afed21a832a43f40f1525f1b67f1264aa`. The one-pair official retest used a 1200×1200 field, ten rounds, and two teams with Classic Robocode 1.11.1 and the matched local artifacts recorded in the registry. The five-repeat score confirmation used the same official setup. The jar was not modified.

The one-pair retest is observation `5adc1d1cfb39859b`; the attempted confirmation is observation `b818d2e9de1c6023`. AN-032 contains the source trace for the same `CaUtils.getNonZeroLateralDirection()` null dereference and records its owner as the robot.

## What was tried

The one-pair result completed with Classic at 21,794 points and Tank Royale at 30,116, a +38.2% delta. Classic recorded two null-pointer errors and Tank Royale one; both recorded the same `lxx.util.CaUtils.getNonZeroLateralDirection` origin.

The five-repeat confirmation stopped on its first attempt after the bot error. It recorded zero completed score samples, null scores, and incomplete skipped-turn telemetry, so it produced no confirmation mean.

## Finding

The +38.2% value is a single measurement with robot-owned errors and is not a confirmed score gap. The aborted confirmation does not show a new bridge defect; AN-032 already traces the null dereference to the read-only robot's wave model retaining a state without a unique opponent.

The registry retains the current observations and the existing cause `robot-null-previous-duel-opponent-on-wave-callback`. The score difference remains unconfirmed until this robot can complete enough official pairs to evaluate it independently of its exception.

## Implications for M-006

No code changed. The case remains a robot-owned error path with a new inconclusive score sample; it is not a clean parity pass and does not justify a bridge repair.

## M-006 handoff

Continue in registry order with `teamrumble/mn.CombatTeam_3.25.0.jar`.

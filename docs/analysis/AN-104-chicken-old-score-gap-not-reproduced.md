---
id: AN-104
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-102]
title: Chicken's historical zero-score Tank Royale result does not recur with current artifacts
provenance: inferred
reversal-cost: low
---

# AN-104 — Chicken's historical zero-score Tank Royale result does not recur with current artifacts

## Risk investigated

Whether DM.Chicken's historical −100% score discrepancy persists under the current matched artifact pair or confirms a current score gap.

## Evidence boundary

The read-only subject jar `DM.Chicken_4.0.jar` has SHA-256 `7c87fd26cff5ac4aa315ef19dfe2427ee6e01761b45c9181ebc192983558a955`. The current official confirmation `1c49b86f1412a864` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `b100e75832baf854aaf681a1942ec9087d365863`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 2 participants, 35 rounds, and an 800×600 arena. The matched local Bot API and runner artifacts are recorded in the observation; the runner SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`. The subject jar was not changed.

## What was tried

The first current-artifact pair scored 9,112 in Classic and 7,525 in Tank Royale, a −17.4% delta, with zero errors on both engines. The official five-repeat confirmation produced mean scores of 7,912.2 in Classic and 8,546.4 in Tank Royale, for a +9.28% mean delta. Its five deltas were +21.1%, −1.2%, +5.6%, −8.1%, and +29.0%; the registry classifies the result as `MATCHED (score noise)`. No skipped-turn events were captured in any repeat.

The historical observation `f98b80f045eaa5ba` scored 7,801 in Classic and zero in Tank Royale, but used bridge commit `7c7e232a37beaac0f3aa1c7760c0a66ab41480b3`, Tank Royale commit `a553d8069e3f67e8711f0a3e976c339f389faec8`, Bot API 1.2.0, and the older examples runner. The current five-repeat result does not reproduce that outcome; the evidence does not isolate which difference in the older artifact pair caused its zero score.

## Finding

The historical −100% discrepancy is not reproduced with the current matched artifacts. The current official five-repeat mean is within the review threshold and is `MATCHED (score noise)`, with zero errors and no skipped turns. Do not treat the old zero-score result as a confirmed current gap. Its cause remains unassigned because the old artifact pair was not retested. No code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/DM.Mijit_.3.jar`.

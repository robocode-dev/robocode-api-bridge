---
id: AN-536
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: RobotA's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-536 — RobotA's Classic score advantage is confirmed again

## Risk investigated

Whether `ma.is.fon.rs.RobotA_0.01.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `4462fc6c458c7a65d32345b0940116241c43416c7448dc02c7dd8c743c764f5b`. The official five-pair confirmation `34183125d25062aa` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `64bf710b3b8c5dadbbe8742a213d5f3fdc5f4ef3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 12,971.8 points and Tank Royale averaged 6,806.2 points. Pair deltas were −49.5%, −48.1%, −46.7%, −46.0%, and −47.4%, for a −47.54% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts, with no events. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `a074520b2159b864` recorded a −43.2% delta. The current five-pair mean confirms a similar Classic score advantage at −47.54%.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

RobotA's Classic score advantage persists under current matched artifacts. Its measured mean gap is slightly larger than the earlier −43.2% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/madmath.Cow_0.1.1.jar` (`score-review`) in AN-537.

---
id: AN-534
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: QuickHack's score retest stops on runtime errors
provenance: inferred
reversal-cost: low
---

# AN-534 — QuickHack's score retest stops on runtime errors

## Risk investigated

Whether `lrem.quickhack.QuickHack_1.0.jar`'s historical score difference can be confirmed under current artifacts, or whether runtime errors again prevent a complete five-pair sample.

## Evidence boundary

The read-only subject jar has SHA-256 `507384805e1ed95a3223f56e2a054c1013ace180c561fd2b4260f2ff880a161f`. The retest observation `b310cceee4e6b092` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `64bf710b3b8c5dadbbe8742a213d5f3fdc5f4ef3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena. It attempted four runs and obtained three valid paired score samples; attempt 4 had incomplete skipped-turn telemetry. This is not clean-checkout reproducibility, and the current record does not preserve the exact Java executable selected for Classic.

The three valid pairs averaged 5,884.67 points in Classic and 3,266.67 in Tank Royale, with pair deltas −54.3%, −26.3%, and −51.9%, for a −44.17% mean. The registry lists `ArrayIndexOutOfBoundsException` signatures from `lrem.quickhack.QuickHack.doMovement`, `lrem.quickhack.QuickHack.smoothe`, and an unknown origin among the Tank Royale confirmation's bridge-only signatures. The earlier observation `28eb9e453aea91dd` had no recorded errors and a −36.7% delta. Current registry status is `DISCREPANCY (errors)`.

## What was tried

The official confirmation attempted the five-pair procedure but stopped after the fourth run encountered errors. The three valid pairs retain a Classic score advantage, but they do not meet the five-pair confirmation method.

## What was not pursued

No source-level diagnosis was attempted, and the recorded method names alone do not establish why the exceptions occurred or assign them to the bridge. No code or rumble-jar change was made.

## Finding

QuickHack's retest ends with `DISCREPANCY (errors)` after three valid pairs and an error-interrupted fourth attempt. Its −44.17% score mean is incomplete evidence and does not confirm the historical score difference. The exception cause remains unknown.

## M-006 handoff

Continue with `roborumble/lw.LuthersTest_0.1.jar` (`score-review`) in AN-535. QuickHack is no longer in `score-review` after this error result.

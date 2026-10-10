---
id: AN-528
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Derp's score retest repeats its runtime error
provenance: inferred
reversal-cost: low
---

# AN-528 — Derp's score retest repeats its runtime error

## Risk investigated

Whether `kneels.nano.Derp_0.2.jar`'s historical score difference can be confirmed under current artifacts, or whether its runtime error again prevents a complete paired score sample.

## Evidence boundary

The read-only subject jar has SHA-256 `a7d4b85627bde12745484df0fcff7088a2c626c4e8f6c9588c84bb87c1e88c1c`. The retest observation `e3d45273ae8f712f` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena. It attempted two runs and obtained one valid paired score sample; the second run's skipped-turn telemetry was incomplete. This is not clean-checkout reproducibility, and the current record does not preserve the exact Java executable selected for Classic.

The one valid pair scored 5,582 points in Classic and 3,502 in Tank Royale, a −37.3% delta. The confirmation stopped after two attempts with one sample because `java.lang.StringIndexOutOfBoundsException` was reported from `kneels.nano.Derp.onScannedRobot`; the registry lists this signature among the confirmation's bridge-only signatures. The previous observation `cd4748fb78080dd6` showed the same exception origin in both engines. The current registry status is `DISCREPANCY (errors)`, not a confirmed score status.

## Population and sampling boundary

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`. The considered population was its `roborumble` robot rows; this subject was eligible because its starting status was `score-review` and it was selected in registry order. The official five-pair method is the sample; all valid pairs were retained, while failed or incomplete attempts are reported separately for error-stopped subjects. Pair deltas show observed spread, but no confidence interval was computed. Under [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this is statistical sweep quality evidence rather than deterministic acceptance proof.

## What was tried

The earlier observation recorded a −29.9% score delta alongside repeated `StringIndexOutOfBoundsException` reports from the subject callback in both Classic and Tank Royale. The current retest again encountered that signature before it could collect all five pairs. Its single valid pair is insufficient to confirm the historical score difference.

## What was not pursued

No source-level diagnosis was attempted, and the callback location alone does not establish why the exception occurred or assign it to the bridge. No code or rumble-jar change was made.

## Finding

Derp's retest repeats a runtime error in `onScannedRobot` and ends with `DISCREPANCY (errors)`. The observed −37.3% score delta comes from one valid pair and is not a five-pair confirmation; the error's cause remains unknown.

## M-006 handoff

Continue with `roborumble/lechu.Lechu_1.1.jar` (`score-review`) in AN-529. Derp is no longer in `score-review` after this error result.

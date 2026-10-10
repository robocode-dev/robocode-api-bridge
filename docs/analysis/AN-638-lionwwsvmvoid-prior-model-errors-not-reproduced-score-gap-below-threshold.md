---
id: AN-638
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: LionWWSVMvoid's prior model errors are not reproduced in the current retest
provenance: inferred
reversal-cost: low
---

# AN-638 — LionWWSVMvoid's prior model errors are not reproduced in the current retest

## Risk investigated

Do LionWWSVMvoid's earlier model-loading errors and score discrepancy persist under current matched artifacts?

## Evidence boundary

The read-only subject jar `sqTank.waveSurfing.LionWWSVMvoid_0.01.jar` has SHA-256 `a001c2404157bab122e0dbe312e33e79322ce0ddae55d9ab660def1c80bff7a6`. The current five-pair confirmation `2e3f8ce266be0b51` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `3f631a2269075741c511271640e96c7351a8adb1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists sqTank.waveSurfing.LionWWSVMvoid 0.01 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `3f631a2269075741c511271640e96c7351a8adb1`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 2,554.6 points and Tank Royale averaged 3,138.4 points. Pair deltas were +9.0%, +36.6%, +25.9%, +46.4%, +5.9%, for a +24.76% mean (Tank Royale leads by 24.76%). The confirmation manifest's threshold is 25.0%; the current mean is below that threshold, while the registry status is `CONFIRMED (score)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `5ee51a0234856dfe` recorded a +38.60% delta with 70 error entries on each engine, including `FileNotFoundException` from `sqTank.libsvm.SVMHelper.loadSVMModel`. The current five-pair mean is +24.76%; both current engine error lists are empty, and the registry reports `CONFIRMED (score)`.

## What was not pursued

The current retest does not establish why the earlier missing-model errors disappeared or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled model-file intervention, trace, or source comparison was made, and no code or rumble-jar change was made.

## Finding

LionWWSVMvoid's current five-pair sample shows a 24.76% Tank Royale score lead, just below the recorded 25.0% threshold. Both current engine error lists are empty; the earlier observation's FileNotFoundException at `sqTank.libsvm.SVMHelper.loadSVMModel` was not reproduced. The registry status is `CONFIRMED (score)`, and the reason is not inferred.

## M-006 handoff

Continue with `roborumble/squidM.SquidmanNano_1.0.jar` (`score-review`) in AN-639.

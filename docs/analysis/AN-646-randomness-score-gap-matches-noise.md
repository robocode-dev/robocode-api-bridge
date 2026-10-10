---
id: AN-646
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Randomness' score gap matches observed noise
provenance: inferred
reversal-cost: low
---

# AN-646 — Randomness' score gap matches observed noise

## Risk investigated

Does Randomness' earlier score discrepancy persist under current matched artifacts, or does it fall within the recorded threshold?

## Evidence boundary

The read-only subject jar `stelo.Randomness_1.1.jar` has SHA-256 `16a117f958df0bd34ea0c6a6f58607e7295f85146d655c0f5609b516dcaf5f90`. The current five-pair confirmation `f35506df150bdb0d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `69f71650c83a9cb7171d74a36b1a7779d1047fad`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists stelo.Randomness 1.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `69f71650c83a9cb7171d74a36b1a7779d1047fad`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,758.2 points and Tank Royale averaged 5,329.0 points. Pair deltas were -7.1%, -3.3%, -2.4%, -16.4%, -6.7%, for a -7.18% mean (Classic leads by 7.18%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `9fd868a5044a5de0` recorded a -61.80% delta. The current five-pair mean is -7.18%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest places the score difference within the recorded noise threshold but does not explain the earlier larger discrepancy. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Randomness' five-pair sample shows a 7.18% Classic score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

The SteloTestNano retest is recorded in AN-647; continue with `roborumble/stelo.UnfoolableNano_1.0.jar` (`score-review`) in AN-648.

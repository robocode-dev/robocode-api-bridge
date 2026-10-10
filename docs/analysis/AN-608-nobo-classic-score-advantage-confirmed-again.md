---
id: AN-608
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Nobo's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-608 — Nobo's Classic score advantage is confirmed again

## Risk investigated

Does Nobo's earlier score discrepancy persist under current matched artifacts, and which engine leads?

## Evidence boundary

The read-only subject jar `ratosh.Nobo_0.21.jar` has SHA-256 `b6883110fc94c5bc4ff8f1082a64023c04959c9255117002357e6307ca1287fb`. The current five-pair confirmation `92dc25c0e370d949` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `bba433e428e4305b180f746963b3870df544010b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ratosh.Nobo 0.21 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `bba433e428e4305b180f746963b3870df544010b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,387.4 points and Tank Royale averaged 2,045.4 points. Pair deltas were -56.7%, -49.0%, -57.0%, -53.8%, -49.4%, for a -53.18% mean (Classic leads by 53.18%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `b6122ed051a7f694` recorded a -52.00% delta. The current five-pair mean is -53.18%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a score difference but does not explain its cause. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Nobo's five-pair sample confirms a 53.18% Classic score advantage: Classic averaged 4,387.4 points and Tank Royale averaged 2,045.4. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/ratosh.Wesco_1.4.jar` (`score-review`) in AN-609.

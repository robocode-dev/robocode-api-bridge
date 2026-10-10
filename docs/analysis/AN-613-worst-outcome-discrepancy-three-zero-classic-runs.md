---
id: AN-613
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Worst's retest records an outcome discrepancy with three zero-score Classic runs
provenance: inferred
reversal-cost: low
---

# AN-613 — Worst's retest records an outcome discrepancy with three zero-score Classic runs

## Risk investigated

Does Worst's earlier score discrepancy persist under current matched artifacts, and are all five attempts scoreable?

## Evidence boundary

The read-only subject jar `ry.Worst_1.0.jar` has SHA-256 `1c221d89476d10b8afa1df695e7eb1cfa6d6402cea4ca05f805ca6209875862c`. The current five-attempt retest `be77bbd172b354b4` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aa1baaec2961a82966280835ba0b66be39a8208b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ry.Worst 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `aa1baaec2961a82966280835ba0b66be39a8208b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. Only two of five attempts produced score deltas; no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

The five-attempt retest produced two score deltas, +270.2% and +600.0%, with a +435.10% mean across those two pairs; the other three attempts had a Classic score of zero, so no relative score delta was available. Classic averaged 132.6 points and Tank Royale averaged 1,576.0 across all five attempts. Both aggregate engine results have `ok:false`; both error lists are empty and no bridge-only signatures were recorded. The registry status is `DISCREPANCY (outcome)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `946be774c812e808` recorded a +31,100.00% delta. The current retest has five attempts but only two score deltas, and the registry reports `DISCREPANCY (outcome)`.

## What was not pursued

The retest records an outcome discrepancy but does not establish its cause. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Worst's five-attempt retest is `DISCREPANCY (outcome)`. Three Classic attempts scored zero; only two of five pairs produced relative score deltas (+270.2% and +600.0%). Both aggregate `ok` fields are false despite empty error lists. The reason for the outcome discrepancy remains undetermined.

## M-006 handoff

Continue with `roborumble/rz.SmallDevil_1.502.jar` (`score-review`) in AN-614.

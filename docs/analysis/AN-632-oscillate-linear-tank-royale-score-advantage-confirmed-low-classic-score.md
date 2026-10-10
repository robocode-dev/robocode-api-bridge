---
id: AN-632
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: OscillateLinear's Tank Royale score advantage is confirmed at low Classic score
provenance: inferred
reversal-cost: low
---

# AN-632 — OscillateLinear's Tank Royale score advantage is confirmed at low Classic score

## Risk investigated

Does OscillateLinear's earlier Tank Royale score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `slugzilla.OscillateLinear_1.0.jar` has SHA-256 `548b4da25cc2e26cb30e30624239c32f58c7ff7757b2f0d9f3d297b65a94e994`. The current five-pair confirmation `080a54358e5536b6` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `87f0ec7e060bb27dbbb93dcba87734ba2d84dcb6`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists slugzilla.OscillateLinear 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `87f0ec7e060bb27dbbb93dcba87734ba2d84dcb6`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 141.6 points and Tank Royale averaged 1,848.2 points. Pair deltas were +471.2%, +1,673.0%, +1,372.0%, +2,040.2%, +2,363.9%, for a +1,584.06% mean (Tank Royale leads by 1,584.06%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `41fdb3b690b35884` recorded a +752.00% delta. The current five-pair mean is +1,584.06%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a very large relative score difference but does not explain its cause. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

OscillateLinear's five-pair sample confirms a 1,584.06% Tank Royale score advantage. Classic averaged 141.6 points and Tank Royale averaged 1,848.2; pair deltas ranged from +471.2% to +2,363.9%. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

The OscillateLinear retest is recorded here. Continue with `roborumble/slugzilla.OscillatePattern_1.0.jar` (`score-review`) in AN-633.

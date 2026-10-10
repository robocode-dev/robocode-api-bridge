---
id: AN-620
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: R0's Tank Royale score gap falls below the threshold
provenance: inferred
reversal-cost: low
---

# AN-620 — R0's Tank Royale score gap falls below the threshold

## Risk investigated

Does R0's earlier Tank Royale score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `satan.R0_0.2.jar` has SHA-256 `3974e02626d6b8c1df6a42746c39289f3ee07108bdf2809db03df73b8b2e1b41`. The current five-pair confirmation `db739388d08ad461` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `f7ed7e790fc94a0e70b0bb10cec731faec44ccf3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists satan.R0 0.2 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `f7ed7e790fc94a0e70b0bb10cec731faec44ccf3`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 9,091.2 points and Tank Royale averaged 10,681.6 points. Pair deltas were +21.6%, +11.7%, +21.2%, +22.6%, +11.0%, for a +17.62% mean (Tank Royale leads by 17.62%). The confirmation manifest's threshold is 25.0%; the mean is below that threshold, while the registry status is `CONFIRMED (score)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `61afc6337d7d111f` recorded a +28.60% delta. The current five-pair mean is +17.62%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest shows a smaller Tank Royale score difference but does not explain the change or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

R0's five-pair sample shows a 17.62% Tank Royale score lead, below the recorded 25.0% threshold. The registry classifies the result as `CONFIRMED (score)`; the reason for that classification is not inferred.

## M-006 handoff

Continue with `roborumble/sgp.JollyNinja_3.53.jar` (`score-review`) in AN-621.

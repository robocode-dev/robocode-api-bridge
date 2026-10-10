---
id: AN-662
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: GeomancyBS's Tank Royale score advantage confirmed with mixed pair results
provenance: inferred
reversal-cost: low
---

# AN-662 — GeomancyBS's Tank Royale score advantage confirmed with mixed pair results

## Risk investigated

Does GeomancyBS's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `synapse.rsim.GeomancyBS_0.11.jar` has SHA-256 `cd1340a3256f82b28d09102201f7c17c1b15f1b1e41f2f2a505fb65e20bc912d`. The current five-pair confirmation `91eb9045b8d949f2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists synapse.rsim.GeomancyBS 0.11 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 3,862.8 points and Tank Royale averaged 4,902.8 points. Pair deltas were +52.0%, +68.4%, -7.4%, +61.7%, -2.6%, for a +34.42% mean (Tank Royale leads by 34.42%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events. The mean Tank Royale lead is above threshold, but two of the five pair deltas favor Classic (-7.4% and -2.6%); the other three range from +52.0% to +68.4%. All pairs were retained, and no confidence interval was computed.

## What was tried

The earlier observation `315c584dc2afb693` recorded a +42.40% delta. The current five-pair mean is +34.42%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only synapse.rsim.GeomancyBS_0.11.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

GeomancyBS's five-pair sample records a 34.42% Tank Royale score lead: Classic averaged 3,862.8 points and Tank Royale averaged 4,902.8. The registry status is `CONFIRMED (score)`; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/synnalagma.NeuralPremier_0.51.jar` (`score-review`) in AN-663.

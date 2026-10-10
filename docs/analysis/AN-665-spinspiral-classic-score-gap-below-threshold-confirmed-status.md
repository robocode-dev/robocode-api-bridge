---
id: AN-665
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: SpinSpiral's Classic score gap falls below threshold despite confirmed status
provenance: inferred
reversal-cost: low
---

# AN-665 — SpinSpiral's Classic score gap falls below threshold despite confirmed status

## Risk investigated

Does SpinSpiral's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `takeBot.SpinSpiral_1.2.jar` has SHA-256 `520582297f1d66faa0d2623379a4ff5fb44bdb98d0063eeeadd0c81f628c660b`. The current five-pair confirmation `023a9d3cfb301fd6` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists takeBot.SpinSpiral 1.2 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,456.2 points and Tank Royale averaged 5,244.2 points. Pair deltas were -38.9%, -26.3%, -1.9%, -4.0%, -16.2%, for a -17.46% mean (Classic leads by 17.46%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events. The current mean Classic lead is 17.46%, below the 25.0% threshold, while the registry status is `CONFIRMED (score)`. The five pair deltas are all negative, but only two exceed 25% in magnitude. The recorded status and measured mean are both preserved here; the retest does not establish why they differ.

## What was tried

The earlier observation `871912878b912f9b` recorded a -25.10% delta. The current five-pair mean is -17.46%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only takeBot.SpinSpiral_1.2.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The measured mean is below threshold even though the registry reports `CONFIRMED (score)`; this note does not infer the classifier's reason. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

SpinSpiral's five-pair sample records a 17.46% Classic score lead: Classic averaged 6,456.2 points and Tank Royale averaged 5,244.2. The registry status is `CONFIRMED (score)`; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/takeBot.SpiralCrash_1.0.jar` (`score-review`) in AN-666.

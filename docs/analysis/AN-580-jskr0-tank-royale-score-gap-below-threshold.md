---
id: AN-580
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Jskr0's Tank Royale score gap narrows below threshold
provenance: inferred
reversal-cost: low
---

# AN-580 — Jskr0's Tank Royale score gap narrows below threshold

## Risk investigated

Does Jskr0's earlier Tank Royale score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `nkn.mini.Jskr0_0.1.jar` has SHA-256 `df2140c3759d5946eef47e1362b3b27c6a3e8e096c78a86d590e0634536b7c92`. The current five-pair confirmation `87af65218e56ed31` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `83005f7f8c1c3a11cf808f22935c91941b765558`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists nkn.mini.Jskr0 0.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `83005f7f8c1c3a11cf808f22935c91941b765558`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,696.4 points and Tank Royale averaged 5,769.0 points. Pair deltas were +28.6%, +18.8%, +30.0%, +13.5%, +24.2%, for a +23.02% mean (Tank Royale leads by 23.02%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `c273fc1b4d424de2` recorded a +31.70% delta. The current five-pair mean is +23.02%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller score difference but does not explain the change or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Jskr0's five-pair sample shows a 23.02% Tank Royale score lead, down from 31.7% and below the recorded 25.0% threshold. The registry classifies the result as `CONFIRMED (score)`; the reason for that classification is not inferred.

## M-006 handoff

Continue with `roborumble/ntc.Evader_1.2.jar` (`score-review`) in AN-581.

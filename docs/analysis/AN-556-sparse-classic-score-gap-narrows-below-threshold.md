---
id: AN-556
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Sparse's Classic score gap narrows below threshold
provenance: inferred
reversal-cost: low
---

# AN-556 — Sparse's Classic score gap narrows below threshold

## Risk investigated

Does Sparse's historical Classic score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `ebo.Sparse_0.02.jar` has SHA-256 `871ad9964dc27ed22be3491e09516a3bbf67e6c0a4d065ae9343c45b31603e74`. The current retest `226776907c09cc68` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ebo.Sparse 0.02 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All 5 valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,470.0 points and Tank Royale averaged 4,141.8 points. Pair deltas were -17.7%, -33.0%, -28.3%, -20.3%, -21.5%, for a -24.16% mean (Classic leads by 24.16%). The confirmation manifest's threshold is 25.0%; the registry status is nevertheless `CONFIRMED (score)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `9da95784397a1e18` recorded a -34.30% delta. The current five-pair mean is -24.16%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller Classic score difference but does not explain the change or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Sparse's five-pair sample shows a 24.16% Classic score lead, down from 34.3% in the earlier observation and below the recorded 25.0% threshold. The registry classifies the result as `CONFIRMED (score)`; the reason for that classification is not inferred.

## M-006 handoff

Continue with `roborumble/eem.IWillFireNoBullet_v2.4.jar` (`score-review`) in AN-557.

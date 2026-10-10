---
id: AN-602
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: PulsarNano's Classic score gap narrows below threshold
provenance: inferred
reversal-cost: low
---

# AN-602 — PulsarNano's Classic score gap narrows below threshold

## Risk investigated

Does PulsarNano's earlier Classic score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `pulsar.PulsarNano_0.2.4.jar` has SHA-256 `557137e6ebb7e20df76344e777677f389a707bbc7ac9990881bfe169dbd8a513`. The current five-pair confirmation `e0fc6a73a1161201` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6803017b6f4db5f8788f5d3dd06861f329e1273f`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists pulsar.PulsarNano 0.2.4 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `6803017b6f4db5f8788f5d3dd06861f329e1273f`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,036.0 points and Tank Royale averaged 3,791.2 points. Pair deltas were -31.8%, -26.3%, -15.5%, -19.4%, -29.6%, for a -24.52% mean (Classic leads by 24.52%). The confirmation manifest's threshold is 25.0%; the registry status is nevertheless `CONFIRMED (score)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `53dbc59e2685a77b` recorded a -31.50% delta. The current five-pair mean is -24.52%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller Classic score difference but does not explain the change or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

PulsarNano's five-pair sample shows a 24.52% Classic score lead, down from 31.5% and below the recorded 25.0% threshold. The registry classifies the result as `CONFIRMED (score)`; the reason for that classification is not inferred.

## M-006 handoff

Continue with `roborumble/rcp.Kuramatron_1.0.jar` (`score-review`) in AN-603.

---
id: AN-601
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Nanis's Classic score gap narrows below threshold
provenance: inferred
reversal-cost: low
---

# AN-601 — Nanis's Classic score gap narrows below threshold

## Risk investigated

Does Nanis's earlier Classic score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `pulsar.Nanis_0.3.jar` has SHA-256 `ef1a1d67111d4b8797642bd47b2c75eea9cc061000cd73a67bc527da37d71816`. The current five-pair confirmation `2e20d2ad493ab8e7` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `e0136cb8f4a676f513c2be647eefc1693836616e`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists pulsar.Nanis 0.3 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `e0136cb8f4a676f513c2be647eefc1693836616e`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 8,567.0 points and Tank Royale averaged 7,248.8 points. Pair deltas were -10.5%, -7.4%, -16.4%, -17.1%, -25.2%, for a -15.32% mean (Classic leads by 15.32%). The confirmation manifest's threshold is 25.0%; the registry status is nevertheless `CONFIRMED (score)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `e9e6347eb3a19302` recorded a -30.80% delta. The current five-pair mean is -15.32%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller Classic score difference but does not explain the change or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Nanis's five-pair sample shows a 15.32% Classic score lead, down from 30.8% and below the recorded 25.0% threshold. The registry classifies the result as `CONFIRMED (score)`; the reason for that classification is not inferred.

## M-006 handoff

Continue with `roborumble/pulsar.PulsarNano_0.2.4.jar` (`score-review`) in AN-602.

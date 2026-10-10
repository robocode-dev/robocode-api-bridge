---
id: AN-550
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Infinity's Classic score difference is confirmed below threshold
provenance: inferred
reversal-cost: low
---

# AN-550 — Infinity's Classic score difference is confirmed below threshold

## Risk investigated

Does Infinity's earlier Classic score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `mld.Infinity_2.2.jar` has SHA-256 `c982f1fe1da7cc9dfd196d695c569695f110bd9147c5b428b261925691707fe4`. The official five-pair confirmation `c9d981b08e239794` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `596a4914d9684b64a70123941a165b8883c03c3f`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry does not retain the exact selected Classic robot string or the Java executable used.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `596a4914d9684b64a70123941a165b8883c03c3f`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,834.6 points and Tank Royale averaged 4,777.8 points. Pair deltas were -11.2%, -13.2%, -14.0%, -23.4%, -26.5%, for a -17.66% mean (Classic leads by 17.66%). The confirmation manifest's threshold is 25.0%; the registry status is nevertheless `CONFIRMED (score)`, which is recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `e5b845799f252102` recorded a -59.80% delta. The current five-pair mean is -17.66%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller score difference but does not explain its cause or why the registry reports a confirmed score discrepancy below the manifest threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Infinity's five-pair sample shows a 17.66% Classic score lead, down from 59.8% in the earlier observation and below the recorded 25.0% threshold. The registry classifies the result as `CONFIRMED (score)`; the reason for that classification is not inferred.

## M-006 handoff

Continue with `roborumble/mld.LittleBlackBook_1.69e.jar` (`score-review`) in AN-551.

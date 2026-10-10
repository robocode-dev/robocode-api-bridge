---
id: AN-685
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: MicroMacro's Score gap matches observed noise after an earlier Classic lead
provenance: inferred
reversal-cost: low
---

# AN-685 — MicroMacro's Score gap matches observed noise after an earlier Classic lead

## Risk investigated

Does MicroMacro's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `winamp32.micro.MicroMacro_1.0.jar` has SHA-256 `3b022d1a10dce16f69a8a357b3bd632031a9d6b5b71ab7403a1361279802d727`. The current five-pair confirmation `d31847c5b8039d14` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists winamp32.micro.MicroMacro 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 7,283.4 points and Tank Royale averaged 6,685.2 points. Pair deltas were -9.9%, -11.9%, -6.2%, -8.2%, -4.7%, for a -8.18% mean (Classic leads by 8.18%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The current 8.18% Classic lead is within the 25.0% threshold; the earlier observation recorded a 95.70% Classic lead.

## What was tried

The earlier observation `859b669e24953d5f` recorded a -95.70% delta. The current five-pair mean is -8.18%; the registry reports `MATCHED (score noise)`. The retest command was `python compat_test.py --collections roborumble --only winamp32.micro.MicroMacro_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest places the score gap within the recorded noise threshold but does not explain why the earlier score discrepancy differed. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

MicroMacro's five-pair sample has a 8.18% Classic score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

Continue with `roborumble/wit.Chuliath_1.0.jar` (`score-review`) in AN-686.

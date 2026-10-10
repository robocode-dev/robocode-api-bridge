---
id: AN-621
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: JollyNinja's score gap matches observed noise
provenance: inferred
reversal-cost: low
---

# AN-621 — JollyNinja's score gap matches observed noise

## Risk investigated

Does JollyNinja's earlier score difference persist under current matched artifacts, or does it fall within the recorded threshold?

## Evidence boundary

The read-only subject jar `sgp.JollyNinja_3.53.jar` has SHA-256 `1fc62e0697b91ec8a497fbb1f02f18195fa7397a4e8f171e5de43cec0318313b`. The current five-pair confirmation `8fca901a457ee51e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `f7ed7e790fc94a0e70b0bb10cec731faec44ccf3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists sgp.JollyNinja 3.53 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `f7ed7e790fc94a0e70b0bb10cec731faec44ccf3`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,710.2 points and Tank Royale averaged 4,480.2 points. Pair deltas were -12.5%, -4.4%, -5.0%, -0.4%, -1.2%, for a -4.70% mean (Classic leads by 4.70%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `fb3b063e3ea768d0` recorded a -25.90% delta. The current five-pair mean is -4.70%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest places the score difference within the recorded noise threshold but does not explain the earlier larger discrepancy. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

JollyNinja's five-pair sample shows a 4.70% Classic score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

Continue with `roborumble/sheldor.micro.EpeeistMicro_2.1.0.jar` (`score-review`) in AN-622.

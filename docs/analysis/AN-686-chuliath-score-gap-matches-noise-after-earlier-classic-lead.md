---
id: AN-686
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Chuliath's Score gap matches observed noise after an earlier Classic lead
provenance: inferred
reversal-cost: low
---

# AN-686 — Chuliath's Score gap matches observed noise after an earlier Classic lead

## Risk investigated

Does Chuliath's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `wit.Chuliath_1.0.jar` has SHA-256 `b8d6cf26ca6eaffdac021562c0bea09d617578182b73a81525bd6393589acbc9`. The current five-pair confirmation `4066d37f0444687b` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists wit.Chuliath 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,333.0 points and Tank Royale averaged 5,693.6 points. Pair deltas were -10.5%, -4.1%, -16.4%, -11.0%, -8.1%, for a -10.02% mean (Classic leads by 10.02%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The current 10.02% Classic lead is within the 25.0% threshold; the earlier observation recorded a 43.80% Classic lead.

## What was tried

The earlier observation `8de4ab4e3ebebf75` recorded a -43.80% delta. The current five-pair mean is -10.02%; the registry reports `MATCHED (score noise)`. The retest command was `python compat_test.py --collections roborumble --only wit.Chuliath_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest places the score gap within the recorded noise threshold but does not explain why the earlier score discrepancy differed. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Chuliath's five-pair sample has a 10.02% Classic score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

The Kowari retest is recorded in AN-687; continue with `roborumble/yagami.Tidus_0.11.jar` (`score-review`) in AN-688.

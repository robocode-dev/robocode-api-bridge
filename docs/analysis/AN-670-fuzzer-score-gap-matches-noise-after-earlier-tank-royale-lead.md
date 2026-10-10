---
id: AN-670
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Fuzzer's Score gap matches observed noise after an earlier Tank Royale lead
provenance: inferred
reversal-cost: low
---

# AN-670 — Fuzzer's Score gap matches observed noise after an earlier Tank Royale lead

## Risk investigated

Does Fuzzer's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `test.Fuzzer_1.0.1.jar` has SHA-256 `e9bec542b722bcab3a0a8553f744eed3f48a13c7ac2aa34cb2de5aeb4fe0e2dd`. The current five-pair confirmation `b0a932ebbf882e6e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `00369794d689d9723d53193c1f38b8d7be8458db`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists test.Fuzzer 1.0.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `00369794d689d9723d53193c1f38b8d7be8458db`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,183.2 points and Tank Royale averaged 4,993.2 points. Pair deltas were -4.0%, -3.5%, -2.9%, -4.7%, -3.1%, for a -3.64% mean (Classic leads by 3.64%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The current 3.64% Classic lead falls within the recorded 25.0% threshold; the earlier observation recorded a 101.10% Tank Royale lead.

## What was tried

The earlier observation `3c7ff8aefc109aa0` recorded a +101.10% delta. The current five-pair mean is -3.64%; the registry reports `MATCHED (score noise)`. The retest command was `python compat_test.py --collections roborumble --only test.Fuzzer_1.0.1.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest places the score gap within the recorded noise threshold but does not explain why the earlier score discrepancy differed. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Fuzzer's five-pair sample has a 3.64% Classic score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

Continue with `roborumble/test.Podgy_4.0.jar` (`score-review`) in AN-671.

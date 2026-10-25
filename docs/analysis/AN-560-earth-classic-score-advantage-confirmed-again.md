---
id: AN-560
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Earth's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-560 — Earth's Classic score advantage is confirmed again

## Risk investigated

Does Earth's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `element.Earth_1.1.jar` has SHA-256 `cc1684fb62819ceb6fcfcb6bee7281ec76ea054e542a5a5ba324fa19fb8904f5`. The current five-pair confirmation `0d2e486780f4b69c` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists element.Earth 1.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,321.2 points and Tank Royale averaged 3,852.8 points. Pair deltas were -37.2%, -32.6%, -43.9%, -40.3%, -40.7%, for a -38.94% mean (Classic leads by 38.94%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

The latest five-pair confirmation `0fc1d7d941353712` completed on 2026-10-25 in the prepared Windows PowerShell environment, using Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `094bea25df50b33f3b9bafcb1fee7eb66883a27`. The locally built bridge API, wrapper, Bot API, and runner hashes are `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It again used two participants, 35 rounds, and an 800×600 arena; the exact Java executable was not retained.

All five latest pairs produced samples without engine errors or bridge-only signatures. Classic averaged 6,189.6 points and Tank Royale 3,642.0 points. Pair deltas were -46.0%, -40.4%, -32.3%, -39.7%, and -46.8%, with a -41.04% mean. All five Tank Royale attempts captured skipped-turn telemetry and recorded no skipped turns. This is prepared-environment statistical quality evidence, not deterministic acceptance proof; no confidence interval was computed.

## What was tried

The earlier observation `e9a5d46ed04f0534` recorded a -38.00% delta, and confirmation `0d2e486780f4b69c` recorded -38.94%. The latest confirmation `0fc1d7d941353712` records -41.04%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The latest five-pair sample confirms a 41.04% Classic score advantage, compared with 38.94% in the previous confirmation. The sample had no runtime errors or bridge-only error signatures, and skipped-turn telemetry recorded none.

## M-006 handoff

Continue with `roborumble/mld.Wisdom_1.0.jar` (`score-review`) in AN-561.

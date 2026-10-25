---
id: AN-563
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: NikeEnhanced's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-563 — NikeEnhanced's Tank Royale score advantage is confirmed again

## Risk investigated

Does NikeEnhanced's earlier Tank Royale score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `mme.NikeEnhanced_2.0.jar` has SHA-256 `6a6c5762dd9c8c4bc47c8bf0fe9f8224534d7045ceaad3c79b969ebdf66d517a`. The current five-pair confirmation `996b77c1db70ca8f` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `895138bd00f8af51a2fb79769271c8efc9c3e597`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists mme.NikeEnhanced 2.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `895138bd00f8af51a2fb79769271c8efc9c3e597`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,015.4 points and Tank Royale averaged 6,695.8 points. Pair deltas were +73.5%, +107.7%, +58.4%, +45.2%, +59.8%, for a +68.92% mean (Tank Royale leads by 68.92%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

The latest five-pair confirmation `bf9015a111d09de6` completed on 2026-10-25 in the prepared Windows PowerShell environment, using Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `094bea25df50b33f3b9bafcb1fee7eb66883a27a`. The locally built bridge API, wrapper, Bot API, and runner hashes were `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It again used two participants, 35 rounds, and an 800×600 arena; the exact Java executable and selected-name field were not retained.

All five latest pairs produced samples without engine errors or bridge-only signatures. Classic averaged 4,103.0 points and Tank Royale 6,430.8 points. Pair deltas were +37.2%, +52.2%, +55.1%, +76.0%, and +67.2%, for a +57.54% mean. All five Tank Royale attempts captured skipped-turn telemetry and recorded no skipped turns. This is prepared-environment statistical quality evidence, not deterministic acceptance proof; no confidence interval was computed.

## What was tried

The earlier observation `82d3439e0edbe4b4` recorded +65.50%, and confirmation `996b77c1db70ca8f` recorded +68.92%. The latest confirmation `bf9015a111d09de6` records +57.54%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The latest five-pair sample confirms a 57.54% Tank Royale score advantage, compared with 68.92% in the previous confirmation. The sample had no runtime errors or bridge-only signatures, and skipped-turn telemetry recorded none.

## M-006 handoff

Continue with `roborumble/mn.nano.perceptual.Impact_1.3.0.jar` (`score-review`) in AN-564.

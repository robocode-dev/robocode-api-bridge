---
id: AN-561
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Wisdom's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-561 — Wisdom's Tank Royale score advantage is confirmed again

## Risk investigated

Does Wisdom's earlier Tank Royale score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `mld.Wisdom_1.0.jar` has SHA-256 `f98eb1af9da5861e0afad2df1afff07642377ab8cea81a09c2860e489e186544`. The current five-pair confirmation `97762731ca62cb56` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists mld.Wisdom 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 2,183.6 points and Tank Royale averaged 2,885.4 points. Pair deltas were +73.2%, +23.3%, +19.0%, +54.7%, +8.6%, for a +35.76% mean (Tank Royale leads by 35.76%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

The latest five-pair confirmation `832d480420a93cd7` completed on 2026-10-25 in the prepared Windows PowerShell environment, using Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `094bea25df50b33f3b9bafcb1fee7eb66883a27a`. The locally built bridge API, wrapper, Bot API, and runner hashes were `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It again used two participants, 35 rounds, and an 800×600 arena; the exact Java executable and selected-name field were not retained.

All five latest pairs produced samples without engine errors or bridge-only signatures. Classic averaged 1,976.6 points and Tank Royale 2,838.2 points. Pair deltas were +18.6%, +62.7%, +35.0%, +47.3%, and +64.9%, for a +45.70% mean. All five Tank Royale attempts captured skipped-turn telemetry and recorded no skipped turns. This is prepared-environment statistical quality evidence, not deterministic acceptance proof; no confidence interval was computed.

## What was tried

The earlier observation `47a73446157b9ff6` recorded +37.50%, and confirmation `97762731ca62cb56` recorded +35.76%. The latest confirmation `832d480420a93cd7` records +45.70%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The latest five-pair sample confirms a 45.70% Tank Royale score advantage, compared with 35.76% in the previous confirmation. The sample had no runtime errors or bridge-only error signatures, and skipped-turn telemetry recorded none.

## M-006 handoff

Continue with `roborumble/mld.jdc.nano.LittleBlackBook_1.0.jar` in [AN-562](AN-562-littleblackbook-1-tank-royale-advantage-grows.md).

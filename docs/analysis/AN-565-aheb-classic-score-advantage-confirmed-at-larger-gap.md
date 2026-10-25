---
id: AN-565
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: AHEB's Classic score advantage is confirmed at a larger gap
provenance: inferred
reversal-cost: low
---

# AN-565 — AHEB's Classic score advantage is confirmed at a larger gap

## Risk investigated

Does AHEB's earlier Classic score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `mnt.AHEB_0.6a.jar` has SHA-256 `67649dbd7f93cddb1af7ff35fe2a48b51c6319b5d613415867b8b6037d1c0bac`. The current five-pair confirmation `488a04379aea3ddd` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `895138bd00f8af51a2fb79769271c8efc9c3e597`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists mnt.AHEB 0.6a for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `895138bd00f8af51a2fb79769271c8efc9c3e597`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,212.4 points and Tank Royale averaged 2,153.4 points. Pair deltas were -64.2%, -66.0%, -60.6%, -69.3%, -66.4%, for a -65.30% mean (Classic leads by 65.3%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

The latest five-pair confirmation `730bc0e3de5a6aeb` completed on 2026-10-25 in the prepared Windows PowerShell environment, using Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `094bea25df50b33f3b9bafcb1fee7eb66883a27a`. The locally built bridge API, wrapper, Bot API, and runner hashes were `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It again used two participants, 35 rounds, and an 800×600 arena; the exact Java executable and selected-name field were not retained.

All five latest pairs produced samples without engine errors or bridge-only signatures. Classic averaged 6,217.4 points and Tank Royale 2,670.0 points. Pair deltas were -62.8%, -59.6%, -52.7%, -53.5%, and -55.8%, for a -56.88% mean. All five Tank Royale attempts captured skipped-turn telemetry and recorded no skipped turns. This is prepared-environment statistical quality evidence, not deterministic acceptance proof; no confidence interval was computed.

## What was tried

The earlier observation `f17bdd7a3c104b3c` recorded -60.40%, and confirmation `488a04379aea3ddd` recorded -65.30%. The latest confirmation `730bc0e3de5a6aeb` records -56.88%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a larger Classic score advantage but does not explain the change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

AHEB's latest five-pair sample confirms a 56.88% Classic score advantage, smaller than the previous 65.30% confirmation but still above the 25% band. It had no runtime errors or bridge-only signatures, and skipped-turn telemetry recorded none.

## M-006 handoff

Continue with `roborumble/mrm.MightyMoose_.2.jar` (`score-review`) in AN-566.

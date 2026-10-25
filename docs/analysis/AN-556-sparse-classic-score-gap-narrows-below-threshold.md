---
id: AN-556
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Sparse's Classic score gap narrows below threshold
provenance: inferred
reversal-cost: low
---

# AN-556 — Sparse's Classic score gap narrows below threshold

## Risk investigated

Does Sparse's historical Classic score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `ebo.Sparse_0.02.jar` has SHA-256 `871ad9964dc27ed22be3491e09516a3bbf67e6c0a4d065ae9343c45b31603e74`. The current retest `226776907c09cc68` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ebo.Sparse 0.02 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All 5 valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

The new official retest `265236e179238fe0` completed on 2026-10-25 with Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `96bdfd7957f0506cd5a84043ec93d3fd7fd1cd4a`. The read-only robot jar SHA-256 is `871ad9964dc27ed22be3491e09516a3bbf67e6c0a4d065ae9343c45b31603e74`; the locally built bridge API, wrapper, Bot API, and runner hashes are respectively `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It used the official two-participant, 35-round, 800×600 setup. The observation does not retain the exact Java executable or Classic selected-name field.

All five pairs in the new retest produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,474.2 points and Tank Royale averaged 4,123.8 points. Pair deltas were -30.4%, -20.1%, -27.1%, -23.4%, and -22.5%, for a -24.7% mean. The registry status is `CONFIRMED (score)`: repeated confirmation uses the 15.0-point regression band, while the 25.0% manifest threshold is used for first-pass discrepancy selection. This retest did not enable bridge skipped-turn or scan-event telemetry.

## What was tried

The earlier observation `9da95784397a1e18` recorded a -34.30% delta, and `226776907c09cc68` recorded -24.16%. The latest five-pair observation `265236e179238fe0` records -24.7%; the registry reports `CONFIRMED (score)` under its 15.0-point repeated-confirmation band.

## What was not pursued

The read-only jar contains no bundled source, and its `ebo.Sparse` implementation is obfuscated. Bytecode shows that it overrides `onSkippedTurn` and prints a per-round count. In the retained output from the fifth Tank Royale pair, each staged robot copy printed `Skipped Turns: 0` for all 36 recorded rounds; both stdout files have SHA-256 `afbdc83f8c2b37708b7c98908759edd797e646dcc4a255f6f23a7cbd08ec7a8c`. This narrows the current sample away from skipped turns but does not explain the score difference or establish that the other four pairs had no skipped turns. No controlled state trace, bridge code change, or rumble-jar change was made.

## Finding

Sparse's latest five-pair sample shows a 24.7% Classic score lead, down from 34.3% in the earlier observation. The registry classifies it as `CONFIRMED (score)` because repeated confirmation uses a 15.0-point regression band; the new mean remains below the 25.0% first-pass selection threshold. The engines reported no runtime errors. The fifth pair's retained stdout shows no skipped turns for either robot copy, but the source is obfuscated and the bridge cause remains undetermined.

## M-006 handoff

IWillFireNoBullet is recorded in [AN-557](AN-557-iwillfirenobullet-tank-royale-advantage-confirmed-again.md) with a +266.1% Tank Royale score advantage. Continue with `roborumble/ej.ChocolateBar_1.1.jar` (`score-review`) in AN-558.

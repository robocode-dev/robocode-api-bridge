---
id: AN-558
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: ChocolateBar's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-558 — ChocolateBar's Classic score advantage is confirmed again

## Risk investigated

Does ChocolateBar's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `ej.ChocolateBar_1.1.jar` has SHA-256 `9ebfcc8e298c6fad8045e8e3f44418be6f2c5c74322fb6d50f71e698005e4a52`. The current five-pair confirmation `55997df09ab76651` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ej.ChocolateBar 1.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

The new official retest `f2ebb876dec17bef` completed on 2026-10-25 with Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `96bdfd7957f0506cd5a84043ec93d3fd7fd1cd4a`. The read-only robot jar SHA-256 is `9ebfcc8e298c6fad8045e8e3f44418be6f2c5c74322fb6d50f71e698005e4a52`; the locally built bridge API, wrapper, Bot API, and runner hashes are respectively `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It used the official two-participant, 35-round, 800×600 setup. The observation does not retain the exact Java executable or Classic selected-name field.

All five pairs in the new retest produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,508.4 points and Tank Royale averaged 1,993.0 points. Pair deltas were -60.1%, -60.6%, -55.5%, -63.3%, and -27.9%, for a -53.48% mean. The registry status remains `CONFIRMED (score)`. This retest did not enable skipped-turn or scan-event telemetry.

## What was tried

The earlier observation `b47ad477d6bd24cb` recorded -63.30%, and `55997df09ab76651` recorded -55.88%. The latest five-pair observation `f2ebb876dec17bef` records -53.48%; the registry still reports `CONFIRMED (score)`.

## What was not pursued

The jar has no bundled source. Read-only bytecode inspection shows an `AdvancedRobot` using a continuous radar sweep, a custom guess-factor model, and `getDataFile(enemyName)` to load and save its model. The staged Tank Royale bot directories contain per-opponent data files keyed by the anonymous `tankroyale.anonymous.BotN 1.0` names; the Classic run uses Classic opponent names. This is an observable input difference for a bot that keys data by opponent name, but the retained files are empty and the measurement does not show that it caused the score gap. The hidden-name behavior is part of the accepted privacy boundary recorded in [AN-708](AN-708-eraser-name-based-self-check-hits-privacy-boundary.md). No controlled state trace, bridge code change, or rumble-jar change was made.

## Finding

The latest five-pair sample confirms a 53.48% Classic score advantage, compared with 63.3% in the earlier observation. The sample had no runtime errors or bridge-only error signatures. The robot's model is keyed by opponent names, but the current evidence does not establish that anonymous names caused its score difference.

## M-006 handoff

Continue with `roborumble/el33t.EL33tGangstarr2_2.0.jar` (`score-review`) in AN-559.

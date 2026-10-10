---
id: AN-653
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Bicephal's Classic score advantage confirmed despite one near-even pair
provenance: inferred
reversal-cost: low
---

# AN-653 — Bicephal's Classic score advantage confirmed despite one near-even pair

## Risk investigated

Does Bicephal's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `sul.Bicephal_1.2.jar` has SHA-256 `acf9ac032f1e33b72260d4f5442551dbc45ad23402cc45552a67659389930a3a`. The current five-pair confirmation `ea90c81159402c73` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `28d23c2e5797f1c8ca38b6162ef53366c12dba4d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists sul.Bicephal 1.2 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `28d23c2e5797f1c8ca38b6162ef53366c12dba4d`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 10,916.8 points and Tank Royale averaged 5,738.6 points. Pair deltas were -58.5%, -2.1%, -60.3%, -59.5%, -56.3%, for a -47.34% mean (Classic leads by 47.34%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events. Four pair deltas show a Classic lead of 56.3% to 60.3%, while one pair was near even at -2.1%. All five valid pairs were retained; this variation is part of the observed sample.

## What was tried

The earlier observation `76e39a0c93333f46` recorded a -57.70% delta. The current five-pair mean is -47.34%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only sul.Bicephal_1.2.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the recorded score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Bicephal's five-pair sample confirms a 47.34% Classic score advantage: Classic averaged 10,916.8 points and Tank Royale averaged 5,738.6. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/sul.BlueBot_1.0.jar` (`score-review`) in AN-654.

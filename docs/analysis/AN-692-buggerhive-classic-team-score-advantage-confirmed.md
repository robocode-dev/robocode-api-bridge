---
id: AN-692
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: BuggerHive's Classic team score advantage confirmed
provenance: inferred
reversal-cost: low
---

# AN-692 — BuggerHive's Classic team score advantage confirmed

## Risk investigated

Does BuggerHive's earlier team score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only team jar `bugger.BuggerHive_1.0.jar` has SHA-256 `9a1c7c53e4e00d9332ea92c2750d4c5bf6d50ccbf23e412575e7ae3d18c7f765`. The current five-pair confirmation `220a730263470dc4` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `338c1f72cb5feadd921266288476d748badc2090`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell team battle used 2 participants, 10 rounds, and a 1200×1200 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists `bugger.HiveQueen [1.0]` for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `338c1f72cb5feadd921266288476d748badc2090`; the population was its `teamrumble` team rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 19,630.2 points and Tank Royale averaged 11,714.6 points. Pair deltas were -38.1%, -47.6%, -36.6%, -40.5%, -38.8%, for a -40.32% mean (Classic leads by 40.32%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured on all five Tank Royale attempts; attempts 1 and 4 recorded 4 and 5 events respectively, while attempts 2, 3, and 5 recorded none.

## What was tried

The earlier observation `d964a1c4da7e73fa` recorded a +35.20% delta. The current five-pair mean is -40.32%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections teamrumble --only bugger.BuggerHive_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the Classic team score advantage but does not explain its cause or change from the earlier Tank Royale lead. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

BuggerHive's five-pair sample confirms a 40.32% Classic team score advantage: Classic averaged 19,630.2 points and Tank Royale averaged 11,714.6. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

No further `score-review` rows remain in the registry after this retest.

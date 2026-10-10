---
id: AN-677
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: MilkyWay's Tank Royale score advantage confirmed just above the threshold
provenance: inferred
reversal-cost: low
---

# AN-677 — MilkyWay's Tank Royale score advantage confirmed just above the threshold

## Risk investigated

Does MilkyWay's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `uccc.MilkyWay_1.01.jar` has SHA-256 `3ff7522221792521e3a70a78d42aca1bf003a605c68b6be4f658bbd0aaa03697`. The current five-pair confirmation `786951b2a58fa25a` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5c6fe6fa7f4131dbb376887c90964e8f3f3d869d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists uccc.MilkyWay 1.01 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5c6fe6fa7f4131dbb376887c90964e8f3f3d869d`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,981.2 points and Tank Royale averaged 8,761.4 points. Pair deltas were +45.3%, +23.4%, +20.3%, +21.3%, +17.8%, for a +25.62% mean (Tank Royale leads by 25.62%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The 25.62% mean is 0.62 percentage points above the 25.0% threshold. Only one pair delta exceeds 25%; the other four range from +17.8% to +23.4%. The registry classifies the mean as `CONFIRMED (score)`.

## What was tried

The earlier observation `41f6d0786fd3e5ce` recorded a +26.00% delta. The current five-pair mean is +25.62%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only uccc.MilkyWay_1.01.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

MilkyWay's five-pair sample confirms a 25.62% Tank Royale score advantage: Classic averaged 6,981.2 points and Tank Royale averaged 8,761.4. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/uccc.Scrapple_1.0.jar` (`score-review`) in AN-678.

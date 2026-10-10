---
id: AN-680
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Stampede2's Tank Royale score gap falls below threshold despite confirmed status
provenance: inferred
reversal-cost: low
---

# AN-680 — Stampede2's Tank Royale score gap falls below threshold despite confirmed status

## Risk investigated

Does Stampede2's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `wcsv.Stampede2.Stampede2_1.1.0.jar` has SHA-256 `b53af62821e743e0c85f750334af88d96b16c4e1c56409cbce16923626a6806b`. The current five-pair confirmation `c95b79b74ad88178` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5c6fe6fa7f4131dbb376887c90964e8f3f3d869d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists wcsv.Stampede2.Stampede2 1.1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5c6fe6fa7f4131dbb376887c90964e8f3f3d869d`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,744.0 points and Tank Royale averaged 5,521.2 points. Pair deltas were +24.4%, +11.3%, +8.4%, +22.2%, +15.9%, for a +16.44% mean (Tank Royale leads by 16.44%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The mean Tank Royale lead is 16.44%, below the recorded 25.0% threshold, while the registry reports `CONFIRMED (score)`. Every pair delta is below 25%, ranging from +8.4% to +24.4%. The retest does not establish why the recorded status differs from the mean.

## What was tried

The earlier observation `b9408606ff177d2e` recorded a +27.30% delta. The current five-pair mean is +16.44%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only wcsv.Stampede2.Stampede2_1.1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The measured mean is below threshold even though the registry reports `CONFIRMED (score)`; this note does not infer the classifier's reason. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The five-pair mean records a 16.44% Tank Royale score lead, below the 25.0% threshold, while the registry status is `CONFIRMED (score)`. Both the measurement and status are retained; the reason for the difference remains undetermined.

## M-006 handoff

Continue with `roborumble/wiki.BasicGFSurfer_1.02.jar` (`score-review`) in AN-681.

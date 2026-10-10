---
id: AN-628
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: ButtHead's Tank Royale score advantage is confirmed at low Classic score
provenance: inferred
reversal-cost: low
---

# AN-628 — ButtHead's Tank Royale score advantage is confirmed at low Classic score

## Risk investigated

Does ButtHead's earlier Tank Royale score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `slugzilla.ButtHead_2.0.jar` has SHA-256 `f02819b9310fab95f832e54dcb68610bdd2e0fa1715e5e12468f7544627067f2`. The current five-pair confirmation `ccb3d7fd7d4384ac` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `dca1553a9d01ee1be201819f58a06addad69368c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists slugzilla.ButtHead 2.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `dca1553a9d01ee1be201819f58a06addad69368c`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 1,086.8 points and Tank Royale averaged 12,042.4 points. Pair deltas were +1,527.5%, +1,465.5%, +1,081.4%, +831.5%, +258.5%, for a +1,032.88% mean (Tank Royale leads by 1032.88%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `068ed7ab5d4b7278` recorded a +684.70% delta. The current five-pair mean is +1,032.88%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a very large relative score difference but does not explain its cause. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

ButtHead's five-pair sample confirms a 1032.88% Tank Royale score advantage. Classic averaged 1,086.8 points and Tank Royale averaged 12,042.4; observed pair deltas ranged from +258.5% to +1,527.5%. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/slugzilla.OrbitGF_1.0.jar` (`score-review`) in AN-629.

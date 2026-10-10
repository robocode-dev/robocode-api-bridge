---
id: AN-687
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Kowari's Tank Royale score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-687 — Kowari's Tank Royale score advantage confirmed again

## Risk investigated

Does Kowari's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `wompi.Kowari_1.6.jar` has SHA-256 `5453968848c1c68c35d7d66b37ed9cfcd30a10bafe856fbf0a22490b76d887d0`. The current five-pair confirmation `13138d68545ea672` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `b02af471b96c88110f1b8d6bd4ebb95b8ef1fa29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists wompi.Kowari 1.6 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `b02af471b96c88110f1b8d6bd4ebb95b8ef1fa29`; the population was its `roborumble robot rows`, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 10,177.4 points and Tank Royale averaged 14,386.0 points. Pair deltas were +52.1%, +54.1%, +31.7%, +17.3%, +51.4%, for a +41.32% mean (Tank Royale leads by 41.32%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded.

## What was tried

The earlier observation `aee185f8772f9bc8` recorded a +39.40% delta. The current five-pair mean is +41.32%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only wompi.Kowari_1.6.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Kowari's five-pair sample confirms a 41.32% Tank Royale score advantage: Classic averaged 10,177.4 points and Tank Royale averaged 14,386.0. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/yagami.Tidus_0.11.jar` (`score-review`) in AN-688.

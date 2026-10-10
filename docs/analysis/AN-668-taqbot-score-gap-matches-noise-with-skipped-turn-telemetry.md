---
id: AN-668
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: taqbot's Score gap matches noise with substantial skipped-turn telemetry
provenance: inferred
reversal-cost: low
---

# AN-668 — taqbot's Score gap matches noise with substantial skipped-turn telemetry

## Risk investigated

Does taqbot's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `taqho.taqbot_1.0.jar` has SHA-256 `50ed2ab0decf7087387ec3039a9563ff6a9381991aa97fc7849623d4ca12a382`. The current five-pair confirmation `5b997989306dd7cd` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `00369794d689d9723d53193c1f38b8d7be8458db`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists taqho.taqbot 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `00369794d689d9723d53193c1f38b8d7be8458db`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,440.8 points and Tank Royale averaged 5,714.4 points. Pair deltas were +9.3%, +32.3%, -8.6%, +6.7%, -5.2%, for a +6.90% mean (Tank Royale leads by 6.90%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. The five-pair retest took 13.0 minutes to complete. Skipped-turn telemetry was unavailable for attempt 1. Attempts 2–5 were captured with 379, 767, 214, and 897 skipped-turn events respectively. These telemetry observations are recorded separately; this retest does not establish that they explain the score measurement. The five-pair retest took 13.0 minutes to complete. Skipped-turn telemetry was unavailable for attempt 1. Attempts 2–5 were captured with 379, 767, 214, and 897 skipped-turn events respectively. These telemetry observations are recorded separately; this retest does not establish that they explain the score measurement.

## What was tried

The earlier observation `6ec301502f0dfec6` recorded a +60.40% delta. The current five-pair mean is +6.90%; the registry reports `MATCHED (score noise)`. The retest command was `python compat_test.py --collections roborumble --only taqho.taqbot_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest places the score gap within the recorded noise threshold but does not explain why the earlier score discrepancy differed. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

taqbot's five-pair sample has a 6.90% Tank Royale score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

Continue with `roborumble/techdude.kombat.FlamingKombat_1.5.jar` (`score-review`) in AN-669.

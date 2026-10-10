---
id: AN-666
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: SpiralCrash's Score gap matches observed noise after an earlier discrepancy
provenance: inferred
reversal-cost: low
---

# AN-666 — SpiralCrash's Score gap matches observed noise after an earlier discrepancy

## Risk investigated

Does SpiralCrash's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `takeBot.SpiralCrash_1.0.jar` has SHA-256 `b14701c121c82e1e025e1e69dacf783521b00ea54bfa5c4d6793ac31d219ca6f`. The current five-pair confirmation `0f793ac9c4bae831` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists takeBot.SpiralCrash 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 9,379.0 points and Tank Royale averaged 9,573.6 points. Pair deltas were +2.8%, +0.8%, -1.1%, +7.9%, -0.2%, for a +2.04% mean (Tank Royale leads by 2.04%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events. The current +2.04% mean is within the 25.0% threshold, while the earlier observation recorded -35.00%. This retest does not explain the change.

## What was tried

The earlier observation `fc13dea89be82484` recorded a -35.00% delta. The current five-pair mean is +2.04%; the registry reports `MATCHED (score noise)`. The retest command was `python compat_test.py --collections roborumble --only takeBot.SpiralCrash_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest places the score gap within the recorded noise threshold but does not explain why the earlier score discrepancy differed. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

SpiralCrash's five-pair sample has a 2.04% Tank Royale score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

The WeavingWiggle retest is recorded in AN-667; continue with `roborumble/taqho.taqbot_1.0.jar` (`score-review`) in AN-668.

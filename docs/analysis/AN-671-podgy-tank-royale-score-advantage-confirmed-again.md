---
id: AN-671
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Podgy's Tank Royale score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-671 — Podgy's Tank Royale score advantage confirmed again

## Risk investigated

Does Podgy's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `test.Podgy_4.0.jar` has SHA-256 `d3f2f8771f490a35f2e25115d17d4c5de2c04a45b49a98c305e697adb3913e17`. The current five-pair confirmation `27a49fad42e73de2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `00369794d689d9723d53193c1f38b8d7be8458db`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists test.Podgy 4.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `00369794d689d9723d53193c1f38b8d7be8458db`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,171.4 points and Tank Royale averaged 9,725.6 points. Pair deltas were +81.0%, +108.9%, +71.4%, +91.2%, +91.3%, for a +88.76% mean (Tank Royale leads by 88.76%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. All five pair deltas show a Tank Royale lead, ranging from 71.4% to 108.9%.

## What was tried

The earlier observation `fbc9e6ea567d3568` recorded a +76.70% delta. The current five-pair mean is +88.76%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only test.Podgy_4.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Podgy's five-pair sample records a 88.76% Tank Royale score lead: Classic averaged 5,171.4 points and Tank Royale averaged 9,725.6. The registry status is `CONFIRMED (score)`; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/tide.pear.Pear_0.62.1.jar` (`score-review`) in AN-672.

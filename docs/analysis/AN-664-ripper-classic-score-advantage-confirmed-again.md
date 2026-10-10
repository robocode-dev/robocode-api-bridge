---
id: AN-664
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Ripper's Classic score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-664 — Ripper's Classic score advantage confirmed again

## Risk investigated

Does Ripper's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `t3.Ripper_1.0.jar` has SHA-256 `7b09daef7f0312ade7cc84f561bcab2fc8b1a131b6e00e7577c30c42b79f1ff6`. The current five-pair confirmation `498fb26975e0a196` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists t3.Ripper 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `0a3e5df8e399c5cf6dd1b5acfb9cce0e6fd395c7`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 7,111.8 points and Tank Royale averaged 4,395.6 points. Pair deltas were -35.6%, -28.2%, -42.7%, -40.0%, -44.4%, for a -38.18% mean (Classic leads by 38.18%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events. All five pair deltas show a Classic lead, ranging from 28.2% to 44.4%.

## What was tried

The earlier observation `805d2e1260db693b` recorded a -31.00% delta. The current five-pair mean is -38.18%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only t3.Ripper_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Ripper's five-pair sample records a 38.18% Classic score lead: Classic averaged 7,111.8 points and Tank Royale averaged 4,395.6. The registry status is `CONFIRMED (score)`; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/takeBot.SpinSpiral_1.2.jar` (`score-review`) in AN-665.

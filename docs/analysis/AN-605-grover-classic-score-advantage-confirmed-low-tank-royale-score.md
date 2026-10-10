---
id: AN-605
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Grover's Classic score advantage is confirmed at low Tank Royale score
provenance: inferred
reversal-cost: low
---

# AN-605 — Grover's Classic score advantage is confirmed at low Tank Royale score

## Risk investigated

Does Grover's earlier Classic score advantage persist under current matched artifacts, and does Tank Royale score improve?

## Evidence boundary

The read-only subject jar `reeder.caden.Grover_1.0.jar` has SHA-256 `f0968d0b127ff1f396b06038cb0fcbc30d4ec635284fa010b4cddee690b9ddbc`. The current five-pair confirmation `05fa2aeca590261e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6803017b6f4db5f8788f5d3dd06861f329e1273f`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists reeder.caden.Grover 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `6803017b6f4db5f8788f5d3dd06861f329e1273f`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,489.2 points and Tank Royale averaged 855.4 points. Pair deltas were -82.3%, -82.7%, -85.9%, -84.3%, -86.9%, for a -84.42% mean (Classic leads by 84.42%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `a627c76b25dd0572` recorded a -84.80% delta. The current five-pair mean is -84.42%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a large Classic score advantage but does not explain why Tank Royale scores remain low. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Grover's five-pair sample confirms a 84.42% Classic score advantage: Classic averaged 5,489.2 points and Tank Royale averaged 855.4. Both engines reported no runtime errors; the reason for the low Tank Royale score remains undetermined.

## M-006 handoff

Continue with `roborumble/robar.micro.Gladius_1.15.jar` (`score-review`) in AN-606.

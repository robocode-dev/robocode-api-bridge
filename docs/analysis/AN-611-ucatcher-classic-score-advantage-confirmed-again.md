---
id: AN-611
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: uCatcher's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-611 — uCatcher's Classic score advantage is confirmed again

## Risk investigated

Does uCatcher's earlier score discrepancy persist under current matched artifacts, and which engine leads?

## Evidence boundary

The read-only subject jar `rsim.micro.uCatcher_0.1.jar` has SHA-256 `6f10b1c4562abafb1eead6dcf810b285b705b73f29e062ae899e0e4467bcc12d`. The current five-pair confirmation `d8c43299c5876751` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `bba433e428e4305b180f746963b3870df544010b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists rsim.micro.uCatcher 0.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `bba433e428e4305b180f746963b3870df544010b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 8,713.6 points and Tank Royale averaged 5,261.2 points. Pair deltas were -21.9%, -36.2%, -42.5%, -53.0%, -41.0%, for a -38.92% mean (Classic leads by 38.92%). Pair deltas ranged from -21.9% to -53.0%, so not every pair exceeded the 25.0% threshold. The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `e47dc8233c05ce71` recorded a -42.80% delta. The current five-pair mean is -38.92%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a score difference but does not explain its cause. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

uCatcher's five-pair sample confirms a 38.92% Classic score advantage: Classic averaged 8,713.6 points and Tank Royale averaged 5,261.2. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

The VirtualGunExperiment retest is recorded in AN-612; continue with `roborumble/ry.Worst_1.0.jar` (`score-review`) in AN-613.

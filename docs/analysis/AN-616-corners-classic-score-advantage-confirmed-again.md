---
id: AN-616
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Corners' Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-616 — Corners' Classic score advantage is confirmed again

## Risk investigated

Does Corners' earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `sample.Corners_1.0.jar` has SHA-256 `41f641daba30b8ff817157a95370cc913563c11cd7d1d23a82de100711d9687f`. The current five-pair confirmation `16a08e0e6fbde79f` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `aa1baaec2961a82966280835ba0b66be39a8208b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists sample.Corners 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `aa1baaec2961a82966280835ba0b66be39a8208b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 13,637.2 points and Tank Royale averaged 7,959.0 points. Pair deltas were -42.3%, -38.0%, -41.5%, -42.2%, -44.1%, for a -41.62% mean (Classic leads by 41.62%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `2d5107dbd6d0ded4` recorded a -33.40% delta. The current five-pair mean is -41.62%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Corners' five-pair sample confirms a 41.62% Classic score advantage: Classic averaged 13,637.2 points and Tank Royale averaged 7,959.0. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/sample.MyFirstJuniorRobot_1.0.jar` (`score-review`) in AN-617.

---
id: AN-546
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: MCool's Tank Royale score advantage is confirmed below threshold
provenance: inferred
reversal-cost: low
---

# AN-546 — MCool's Tank Royale score advantage is confirmed below threshold

## Risk investigated

Whether `metal.small.MCool_1.21.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and how the measured mean compares with the recorded threshold.

## Evidence boundary

The read-only subject jar has SHA-256 `cd1dd4593985a1a8f571acccf38afbc4ea9c45cf13bd9d9ad0d19c17bb9cd5f2`. The official five-pair confirmation `102fb4fda9a00875` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9a418cc3a44d3944159ed054675754d17ca3b245`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9a418cc3a44d3944159ed054675754d17ca3b245`; the population was its `roborumble` robot rows, and MCool was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,026.6 points and Tank Royale averaged 6,152.8 points. Pair deltas were +18.4%, +15.9%, +17.1%, +29.7%, and +32.0%, for a +22.62% mean. The confirmation manifest's threshold is 25.0%; the registry status is nevertheless `CONFIRMED (score)`, which is recorded without inferring the classifier's reason. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 1 recorded bots 1 and 2 at round 22, turn 59, attempt 2 recorded bot 1 at round 20, turn 61, and attempts 3–5 recorded none.

## What was tried

The earlier observation `2f3bc23f806cf0b6` recorded a +26.3% delta. The current five-pair mean still favors Tank Royale at +22.62%, below the 25.0% manifest threshold; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a score difference but does not explain why it changed or why the registry assigns `CONFIRMED (score)` below the recorded threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

MCool's Tank Royale advantage persists in the five-pair sample at +22.62%, slightly below the recorded 25.0% threshold and below the earlier +26.3% delta. The registry classifies it as `CONFIRMED (score)`; the cause and classification remain unexplained.

## M-006 handoff

Continue with `roborumble/mladjo.GnuKlub_0.1.jar` (`score-review`) in AN-547.

---
id: AN-589
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Mercutio's score advantage reverses direction
provenance: inferred
reversal-cost: low
---

# AN-589 — Mercutio's score advantage reverses direction

## Risk investigated

Does Mercutio's earlier Classic score advantage persist under current matched artifacts, or has the score direction changed?

## Evidence boundary

The read-only subject jar `oog.melee.Mercutio_1.0.jar` has SHA-256 `7764f3b249077f2116d0cb4a1698534d0b19e9bb668295a5dc02bf0e0cfb827e`. The current five-pair confirmation `e36bac2855593c50` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9d8c04d240e231da6175156328b434fdc8a0e3ac`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists oog.melee.Mercutio 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9d8c04d240e231da6175156328b434fdc8a0e3ac`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,888.2 points and Tank Royale averaged 7,795.4 points. Pair deltas were +28.9%, +31.4%, +36.0%, +33.7%, +32.1%, for a +32.42% mean (Tank Royale leads by 32.42%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `1b8ff814bf573b6a` recorded a -61.10% delta. The current five-pair mean is +32.42%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a score direction reversal but does not explain its cause. The old and new observations used different artifact versions, so the reversal is not attributed to a specific bridge change. No controlled trace or source comparison was made.

## Finding

Mercutio's score direction reversed from a 61.1% Classic advantage to a 32.42% Tank Royale advantage. The current five-pair sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/oog.nano.Caligula_1.15.jar` (`score-review`) in AN-590.

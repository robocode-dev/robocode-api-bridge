---
id: AN-588
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Capulet's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-588 — Capulet's Tank Royale score advantage is confirmed again

## Risk investigated

Does Capulet's earlier Tank Royale score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `oog.melee.Capulet_1.2.jar` has SHA-256 `729f924e59c1b3c1cfc2ceb7bd61d75ade56ca8498051737bbee44789d50b5aa`. The current five-pair confirmation `368fca069c9d26ee` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9d8c04d240e231da6175156328b434fdc8a0e3ac`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists oog.melee.Capulet 1.2 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9d8c04d240e231da6175156328b434fdc8a0e3ac`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,538.8 points and Tank Royale averaged 13,544.6 points. Pair deltas were +133.1%, +163.4%, +123.8%, +152.4%, +150.9%, for a +144.72% mean (Tank Royale leads by 144.72%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `ee56d3e5562790dc` recorded a +156.40% delta. The current five-pair mean is +144.72%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a slightly smaller Tank Royale score advantage but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Capulet's five-pair sample confirms a 144.72% Tank Royale score advantage, down from 156.4% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/oog.melee.Mercutio_1.0.jar` (`score-review`) in AN-589.

---
id: AN-593
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: TheHulk's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-593 — TheHulk's Classic score advantage is confirmed again

## Risk investigated

Does TheHulk's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `paolord.TheHulk_1.0.jar` has SHA-256 `f285bd5a9a3bd54afe3c02eb907b9b28992cb56014317f07d0b62d6bf87204ca`. The current five-pair confirmation `6e06b3e1e59d805f` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `abb7e441763a97998bf3d442910e70ce452f9e52`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists paolord.TheHulk 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `abb7e441763a97998bf3d442910e70ce452f9e52`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 9,565.2 points and Tank Royale averaged 6,264.8 points. Pair deltas were -23.0%, -44.9%, -29.9%, -42.1%, -28.8%, for a -33.74% mean (Classic leads by 33.74%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `b69ddb01a016a30b` recorded a -26.60% delta. The current five-pair mean is -33.74%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a larger Classic score advantage but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

TheHulk's five-pair sample confirms a 33.74% Classic score advantage, compared with 26.6% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/paulk.PaulV3_1.7.jar` (`score-review`) in AN-594.

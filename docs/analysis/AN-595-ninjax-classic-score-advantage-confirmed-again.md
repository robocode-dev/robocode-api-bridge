---
id: AN-595
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: NinjaX's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-595 — NinjaX's Classic score advantage is confirmed again

## Risk investigated

Does NinjaX's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `pbg.NinjaX_1.2.jar` has SHA-256 `9cecfc7152696164fe4fb12b2211d9ae8fa0d543257b6d96ed0d9dbd95f17236`. The current five-pair confirmation `0b8011888814fd78` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `abb7e441763a97998bf3d442910e70ce452f9e52`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists pbg.NinjaX 1.2 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `abb7e441763a97998bf3d442910e70ce452f9e52`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,626.4 points and Tank Royale averaged 3,461.4 points. Pair deltas were -39.7%, -41.4%, -38.1%, -36.2%, -36.6%, for a -38.40% mean (Classic leads by 38.4%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `b97a060c77e8cbd9` recorded a -32.40% delta. The current five-pair mean is -38.40%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a larger Classic score advantage but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

NinjaX's five-pair sample confirms a 38.4% Classic score advantage, compared with 32.4% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/pez.micro.Aristocles_0.3.7.jar` (`score-review`) in AN-596.

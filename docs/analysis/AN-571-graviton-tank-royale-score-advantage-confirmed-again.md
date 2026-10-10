---
id: AN-571
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Graviton's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-571 — Graviton's Tank Royale score advantage is confirmed again

## Risk investigated

Does Graviton's earlier Tank Royale score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `myl.nano.Graviton_1.10.jar` has SHA-256 `4c6f85e03f5f67e918674e9ef0b5ec259998422e354c8e95ee33a3347fbcc212`. The current five-pair confirmation `cf68b0f16fc6e434` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `de043930e0bf34d4b576923ab49afc91078e4e14`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists myl.nano.Graviton 1.10 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `de043930e0bf34d4b576923ab49afc91078e4e14`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 3,381.0 points and Tank Royale averaged 13,815.0 points. Pair deltas were +293.0%, +334.5%, +302.4%, +335.2%, +279.0%, for a +308.82% mean (Tank Royale leads by 308.82%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `42f21d05faa46521` recorded a +281.50% delta. The current five-pair mean is +308.82%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a larger Tank Royale score advantage but does not explain the change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Graviton's Tank Royale score advantage grew from +281.5% to +308.82% in the five-pair sample. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/myl.nano.KomoriNinja_1.1.jar` (`score-review`) in AN-572.

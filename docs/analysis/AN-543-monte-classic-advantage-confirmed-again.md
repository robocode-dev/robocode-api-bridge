---
id: AN-543
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Monte's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-543 — Monte's Classic score advantage is confirmed again

## Risk investigated

Whether `mb.Monte_0.1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `fcfc2b8af47f081d1c5de35a8c0ebc92109a74d78fa6e5d2716950894390ae73`. The official five-pair confirmation `fbc3b510903834bb` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9a418cc3a44d3944159ed054675754d17ca3b245`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9a418cc3a44d3944159ed054675754d17ca3b245`; the population was its `roborumble` robot rows, and Monte was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,537.6 points and Tank Royale averaged 3,174.2 points. Pair deltas were −61.5%, −56.4%, −52.5%, −37.4%, and −47.4%, for a −51.04% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts, with no events. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `f52fa9153bebc76b` recorded a −44.1% delta. The current five-pair mean confirms the same Classic score advantage at −51.04%, with a larger gap.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Monte's Classic score advantage persists under current matched artifacts. The mean gap is larger than the earlier −44.1% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/mc.Messapia_0.1.8.jar` (`score-review`) in AN-544.

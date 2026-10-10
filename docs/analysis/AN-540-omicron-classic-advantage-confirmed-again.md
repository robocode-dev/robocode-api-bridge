---
id: AN-540
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Omicron's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-540 — Omicron's Classic score advantage is confirmed again

## Risk investigated

Whether `maribo.Omicron_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `2079ad27794bd657b1e2aabe07afbb0e3800f05a9224b30a1aa4cb146d2ab922`. The official five-pair confirmation `293511f94a2c00ba` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source is `compat-test/parity-registry.json` at starting commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`; the population is its `roborumble` robot rows, and Omicron was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. The pair deltas report observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence, not deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,530.6 points and Tank Royale averaged 2,738.4 points. Pair deltas were −32.5%, −32.2%, −41.4%, −49.1%, and −42.1%, for a −39.46% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts, with no events. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `531052ecdf1582a2` recorded a −41.6% delta. The current five-pair mean confirms a similar Classic score advantage at −39.46%.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Omicron's Classic score advantage persists under current matched artifacts. The mean gap is slightly smaller than the earlier −41.6% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/marksteam.Phoenix_1.0.jar` (`score-review`) in AN-541.

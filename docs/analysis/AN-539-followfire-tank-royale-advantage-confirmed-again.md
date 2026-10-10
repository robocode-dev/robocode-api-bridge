---
id: AN-539
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: FollowFire's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-539 — FollowFire's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `maribo.FollowFire_1.11.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `967c1664e3ae827c9fcec77b419184f6f65e737e18eb568d4ca1c48eba6b205e`. The official five-pair confirmation `53ddf6d2a090587d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source is `compat-test/parity-registry.json` at starting commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`; the population is its `roborumble` robot rows, and FollowFire was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. The pair deltas report observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence, not deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 10,513.0 points and Tank Royale averaged 16,326.2 points. Pair deltas were +55.9%, +54.9%, +58.1%, +54.2%, and +53.5%, for a +55.32% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts, with no events. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `8c3f1a7c4da1872b` recorded a +59.1% delta. The current five-pair mean confirms the same Tank Royale advantage at +55.32%.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

FollowFire's Tank Royale score advantage persists under current matched artifacts. The mean gap is slightly smaller than the earlier +59.1% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/maribo.Omicron_1.0.jar` (`score-review`) in AN-540.

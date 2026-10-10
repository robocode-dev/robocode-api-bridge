---
id: AN-544
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Messapia's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-544 — Messapia's Classic score advantage is confirmed again

## Risk investigated

Whether `mc.Messapia_0.1.8.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `7cf3e05643b09c2673c0e28668923315b13bf05d7a8050c5e1a2c459a9e87666`. The official five-pair confirmation `8dc86c57e7a67d8b` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9a418cc3a44d3944159ed054675754d17ca3b245`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9a418cc3a44d3944159ed054675754d17ca3b245`; the population was its `roborumble` robot rows, and Messapia was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 11,180.0 points and Tank Royale averaged 8,272.4 points. Pair deltas were −28.7%, −20.3%, −33.3%, −23.3%, and −23.9%, for a −25.9% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 5 recorded bot 1 at round 5, turn 514, and the other attempts recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `d02c8b3b9aa53fe9` recorded a −26.6% delta. The current five-pair mean confirms a similar Classic score advantage at −25.9%.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Messapia's Classic score advantage persists under current matched artifacts. Its mean gap is close to the earlier −26.6% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/mdouet.BotKicker_2.0.jar` (`score-review`) in AN-545.

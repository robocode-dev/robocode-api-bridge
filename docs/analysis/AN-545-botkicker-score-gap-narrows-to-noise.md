---
id: AN-545
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: BotKicker's score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-545 — BotKicker's score gap narrows to noise

## Risk investigated

Whether `mdouet.BotKicker_2.0.jar`'s historical Classic score advantage persists under current matched artifacts, or whether the measured difference is now within score noise.

## Evidence boundary

The read-only subject jar has SHA-256 `8d82136a5e1ccc0c4c028d38a2bbca7be9c55f428591be367bec5e033e5fbe99`. The official five-pair confirmation `411c47d5037587e9` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9a418cc3a44d3944159ed054675754d17ca3b245`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9a418cc3a44d3944159ed054675754d17ca3b245`; the population was its `roborumble` robot rows, and BotKicker was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,553.4 points and Tank Royale averaged 5,008.0 points. Pair deltas were +20.2%, −25.3%, −27.3%, −10.4%, and +4.7%, for a −7.62% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 2 recorded bot 1 at round 16, turn 170, and the other attempts recorded none. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `40c16d77c8c88342` recorded a −25.7% delta. The current five-pair mean is −7.62%, within the recorded score-noise band, and the registry now reports `MATCHED (score noise)`.

## What was not pursued

The retest shows that the earlier score gap does not recur at a material size in this sample, but it does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

BotKicker's earlier Classic score advantage did not recur at a material size under current matched artifacts. Its current −7.62% mean is classified as `MATCHED (score noise)`; the cause of the earlier difference remains unknown.

## M-006 handoff

Continue with `roborumble/metal.small.MCool_1.21.jar` (`score-review`) in AN-546.

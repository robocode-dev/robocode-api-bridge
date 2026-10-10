---
id: AN-537
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Cow's Classic score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-537 — Cow's Classic score gap narrows to noise

## Risk investigated

Whether `madmath.Cow_0.1.1.jar`'s historical Classic score advantage persists under current matched artifacts, or whether the measured difference is now within score noise.

## Evidence boundary

The read-only subject jar has SHA-256 `939c09b0fc2cd80d024bd286dd37cad3e78e0377b4d78a04ba29466c68d7d51c`. The official five-pair confirmation `9b9af6bfe586fa97` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source is `compat-test/parity-registry.json` at starting commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`; the population is its `roborumble` robot rows, and Cow was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. The pair deltas report observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence, not deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,913.4 points and Tank Royale averaged 6,743.4 points. Pair deltas were +4.1%, −1.5%, −7.0%, −2.1%, and −5.7%, for a −2.44% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 2 recorded bot 1 at round 8, turn 395, and attempt 3 recorded bot 1 at round 22, turn 96; the other attempts recorded none. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `25cbd1ecf5367e95` recorded a −29.8% delta. The current five-pair mean is −2.44%, within the recorded score-noise band, so the registry now reports `MATCHED (score noise)`.

## What was not pursued

The retest shows that the earlier score gap does not recur in this sample, but it does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Cow's earlier Classic score advantage did not recur at a material size under current matched artifacts. Its current −2.44% mean is classified as `MATCHED (score noise)`; the cause of the earlier difference remains unknown.

## M-006 handoff

Continue with `roborumble/mahrgell.mahrram_1.3.jar` (`score-review`) in AN-538.

---
id: AN-541
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Phoenix's current score difference is within noise
provenance: inferred
reversal-cost: low
---

# AN-541 — Phoenix's current score difference is within noise

## Risk investigated

Whether `marksteam.Phoenix_1.0.jar`'s historical Tank Royale score advantage persists under current matched artifacts, or whether the current difference is within score noise.

## Evidence boundary

The read-only subject jar has SHA-256 `0e76127d76efd6fd593c51ea42574cb573df18bf94cbf78c4bddad0e08a60ff9`. The official five-pair confirmation `563d89364f21559d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

The versioned campaign source is `compat-test/parity-registry.json` at starting commit `ff81bb172b0cc5a7ea90bce33d8e3ef55fe070ab`; the population is its `roborumble` robot rows, and Phoenix was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. The pair deltas report observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence, not deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,872.4 points and Tank Royale averaged 5,503.2 points. Pair deltas were −6.3%, −8.1%, −7.8%, −4.6%, and −4.6%, for a −6.28% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts, with no events. The registry status is `MATCHED (score noise)`.

## What was tried

The earlier observation `a07937e0978e74f3` recorded an +81.6% delta in Tank Royale's favor. The current five-pair mean is −6.28%, a small difference in Classic's favor and within the recorded score-noise band. The registry now reports `MATCHED (score noise)`.

## What was not pursued

The retest shows that Phoenix's earlier score advantage does not recur in this sample, but it does not explain the direction change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Phoenix's earlier Tank Royale score advantage did not recur at a material size under current matched artifacts. The current −6.28% mean is classified as `MATCHED (score noise)`; the cause of the earlier difference and direction change remains unknown.

## M-006 handoff

Continue with `roborumble/matt.UnderDark3_2.4.34.jar` (`score-review`) in AN-542.

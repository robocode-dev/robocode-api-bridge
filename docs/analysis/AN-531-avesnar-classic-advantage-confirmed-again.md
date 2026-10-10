---
id: AN-531
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Avesnar's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-531 — Avesnar's Classic score advantage is confirmed again

## Risk investigated

Whether `lk.nano.Avesnar_1.1.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `5e93a830aad46fd45eac1ecdd0774b6b0bb8246b96a6a199609a0c78a08fc44a`. The official five-pair confirmation `5aa459e8e50f0311` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,701.0 points and Tank Royale averaged 2,741.4 points. Pair deltas were −44.6%, −43.6%, −38.8%, −45.9%, and −34.9%, for a −41.56% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 5 recorded bot 1 at round 15, turn 103, while the first four attempts recorded none. The registry status is `CONFIRMED (score)`.

## Population and sampling boundary

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`. The considered population was its `roborumble` robot rows; this subject was eligible because its starting status was `score-review` and it was selected in registry order. The official five-pair method is the sample; all valid pairs were retained, while failed or incomplete attempts are reported separately for error-stopped subjects. Pair deltas show observed spread, but no confidence interval was computed. Under [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this is statistical sweep quality evidence rather than deterministic acceptance proof.

## What was tried

The earlier observation `43dd6d385a5afa38` recorded a −45.9% delta. The current five-pair mean confirms a similar Classic advantage at −41.56%.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Avesnar's Classic score advantage persists under current matched artifacts. Its mean gap is slightly smaller than the earlier −45.9% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Record `roborumble/logiblocs.Fire_1.0.jar` as `CONFIRMED (score)` in AN-532, then continue with `roborumble/lorneswork.Predator_1.0.jar` (`score-review`) in AN-533.

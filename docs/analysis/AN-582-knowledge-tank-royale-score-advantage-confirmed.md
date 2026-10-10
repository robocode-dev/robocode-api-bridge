---
id: AN-582
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Knowledge's Tank Royale score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-582 — Knowledge's Tank Royale score advantage is confirmed

## Risk investigated

Does Knowledge's earlier Tank Royale score advantage persist under current matched artifacts, and is the mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `ntc.Knowledge_1.1.jar` has SHA-256 `ee4ba1880542b02bc175a63943ee9e7f4840c733ba1dd3ea49a814223d895895`. The current five-pair confirmation `00f405b366442b2f` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5b8ed0eed162a4c67818a30b4e0cd3b930be903b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ntc.Knowledge 1.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5b8ed0eed162a4c67818a30b4e0cd3b930be903b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,679.6 points and Tank Royale averaged 5,854.2 points. Pair deltas were +38.6%, +32.8%, +3.0%, +30.1%, +28.7%, for a +26.64% mean (Tank Royale leads by 26.64%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `9452daca84f0292a` recorded a +46.70% delta. The current five-pair mean is +26.64%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a Tank Royale score advantage slightly above the threshold but does not explain its cause or pair-to-pair variation. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Knowledge's five-pair sample confirms a 26.64% Tank Royale score advantage, just above the recorded 25.0% threshold and down from 46.7%. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/nz.jdc.micro.HedgehogGF_1.5.jar` (`score-review`) in AN-583.

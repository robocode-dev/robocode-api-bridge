---
id: AN-584
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: NeophyteSRAL's Tank Royale score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-584 — NeophyteSRAL's Tank Royale score advantage is confirmed

## Risk investigated

Does NeophyteSRAL's earlier Tank Royale score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `nz.jdc.nano.NeophyteSRAL_1.3.jar` has SHA-256 `09ffd3f3880fd9379ae1414d8a35fa0af88af7f80bde4c829bc70cab2d8e4530`. The current five-pair confirmation `1b34a835bcc8851d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5b8ed0eed162a4c67818a30b4e0cd3b930be903b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists nz.jdc.nano.NeophyteSRAL 1.3 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5b8ed0eed162a4c67818a30b4e0cd3b930be903b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 3,102.4 points and Tank Royale averaged 4,144.2 points. Pair deltas were +14.0%, +45.5%, +40.9%, +29.9%, +39.0%, for a +33.86% mean (Tank Royale leads by 33.86%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `4b8a0820ce25f76a` recorded a +76.30% delta. The current five-pair mean is +33.86%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller Tank Royale score advantage but does not explain the change in magnitude or the pair-to-pair variation. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

NeophyteSRAL's five-pair sample confirms a 33.86% Tank Royale score advantage, down from 76.3% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/oa.weak.BotherBot_0.1.jar` (`score-review`) in AN-585.

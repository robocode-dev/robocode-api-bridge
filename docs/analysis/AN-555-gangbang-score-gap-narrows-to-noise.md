---
id: AN-555
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: GangBang's score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-555 — GangBang's score gap narrows to noise

## Risk investigated

Does GangBang's historical Tank Royale score advantage persist under current matched artifacts, or is the current difference within score noise?

## Evidence boundary

The read-only subject jar `dvogon.GangBang_1.0.jar` has SHA-256 `a40fb98323315743723be8b3153d3fbca76a18889ca3a3a22ec70d18826e730c`. The current retest `40506d20c2be9f51` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists dvogon.GangBang 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All 5 valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,824.8 points and Tank Royale averaged 5,534.2 points. Pair deltas were +30.4%, +31.7%, -0.3%, -1.8%, +14.8%, for a +14.96% mean (Tank Royale leads by 14.96%). The confirmation manifest's threshold is 25.0%; the registry status is nevertheless `MATCHED (score noise)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `8e3f284de332c1dd` recorded a +32.80% delta. The current five-pair mean is +14.96%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest shows the score gap narrowed into the registry noise band but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

GangBang's Tank Royale advantage narrowed from +32.8% to +14.96%, and the registry now classifies it as `MATCHED (score noise)`. The five-pair sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/ebo.Sparse_0.02.jar` (`score-review`) in AN-556.

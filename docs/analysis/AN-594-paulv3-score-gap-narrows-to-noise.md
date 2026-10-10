---
id: AN-594
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: PaulV3's score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-594 — PaulV3's score gap narrows to noise

## Risk investigated

Does PaulV3's earlier Tank Royale score advantage persist under current matched artifacts, or is the current difference within score noise?

## Evidence boundary

The read-only subject jar `paulk.PaulV3_1.7.jar` has SHA-256 `6dfb00aa1ffbeac2c76658041841f0f0757639205a7a20983dc5d5797bfc4f81`. The current five-pair confirmation `2b28d79fd166a8d8` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `abb7e441763a97998bf3d442910e70ce452f9e52`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists paulk.PaulV3 1.7 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `abb7e441763a97998bf3d442910e70ce452f9e52`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,980.4 points and Tank Royale averaged 6,043.8 points. Pair deltas were -2.9%, -14.8%, -26.0%, -3.4%, -17.6%, for a -12.94% mean (Classic leads by 12.94%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `95adbd22ba62453c` recorded a +39.50% delta. The current five-pair mean is -12.94%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest shows that the earlier score difference is now within the registry noise band and has changed direction, but it does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

PaulV3's score direction shifted from a 39.5% Tank Royale advantage to a 12.94% Classic advantage, and the registry now classifies the result as `MATCHED (score noise)`. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/pbg.NinjaX_1.2.jar` (`score-review`) in AN-595.

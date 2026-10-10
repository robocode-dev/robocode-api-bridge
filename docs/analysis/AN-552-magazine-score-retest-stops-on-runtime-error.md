---
id: AN-552
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Magazine's score retest stops on a runtime error
provenance: inferred
reversal-cost: low
---

# AN-552 — Magazine's score retest stops on a runtime error

## Risk investigated

Can Magazine's historical score difference be confirmed under current matched artifacts, or does a runtime error prevent a complete paired sample?

## Evidence boundary

The read-only subject jar `drm.Magazine_0.39.jar` has SHA-256 `099e372c6ef6f3d7d4fe34ea956a0476bccfcd75acd78d83a3603788841a0b3c`. The current retest `bb3e2079100cbf81` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists drm.Magazine 0.39 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. One valid pair was retained; the second attempt ended in an error, leaving the planned five-pair confirmation incomplete. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

One of five planned pairs completed. That pair scored Classic 6,339.0 and Tank Royale 7,416.0 points, a +17.0% Tank Royale delta. Attempt 2 ended with the bridge-only error signature `java.lang.ArrayIndexOutOfBoundsException in drm.common3.Brain.doVirtualBullet`. The registry retains two attempts but only one valid sample and classifies the subject as `DISCREPANCY (errors)`. The manifest threshold is 25.0%; this incomplete sample does not establish a five-pair score result. Skipped-turn telemetry was captured without events on attempt 1; attempt 2 is incomplete because the run stopped after its runtime error.

## What was tried

The earlier observation `e8b005bbf41df0df` recorded a +25.30% delta and status `DISCREPANCY (score)`. The current retest `bb3e2079100cbf81` produced one valid pair before the runtime error; it could not complete the planned five-pair confirmation.

## What was not pursued

The runtime failure was recorded but not diagnosed or repaired in this analysis. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Magazine's score retest remains unresolved: one pair produced a +17.0% Tank Royale delta, then the second attempt recorded `java.lang.ArrayIndexOutOfBoundsException in drm.common3.Brain.doVirtualBullet` and stopped the five-pair confirmation. The cause remains undiagnosed.

## M-006 handoff

Continue with `roborumble/ds.Versatile_RB1.0.1.jar` (`score-review`) in AN-553.

---
id: AN-553
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Versatile's Classic score advantage is confirmed after the wrapper repair
provenance: inferred
reversal-cost: low
---

# AN-553 — Versatile's Classic score advantage is confirmed after the wrapper repair

## Risk investigated

Does Versatile's historical score difference persist under current matched artifacts after the recorded wrapper repair?

## Evidence boundary

The read-only subject jar `ds.Versatile_RB1.0.1.jar` has SHA-256 `756b8734cc934fc2c3107a9ad6d7211f40b9a16a851c5242bd1b93ee4d957cd4`. The current retest `64f84effd3693f9c` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ds.Versatile RB1.0.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All 5 valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,319.8 points and Tank Royale averaged 2,359.6 points. Pair deltas were -56.0%, -55.0%, -55.7%, -55.3%, -56.1%, for a -55.62% mean (Classic leads by 55.62%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `6a0a47aacb2e5d1b` recorded a +147.90% delta. The current five-pair mean is -55.62%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The five-pair confirmation completed after the registry recorded the wrapper JSON encoding repair 48871c9. The score reversal is not attributed to that repair because the old and new observations used different artifact versions. No source comparison or trace was made in this analysis, and no code or rumble-jar change was made.

## Finding

Versatile's current five-pair sample confirms a 55.62% Classic score advantage, reversing the earlier +147.9% Tank Royale advantage. The run completed without runtime errors after the recorded wrapper repair 48871c9; the score reversal is not attributed to that repair.

## M-006 handoff

Continue with `roborumble/dsekercioglu.mega.WhiteFang_2.8.1.jar` (`score-review`) in AN-554.

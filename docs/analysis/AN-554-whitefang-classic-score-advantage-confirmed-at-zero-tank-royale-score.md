---
id: AN-554
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: WhiteFang's Classic score advantage is confirmed at zero Tank Royale score
provenance: inferred
reversal-cost: low
---

# AN-554 — WhiteFang's Classic score advantage is confirmed at zero Tank Royale score

## Risk investigated

Does WhiteFang's historical Classic score advantage persist under current matched artifacts, and does Tank Royale produce a nonzero score?

## Evidence boundary

The read-only subject jar `dsekercioglu.mega.WhiteFang_2.8.1.jar` has SHA-256 `e39500e824d3b350ca5e7be4722697a576764fc6076ba399347fcc827438e7f1`. The current retest `333b0839033e2c5e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists dsekercioglu.mega.WhiteFang 2.8.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All 5 valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

The new official retest `05248b83a1896903` completed on 2026-10-25 with Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `96bdfd7957f0506cd5a84043ec93d3fd7fd1cd4a`. The read-only robot jar SHA-256 is `e39500e824d3b350ca5e7be4722697a576764fc6076ba399347fcc827438e7f1`; the locally built bridge API, wrapper, Bot API, and runner hashes are respectively `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It used the official two-participant, 35-round, 800×600 setup. The observation does not retain the exact Java executable or Classic selected-name field.

All five pairs in the new retest produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,754.4 points and Tank Royale averaged 0.0 points. Pair deltas were -100.0% in all five pairs. The registry status remains `CONFIRMED (score)`. This retest did not enable skipped-turn or scan-event telemetry.

## What was tried

The earlier observations `4d83ffe42a94379f` and `333b0839033e2c5e` recorded the same -100.00% delta. The latest five-pair mean, observation `05248b83a1896903`, is also -100.00%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The new official five-pair retest confirms the score difference but does not explain why Tank Royale scores zero in every pair. Read-only inspection of the jar's `WhiteFang.run` and event callbacks found that it enters its gun-and-movement path only after shield mode is disabled or kill mode is enabled; before that, scan and hit callbacks are routed to the shield. This source reading does not show which path runs in Tank Royale or why the Classic and Tank Royale self-battles score differently. No controlled state trace, bridge code change, or rumble-jar change was made.

## Finding

WhiteFang's latest five-pair sample confirms a 100% Classic score advantage: Classic averaged 4,754.4 points, while Tank Royale scored zero in all five pairs. Both engines reported no runtime errors. Source inspection narrowed the robot's strategy gates, but the reason for the zero Tank Royale score remains undetermined.

## M-006 handoff

Continue with `roborumble/dvogon.GangBang_1.0.jar` (`score-review`) in AN-555.

---
id: AN-559
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: EL33tGangstarr2's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-559 — EL33tGangstarr2's Classic score advantage is confirmed again

## Risk investigated

Does EL33tGangstarr2's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `el33t.EL33tGangstarr2_2.0.jar` has SHA-256 `26ab583db98602ea0d29fc8c0f242166417ca16e218e21c91ad3ee1de45293f4`. The current five-pair confirmation `065e3e334711f348` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists el33t.EL33tGangstarr2 2.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

The new official retest `6763c8ae5e170bfb` completed on 2026-10-25 with Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `96bdfd7957f0506cd5a84043ec93d3fd7fd1cd4a`. The read-only robot jar SHA-256 is `26ab583db98602ea0d29fc8c0f242166417ca16e218e21c91ad3ee1de45293f4`; the locally built bridge API, wrapper, Bot API, and runner hashes are respectively `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It used the official two-participant, 35-round, 800×600 setup. The observation does not retain the exact Java executable or Classic selected-name field.

All five pairs in the new retest produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,312.0 points and Tank Royale averaged 2,777.4 points. Pair deltas were -38.9%, -23.6%, -43.7%, -36.4%, and -33.8%, for a -35.28% mean. The registry status remains `CONFIRMED (score)`. This retest did not enable skipped-turn or scan-event telemetry.

## What was tried

The earlier observation `791e673951bd2014` recorded -39.60%, and `065e3e334711f348` recorded -36.34%. The latest five-pair observation `6763c8ae5e170bfb` records -35.28%; the registry still reports `CONFIRMED (score)`.

## What was not pursued

The read-only jar includes its 46-line source. It continuously turns the radar, then in `onScannedRobot` changes body and gun turns, fire power, and movement based on the scanned bearing and distance. The inspected source shows no direct bridge-specific dependency that explains the score gap. The current repeat confirms the difference but does not identify its cause. No controlled trace, bridge code change, or rumble-jar change was made.

## Finding

The latest five-pair sample confirms a 35.28% Classic score advantage, compared with 39.6% in the earlier observation. The sample had no runtime errors or bridge-only error signatures; the source review did not isolate a bridge cause.

## M-006 handoff

Continue with `roborumble/element.Earth_1.1.jar` (`score-review`) in AN-560.

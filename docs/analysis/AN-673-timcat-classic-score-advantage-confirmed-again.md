---
id: AN-673
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: TimCat's Classic score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-673 — TimCat's Classic score advantage confirmed again

## Risk investigated

Does TimCat's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `timmit.nano.TimCat_0.13.jar` has SHA-256 `fa990b08493927d3250cf35b7e07e6814ad68668d6e3fd5bfbf9c7e11acfd530`. The current five-pair confirmation `3f89adff122501de` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `d2493c2d18fda09f76ca5a41865b6a2a8982fedb`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists timmit.nano.TimCat 0.13 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `d2493c2d18fda09f76ca5a41865b6a2a8982fedb`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,417.2 points and Tank Royale averaged 2,934.8 points. Pair deltas were -45.7%, -42.1%, -39.9%, -48.9%, -51.6%, for a -45.64% mean (Classic leads by 45.64%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. All five pair deltas show a Classic lead, ranging from -51.6% to -39.9%.

## What was tried

The earlier observation `79745ba32e2f25fe` recorded a -38.50% delta. The current five-pair mean is -45.64%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only timmit.nano.TimCat_0.13.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

TimCat's five-pair sample confirms a 45.64% Classic score advantage: Classic averaged 5,417.2 points and Tank Royale averaged 2,934.8. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/tkt.RedShift_1.1.CS.0.jar` (`score-review`) in AN-674.

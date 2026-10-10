---
id: AN-667
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: WeavingWiggle's Classic score gap falls below threshold despite confirmed status
provenance: inferred
reversal-cost: low
---

# AN-667 — WeavingWiggle's Classic score gap falls below threshold despite confirmed status

## Risk investigated

Does WeavingWiggle's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `takeBot.WeavingWiggle_1.1.jar` has SHA-256 `8d758d7f664ba0f44c7b5630063a39f666796962d2a4a37c4389a876efc23dd0`. The current five-pair confirmation `25aa7a2e1fac3a85` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `00369794d689d9723d53193c1f38b8d7be8458db`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists takeBot.WeavingWiggle 1.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `00369794d689d9723d53193c1f38b8d7be8458db`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,410.6 points and Tank Royale averaged 5,333.8 points. Pair deltas were -36.1%, -10.7%, -20.7%, -0.8%, -12.4%, for a -16.14% mean (Classic leads by 16.14%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The mean Classic lead is 16.14%, below the recorded 25.0% threshold, while the registry reports `CONFIRMED (score)`. All five pair deltas favor Classic, but only one exceeds the threshold in magnitude. The retest does not establish why the recorded status differs from the mean.

## What was tried

The earlier observation `abb81ddb48d816a8` recorded a -28.30% delta. The current five-pair mean is -16.14%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only takeBot.WeavingWiggle_1.1.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The measured mean is below threshold even though the registry reports `CONFIRMED (score)`; this note does not infer the classifier's reason. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The five-pair mean records a 16.14% Classic score lead, below the 25.0% threshold, while the registry status is `CONFIRMED (score)`. Both the measurement and status are retained; the reason for the difference remains undetermined.

## M-006 handoff

Continue with `roborumble/taqho.taqbot_1.0.jar` (`score-review`) in AN-668.

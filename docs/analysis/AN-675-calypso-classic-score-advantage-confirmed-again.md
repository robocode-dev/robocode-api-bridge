---
id: AN-675
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Calypso's Classic score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-675 — Calypso's Classic score advantage confirmed again

## Risk investigated

Does Calypso's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `tobe.calypso.Calypso_4.1.jar` has SHA-256 `67f73306327284cc1f1e3a7a939309b218c8333a15c3f47d5a9c06365f298d8e`. The current five-pair confirmation `6a0f7795c653b21d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `d2493c2d18fda09f76ca5a41865b6a2a8982fedb`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists tobe.calypso.Calypso 4.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `d2493c2d18fda09f76ca5a41865b6a2a8982fedb`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,211.4 points and Tank Royale averaged 2,661.6 points. Pair deltas were -33.6%, -32.4%, -37.3%, -41.5%, -39.0%, for a -36.76% mean (Classic leads by 36.76%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. All five pair deltas show a Classic lead, ranging from -41.5% to -32.4%.

## What was tried

The earlier observation `70a448fb51b746c6` recorded a -37.00% delta. The current five-pair mean is -36.76%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only tobe.calypso.Calypso_4.1.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Calypso's five-pair sample confirms a 36.76% Classic score advantage: Classic averaged 4,211.4 points and Tank Royale averaged 2,661.6. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/uccc.Dorito_1.12.jar` (`score-review`) in AN-676.

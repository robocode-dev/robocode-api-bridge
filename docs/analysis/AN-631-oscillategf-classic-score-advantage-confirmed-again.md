---
id: AN-631
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: OscillateGF's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-631 — OscillateGF's Classic score advantage is confirmed again

## Risk investigated

Does OscillateGF's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `slugzilla.OscillateGF_1.0.jar` has SHA-256 `14bbed01c2b1269c625bf9f3a3dd9e7770ed3892de93a37abf663a044f6339e8`. The current five-pair confirmation `02ac4c98a5e817a5` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `dca1553a9d01ee1be201819f58a06addad69368c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists slugzilla.OscillateGF 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `dca1553a9d01ee1be201819f58a06addad69368c`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 8,241.8 points and Tank Royale averaged 4,073.8 points. Pair deltas were -55.6%, -37.8%, -47.6%, -51.2%, -57.9%, for a -50.02% mean (Classic leads by 50.02%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `686937fbcc8bc3e6` recorded a -56.40% delta. The current five-pair mean is -50.02%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

OscillateGF's five-pair sample confirms a 50.02% Classic score advantage: Classic averaged 8,241.8 points and Tank Royale averaged 4,073.8. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

The OscillateLinear retest is recorded in AN-632; continue with `roborumble/slugzilla.OscillatePattern_1.0.jar` (`score-review`) in AN-633.

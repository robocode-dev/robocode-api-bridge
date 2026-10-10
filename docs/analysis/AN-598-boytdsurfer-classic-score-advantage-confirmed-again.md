---
id: AN-598
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: BoyTDSurfer's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-598 — BoyTDSurfer's Classic score advantage is confirmed again

## Risk investigated

Does BoyTDSurfer's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `pkbots.BoyTDSurfer_1.0.jar` has SHA-256 `c0125dc2bc24622bd67fe06132864a40324344322d2e2b0e337ba0e696508226`. The current five-pair confirmation `f36ea0f1ff97b1cb` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `e0136cb8f4a676f513c2be647eefc1693836616e`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists pkbots.BoyTDSurfer 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `e0136cb8f4a676f513c2be647eefc1693836616e`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,158.2 points and Tank Royale averaged 3,040.0 points. Pair deltas were -53.2%, -51.0%, -48.9%, -50.8%, -49.2%, for a -50.62% mean (Classic leads by 50.62%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `239b4d3d4a600a82` recorded a -51.30% delta. The current five-pair mean is -50.62%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a similar Classic score advantage but does not explain its cause or small change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

BoyTDSurfer's five-pair sample confirms a 50.62% Classic score advantage, compared with 51.3% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/pl.mskiba.Hilton_0.4.jar` (`score-review`) in AN-599.

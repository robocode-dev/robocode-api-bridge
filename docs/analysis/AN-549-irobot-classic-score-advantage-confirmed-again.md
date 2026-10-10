---
id: AN-549
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: iRobot's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-549 — iRobot's Classic score advantage is confirmed again

## Risk investigated

Does iRobot's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `mladjo.iRobot_0.3.jar` has SHA-256 `97438c6b46ff1d122975ec726f10291e23db8d9e21956241dcd490d4cdabb1e0`. The official five-pair confirmation `88b7e3e05b72f51e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `596a4914d9684b64a70123941a165b8883c03c3f`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry does not retain the exact selected Classic robot string or the Java executable used.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `596a4914d9684b64a70123941a165b8883c03c3f`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,783.8 points and Tank Royale averaged 3,776.0 points. Pair deltas were -33.9%, -45.0%, -39.3%, -35.4%, -20.6%, for a -34.84% mean (Classic leads by 34.84%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `38dccf41ca9f4c06` recorded a -33.40% delta. The current five-pair mean is -34.84%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The five-pair sample confirms a 34.84% Classic score advantage, compared with 33.4% in the earlier observation. The sample had no runtime errors or bridge-only error signatures.

## M-006 handoff

Continue with `roborumble/mld.Infinity_2.2.jar` (`score-review`) in AN-550.

---
id: AN-597
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: ChironexFleckeri's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-597 — ChironexFleckeri's Classic score advantage is confirmed again

## Risk investigated

Does ChironexFleckeri's earlier Classic score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `pez.mini.ChironexFleckeri_0.5.jar` has SHA-256 `81cef3b2baf4d2a34a255590c4dd7a10f405258b82167985f91db6b7b62afa35`. The current five-pair confirmation `2088c66adc8f05e8` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `e0136cb8f4a676f513c2be647eefc1693836616e`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists pez.mini.ChironexFleckeri 0.5 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `e0136cb8f4a676f513c2be647eefc1693836616e`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,749.0 points and Tank Royale averaged 2,543.2 points. Pair deltas were -57.0%, -49.1%, -59.0%, -58.8%, -54.8%, for a -55.74% mean (Classic leads by 55.74%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `d54f346f46c249a3` recorded a -54.30% delta. The current five-pair mean is -55.74%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a similar Classic score advantage but does not explain its cause or small change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

ChironexFleckeri's five-pair sample confirms a 55.74% Classic score advantage, compared with 54.3% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/pkbots.BoyTDSurfer_1.0.jar` (`score-review`) in AN-598.

---
id: AN-583
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: HedgehogGF's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-583 — HedgehogGF's Classic score advantage is confirmed again

## Risk investigated

Does HedgehogGF's earlier Classic score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `nz.jdc.micro.HedgehogGF_1.5.jar` has SHA-256 `ec09b0e0c878d286ab72bf03e5821ee55e35056762387c79f2c0f870e9edf22b`. The current five-pair confirmation `abf9e457ec4272eb` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5b8ed0eed162a4c67818a30b4e0cd3b930be903b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists nz.jdc.micro.HedgehogGF 1.5 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5b8ed0eed162a4c67818a30b4e0cd3b930be903b`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,523.8 points and Tank Royale averaged 2,250.4 points. Pair deltas were -62.9%, -57.1%, -63.3%, -63.0%, -49.7%, for a -59.20% mean (Classic leads by 59.2%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `9c026df336f09e6d` recorded a -64.00% delta. The current five-pair mean is -59.20%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a slightly smaller Classic score advantage but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

HedgehogGF's five-pair sample confirms a 59.2% Classic score advantage, compared with 64.0% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/nz.jdc.nano.NeophyteSRAL_1.3.jar` (`score-review`) in AN-584.

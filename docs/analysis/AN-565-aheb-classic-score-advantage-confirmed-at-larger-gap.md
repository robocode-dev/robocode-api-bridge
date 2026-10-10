---
id: AN-565
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: AHEB's Classic score advantage is confirmed at a larger gap
provenance: inferred
reversal-cost: low
---

# AN-565 — AHEB's Classic score advantage is confirmed at a larger gap

## Risk investigated

Does AHEB's earlier Classic score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `mnt.AHEB_0.6a.jar` has SHA-256 `67649dbd7f93cddb1af7ff35fe2a48b51c6319b5d613415867b8b6037d1c0bac`. The current five-pair confirmation `488a04379aea3ddd` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `895138bd00f8af51a2fb79769271c8efc9c3e597`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists mnt.AHEB 0.6a for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `895138bd00f8af51a2fb79769271c8efc9c3e597`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,212.4 points and Tank Royale averaged 2,153.4 points. Pair deltas were -64.2%, -66.0%, -60.6%, -69.3%, -66.4%, for a -65.30% mean (Classic leads by 65.3%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `f17bdd7a3c104b3c` recorded a -60.40% delta. The current five-pair mean is -65.30%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a larger Classic score advantage but does not explain the change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

AHEB's five-pair sample confirms a 65.3% Classic score advantage, larger than the earlier 60.4% result. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/mrm.MightyMoose_.2.jar` (`score-review`) in AN-566.

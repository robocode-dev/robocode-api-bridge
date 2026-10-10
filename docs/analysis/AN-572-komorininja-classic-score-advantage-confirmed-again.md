---
id: AN-572
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: KomoriNinja's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-572 — KomoriNinja's Classic score advantage is confirmed again

## Risk investigated

Does KomoriNinja's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `myl.nano.KomoriNinja_1.1.jar` has SHA-256 `d44ca5734824a7f7f7396016c47ed7d474751e775b1be11f1a6c22053db82f1b`. The current five-pair confirmation `40eb9defa2457be6` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `e644efe51b66d681e8e56725b0aac3f80efed55e`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists myl.nano.KomoriNinja 1.1 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `e644efe51b66d681e8e56725b0aac3f80efed55e`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,378.6 points and Tank Royale averaged 4,561.4 points. Pair deltas were -22.6%, -31.3%, -32.6%, -30.6%, -24.4%, for a -28.30% mean (Classic leads by 28.3%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `6a75615f456acc97` recorded a -70.80% delta. The current five-pair mean is -28.30%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the Classic score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

KomoriNinja's five-pair sample confirms a 28.3% Classic score advantage, down from 70.8% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/nammyung.ModelT_0.23.jar` (`score-review`) in AN-573.

---
id: AN-684
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Chameleon's Tank Royale score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-684 — Chameleon's Tank Royale score advantage confirmed again

## Risk investigated

Does Chameleon's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `wilson.Chameleon_0.91.jar` has SHA-256 `23cb1ef8ed9689d0e4182c55c6d603a8d85f0af2aeae4e9d370ae5adcc9493bb`. The current five-pair confirmation `9226e5e501c357ec` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists wilson.Chameleon 0.91 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,054.2 points and Tank Royale averaged 7,654.6 points. Pair deltas were +63.7%, +44.7%, +38.2%, +49.9%, +60.7%, for a +51.44% mean (Tank Royale leads by 51.44%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. All five pair deltas show a Tank Royale lead, ranging from +38.2% to +63.7%.

## What was tried

The earlier observation `91b838943f37afae` recorded a +53.40% delta. The current five-pair mean is +51.44%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only wilson.Chameleon_0.91.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Chameleon's five-pair sample confirms a 51.44% Tank Royale score advantage: Classic averaged 5,054.2 points and Tank Royale averaged 7,654.6. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/winamp32.micro.MicroMacro_1.0.jar` (`score-review`) in AN-685.

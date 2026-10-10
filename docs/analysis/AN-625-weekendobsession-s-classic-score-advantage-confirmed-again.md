---
id: AN-625
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: WeekendObsession_S keeps a confirmed Classic score advantage
provenance: inferred
reversal-cost: low
---

# AN-625 — WeekendObsession_S keeps a confirmed Classic score advantage

## Risk investigated

Does the Classic score advantage for WeekendObsession_S persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `simonton.nano.WeekendObsession_S_1.7.jar` has SHA-256 `9ed256050a9b9505dbc3401209e93330ec5b21b2abffc6715dd612884089a128`. The current five-pair confirmation `2e84f65fb5b5dcd2` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `fdf97a473838cb208adcd58928dc5840ef32367a`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists simonton.nano.WeekendObsession_S 1.7 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `fdf97a473838cb208adcd58928dc5840ef32367a`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,698.6 points and Tank Royale averaged 2,048.2 points. Pair deltas were -63.7%, -65.0%, -66.1%, -62.2%, -63.0%, for a -64.00% mean (Classic leads by 64.00%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `e102e3ed5887efb7` recorded a -61.70% delta. The current five-pair mean is -64.00%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The five-pair sample for WeekendObsession_S confirms a 64.00% Classic score advantage: Classic averaged 5,698.6 points and Tank Royale averaged 2,048.2. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/skm.PateranBotlock2_1.0.jar` (`score-review`) in AN-626.

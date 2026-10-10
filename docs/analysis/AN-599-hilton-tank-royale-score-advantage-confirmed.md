---
id: AN-599
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Hilton's Tank Royale score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-599 — Hilton's Tank Royale score advantage is confirmed

## Risk investigated

Does Hilton's earlier Tank Royale score advantage persist under current matched artifacts, and is the current mean still above the recorded threshold?

## Evidence boundary

The read-only subject jar `pl.mskiba.Hilton_0.4.jar` has SHA-256 `2db756db76c429e90f559007358ee7e6dcf0bde6964688af13dbe6179b93c6dd`. The current five-pair confirmation `f6d9f91e640b74ee` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `e0136cb8f4a676f513c2be647eefc1693836616e`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists pl.mskiba.Hilton 0.4 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `e0136cb8f4a676f513c2be647eefc1693836616e`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 2,390.0 points and Tank Royale averaged 3,020.4 points. Pair deltas were +39.9%, +15.8%, +11.0%, +14.4%, +64.5%, for a +29.12% mean (Tank Royale leads by 29.12%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `661c265540cacf47` recorded a +35.20% delta. The current five-pair mean is +29.12%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a Tank Royale score advantage slightly above the threshold but does not explain the pair-to-pair variation. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Hilton's five-pair sample confirms a 29.12% Tank Royale score advantage, just above the 25.0% threshold and down from 35.2%. The deltas range from +11.0% to +64.5%, so the mean includes substantial variation.

## M-006 handoff

Continue with `roborumble/pl.robocode.Pacyfista_1.0.jar` (`score-review`) in AN-600.

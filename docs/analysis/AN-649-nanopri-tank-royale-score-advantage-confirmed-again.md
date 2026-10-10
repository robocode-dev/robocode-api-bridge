---
id: AN-649
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: nanoPri's Tank Royale score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-649 — nanoPri's Tank Royale score advantage confirmed again

## Risk investigated

Does nanoPri's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `step.nanoPri_1.0.jar` has SHA-256 `b0e70f458532d1924cdf10ee5ab670bb3a705386dc8a5cac295a3b72c7606e75`. The current five-pair confirmation `73d25f3e01d15318` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `79d4a669bf8355ffd91b1c9cce9bbdf693121cf3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists step.nanoPri 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `79d4a669bf8355ffd91b1c9cce9bbdf693121cf3`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 10,849.0 points and Tank Royale averaged 14,614.6 points. Pair deltas were +38.8%, +26.1%, +39.9%, +39.3%, +29.8%, for a +34.78% mean (Tank Royale leads by 34.78%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `9485f074e69490f3` recorded a +38.90% delta. The current five-pair mean is +34.78%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only step.nanoPri_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

nanoPri's five-pair sample confirms a 34.78% Tank Royale score advantage: Classic averaged 10,849.0 points and Tank Royale averaged 14,614.6. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/suh.mega.WaveSurferGF_1.04.jar` (`score-review`) in AN-650.

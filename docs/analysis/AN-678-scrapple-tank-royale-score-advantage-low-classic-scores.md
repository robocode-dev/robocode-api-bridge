---
id: AN-678
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Scrapple's Tank Royale score advantage confirmed with low Classic scores
provenance: inferred
reversal-cost: low
---

# AN-678 — Scrapple's Tank Royale score advantage confirmed with low Classic scores

## Risk investigated

Does Scrapple's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `uccc.Scrapple_1.0.jar` has SHA-256 `2c28b7e470423715df67571a5a4bccec0c4f9b2f0ae04a33e9d9fbea61697f87`. The current five-pair confirmation `584d104d6c03ac46` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5c6fe6fa7f4131dbb376887c90964e8f3f3d869d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists uccc.Scrapple 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5c6fe6fa7f4131dbb376887c90964e8f3f3d869d`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 645.8 points and Tank Royale averaged 2,243.4 points. Pair deltas were +112.2%, +590.8%, +234.5%, +241.7%, +262.8%, for a +288.40% mean (Tank Royale leads by 288.40%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The relative Tank Royale lead is large alongside a low Classic mean of 645.8 points, compared with 2,243.4 Tank Royale points. Pair deltas range from +112.2% to +590.8%; the percentage magnitude should be read with the raw means.

## What was tried

The earlier observation `fe4ed74105911c2d` recorded a +105.30% delta. The current five-pair mean is +288.40%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only uccc.Scrapple_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Scrapple's five-pair sample confirms a 288.40% Tank Royale score advantage: Classic averaged 645.8 points and Tank Royale averaged 2,243.4. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/voidious.mini.Komarious_1.88.jar` (`score-review`) in AN-679.

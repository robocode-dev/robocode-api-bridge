---
id: AN-682
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: SuperSittingDuck's Tank Royale score advantage confirmed at low scores with one reversed pair
provenance: inferred
reversal-cost: low
---

# AN-682 — SuperSittingDuck's Tank Royale score advantage confirmed at low scores with one reversed pair

## Risk investigated

Does SuperSittingDuck's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `wiki.SuperSampleBot.SuperSittingDuck_1.0.jar` has SHA-256 `f664cea9c8c8ca7ad62031da9fb9777b15692cec5c2cbf447efff2460a33f281`. The current five-pair confirmation `60c1e57e6a950dcd` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists wiki.SuperSampleBot.SuperSittingDuck 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `5f03cf276adccb5ff7b1e5d5eef5f4a97b48f2c8`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 228.2 points and Tank Royale averaged 528.6 points. Pair deltas were +151.2%, +296.7%, +266.7%, +233.3%, -28.6%, for a +183.86% mean (Tank Royale leads by 183.86%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded. The Classic mean is only 228.2 points and the Tank Royale mean 528.6. Four pair deltas show a large Tank Royale lead, while the fifth is -28.6% in Classic’s favor. The very low scores produce a large, variable relative percentage; all five pairs were retained.

## What was tried

The earlier observation `4547e0802306f2d7` recorded a +150.00% delta. The current five-pair mean is +183.86%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only wiki.SuperSampleBot.SuperSittingDuck_1.0.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

SuperSittingDuck's five-pair sample confirms a 183.86% Tank Royale score advantage: Classic averaged 228.2 points and Tank Royale averaged 528.6. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/wiki.Wolverine_2.1.jar` (`score-review`) in AN-683.

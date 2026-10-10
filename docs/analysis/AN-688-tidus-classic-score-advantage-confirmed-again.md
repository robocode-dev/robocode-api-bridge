---
id: AN-688
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Tidus's Classic score advantage confirmed again
provenance: inferred
reversal-cost: low
---

# AN-688 — Tidus's Classic score advantage confirmed again

## Risk investigated

Does Tidus's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `yagami.Tidus_0.11.jar` has SHA-256 `d8bafdd7f741a8631b7d15ab3e3ca089b05feb23a17129ef0bbe2d83a1ff90fb`. The current five-pair confirmation `b688c77e8e4d7f4e` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `b02af471b96c88110f1b8d6bd4ebb95b8ef1fa29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists yagami.Tidus 0.11 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `b02af471b96c88110f1b8d6bd4ebb95b8ef1fa29`; the population was its `roborumble robot rows`, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 3,689.8 points and Tank Royale averaged 2,553.8 points. Pair deltas were -26.2%, -35.0%, -30.4%, -33.4%, -29.2%, for a -30.84% mean (Classic leads by 30.84%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; no skipped-turn events were recorded.

## What was tried

The earlier observation `0885ba495b5a215b` recorded a -32.30% delta. The current five-pair mean is -30.84%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only yagami.Tidus_0.11.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Tidus's five-pair sample confirms a 30.84% Classic score advantage: Classic averaged 3,689.8 points and Tank Royale averaged 2,553.8. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/zyx.nano.RedBull_1.0.jar` (`score-review`) in AN-689.

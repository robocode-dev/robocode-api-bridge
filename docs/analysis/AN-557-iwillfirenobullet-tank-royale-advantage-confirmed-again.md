---
id: AN-557
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: IWillFireNoBullet's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-557 — IWillFireNoBullet's Tank Royale score advantage is confirmed again

## Risk investigated

Does IWillFireNoBullet's earlier Tank Royale score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `eem.IWillFireNoBullet_v2.4.jar` has SHA-256 `2f4ba80edb9f95aabd5e69487de2d2cdf84243d80e0da18a6cac2b06c1da1609`. The current five-pair confirmation `3523bc0befc6246a` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists eem.IWillFireNoBullet v2.4 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `4d232d88c0f1ed9aaba2d1b11c3761cacfcea235`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 634.6 points and Tank Royale averaged 2,172.8 points. Pair deltas were +288.4%, +273.2%, +442.1%, +124.3%, +202.5%, for a +266.10% mean (Tank Royale leads by 266.1%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured in all five Tank Royale attempts. Attempt 1 recorded bot 2 at round 9, turn 1,250; attempt 2 recorded bots 1 and 2 at round 3, turns 1–14; attempts 3–5 recorded no skipped-turn events.

## What was tried

The earlier observation `7cdf1837d55623fb` recorded a +44.80% delta. The current five-pair mean is +266.10%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain why the score advantage increased. Skipped-turn events were observed in two attempts, but no controlled trace or source comparison was made. No code or rumble-jar change was made.

## Finding

IWillFireNoBullet's Tank Royale score advantage increased from +44.8% to +266.10% in the five-pair sample. Skipped-turn events appeared in attempts 1 and 2, while the engines reported no runtime errors.

## M-006 handoff

Continue with `roborumble/ej.ChocolateBar_1.1.jar` (`score-review`) in AN-558.

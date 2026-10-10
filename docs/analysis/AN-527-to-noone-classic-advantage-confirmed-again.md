---
id: AN-527
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: ToNoone's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-527 — ToNoone's Classic score advantage is confirmed again

## Risk investigated

Whether `kneels.ToNoone_0.2.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `364b38e3f238f906b9d9377085e6f0ac75abf2a16c6f34f3118ddccf1176817f`. The official five-pair confirmation `c5e140a867a73077` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,582.2 points and Tank Royale averaged 3,256.0 points. Pair deltas were −46.8%, −37.5%, −29.1%, −52.0%, and −41.6%, for a −41.4% mean. Skipped-turn telemetry was captured in all five attempts, with no events. The registry status is `CONFIRMED (score)`.

## Population and sampling boundary

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`. The considered population was its `roborumble` robot rows; this subject was eligible because its starting status was `score-review` and it was selected in registry order. The official five-pair method is the sample; all valid pairs were retained, while failed or incomplete attempts are reported separately for error-stopped subjects. Pair deltas show observed spread, but no confidence interval was computed. Under [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this is statistical sweep quality evidence rather than deterministic acceptance proof.

## What was tried

The earlier observation `ae62db4724a7b736` recorded a −48.0% delta. The current five-pair mean confirms the same direction, with a smaller −41.4% gap.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

ToNoone's Classic score advantage persists under current matched artifacts. The measured gap is smaller than the earlier observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/kneels.nano.Derp_0.2.jar` (`score-review`) in AN-528.

---
id: AN-530
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Oz's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-530 — Oz's Classic score advantage is confirmed again

## Risk investigated

Whether `lessonz.robocode.Oz_0.5.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `4bd0078e14471f94a515e4d44b806a4e5cca3d65f75dc004d85ed802f25806fa`. The official five-pair confirmation `523f6f50e6466de4` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 6,021.8 points and Tank Royale averaged 3,408.4 points. Pair deltas were −32.7%, −41.8%, −38.3%, −60.2%, and −44.3%, for a −43.46% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 5 recorded bot 1 at round 6, turns 643 and 644, while the first four attempts recorded none. The registry status is `CONFIRMED (score)`.

## Population and sampling boundary

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`. The considered population was its `roborumble` robot rows; this subject was eligible because its starting status was `score-review` and it was selected in registry order. The official five-pair method is the sample; all valid pairs were retained, while failed or incomplete attempts are reported separately for error-stopped subjects. Pair deltas show observed spread, but no confidence interval was computed. Under [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this is statistical sweep quality evidence rather than deterministic acceptance proof.

## What was tried

The earlier observation `3f66cee1b6bf3c7a` recorded a −38.9% delta. The current five-pair mean confirms the same Classic advantage at −43.46%.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Oz's Classic score advantage persists under current matched artifacts. The measured mean gap is larger than the earlier −38.9% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/lk.nano.Avesnar_1.1.jar` (`score-review`) in AN-531.

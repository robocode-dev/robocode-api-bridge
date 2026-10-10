---
id: AN-529
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Lechu's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-529 — Lechu's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `lechu.Lechu_1.1.jar`'s historical score difference persists under current matched artifacts, and whether its direction and magnitude have changed.

## Evidence boundary

The read-only subject jar has SHA-256 `8533375040b9f858e480b423fff413460f20519ea11b41aeb0c1fae2367e3d0d`. The official five-pair confirmation `85931b36a6d41da8` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 998.4 points and Tank Royale averaged 2,668.6 points. Pair deltas were +89.4%, +177.8%, +153.9%, +218.0%, and +236.9%, for a +175.2% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts, with no events. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `f47bc92c4d2ff2ae` recorded a +261.8% delta, also showing higher Tank Royale scores. The current five-pair confirmation preserves the direction but measures a smaller relative gap.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Lechu's Tank Royale score advantage persists under current matched artifacts. Its mean pair delta is +175.2%, down from the earlier +261.8%, and the registry classifies it as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/lessonz.robocode.Oz_0.5.0.jar` (`score-review`) in AN-530.

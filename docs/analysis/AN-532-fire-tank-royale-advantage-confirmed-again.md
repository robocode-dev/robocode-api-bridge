---
id: AN-532
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Fire's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-532 — Fire's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `logiblocs.Fire_1.0.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `e7999754905f742a89d3b91a1574c3427f590262768b51c8c40088a7c387a3e6`. The official five-pair confirmation `7e8b0c1cf09cf105` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `64bf710b3b8c5dadbbe8742a213d5f3fdc5f4ef3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 1,718.6 points and Tank Royale averaged 3,356.2 points. Pair deltas were +212.8%, +69.1%, +59.6%, +85.1%, and +103.9%, for a +106.1% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 3 recorded bot 1 at round 4, turn 510, and the other attempts recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `4a263e819ee273fa` recorded a +138.2% delta. The current five-pair confirmation preserves the Tank Royale advantage at +106.1%, with a smaller mean gap.

## What was not pursued

The retest confirms the score difference but does not explain it. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Fire's Tank Royale score advantage persists under current matched artifacts. The measured mean gap is smaller than the earlier +138.2% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause remains unknown.

## M-006 handoff

Continue with `roborumble/lorneswork.Predator_1.0.jar` (`score-review`) in AN-533.

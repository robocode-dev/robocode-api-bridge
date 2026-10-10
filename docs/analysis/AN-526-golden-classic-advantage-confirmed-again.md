---
id: AN-526
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Golden's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-526 — Golden's Classic score advantage is confirmed again

## Risk investigated

Whether `kms.Golden_0.10.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `787293b2d0294b483b6862e2646c2259d28dd2ab00be208f8cdcaa13b8c0c19a`. The official five-pair confirmation `07727aa519a8593b` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `163d7b1e53fbe701f86cc6b55d77535e4cf8f75b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors. Classic averaged 5,489.2 points and Tank Royale averaged 3,695.6 points. Pair deltas were −39.3%, −35.2%, −38.0%, −21.6%, and −29.4%, for a −32.7% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempts 3 and 4 recorded the events listed in the registry, and attempts 1, 2, and 5 recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `b5d6900c92396f8b` recorded a −33.1% delta. The current five-pair mean confirms a similar Classic score advantage at −32.7%.

## What was not pursued

The retest confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Golden's Classic score advantage persists under current matched artifacts and is close to the earlier measured gap. The registry classifies it as `CONFIRMED (score)`; the cause remains unknown.

## M-006 handoff

Continue with `roborumble/kneels.ToNoone_0.2.jar` (`score-review`) in AN-527.

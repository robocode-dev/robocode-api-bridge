---
id: AN-237
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-087, AN-194]
title: AdvancedTrackerII's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-237 — AdvancedTrackerII's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether AdvancedTrackerII's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/com.cgarias.rc.AdvancedTrackerII_1.0.jar` has SHA-256 `a3da2e6251e317bbc767b8ac58ba3d37581a70a1597e0d2d10de8d961ade2ad3`. The previous current-pair observation `4c84d08e1bc4c3dc` completed on 2026-10-06; the new official observation `9f844c4af2979bcd` completed on 2026-10-07 with bridge commit `6b5b936488405997f6594e04c4ee02d87d87b1e7` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with five events, all in round 1 at turn 1.

## What was tried

The previous current-pair observation scored 116,709 in Classic with 874 errors and 113,603 in Tank Royale with no errors, a −2.7% delta. The new observation scored 116,806 in Classic with 818 errors and 112,321 in Tank Royale with 31 errors, a −3.8% delta. Both deltas are inside the 25% review threshold. Classic recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale recorded the ChumbaMini stream-limit exception and an unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. This AdvancedTrackerII observation is consistent with that finding and identifies no AdvancedTrackerII-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/com.cohesiva.robocode.ManOwaR_1.0.jar` (`DISCREPANCY (errors)`).

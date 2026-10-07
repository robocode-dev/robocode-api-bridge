---
id: AN-244
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-094, AN-194]
title: Talon's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-244 — Talon's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether Talon's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/cs.sheldor.Talon_1.1.jar` has SHA-256 `5309462a28f7ba9d36cf60aaf5b74efa130a9a4ba62fa426d281efb4f8b2bc92`. The previous current-pair observation `cf8014dc1274c8e8` completed on 2026-10-06; the new official observation `a2a927f67a45ea34` completed on 2026-10-07 with bridge commit `1a2cb799e18377347bf8ab3a70de2098c430e9b3` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with eight events, all in round 1 at turn 1.

## What was tried

The previous current-pair observation scored 113,496 in Classic with 1,048 errors and 110,362 in Tank Royale with no errors, a −2.8% delta. The new observation scored 113,986 in Classic with 410 errors and 109,284 in Tank Royale with 29 errors, a −4.1% delta. Both deltas are inside the 25% review threshold. Both engines recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale's remaining signature is unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The Classic error count changed, but its named signatures are unchanged. The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. This observation identifies no Talon-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/css.Delitioner_0.11.jar` (`DISCREPANCY (errors)`).

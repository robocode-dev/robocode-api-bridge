---
id: AN-246
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-096, AN-194]
title: Firsty's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-246 — Firsty's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether Firsty's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/cvt.Firsty_1.0.jar` has SHA-256 `b40717173156cbdc6189f9cb60c70f664df2e9c0d1354a59a1acd36c2d4134d4`. The previous current-pair observation `c00c28dde6c6d301` completed on 2026-10-06; the new official observation `3f891266ba3d9c06` completed on 2026-10-07 with bridge commit `1a2cb799e18377347bf8ab3a70de2098c430e9b3` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with two events, both in round 1 at turn 1.

## What was tried

The previous current-pair observation scored 115,271 in Classic with 158 errors and 111,878 in Tank Royale with no errors, a −2.9% delta. The new observation scored 115,028 in Classic with 574 errors and 113,488 in Tank Royale with 34 errors, a −1.3% delta. Both deltas are inside the 25% review threshold. Both engines recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale's remaining signature is unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The Classic error count increased, but its named signatures are unchanged. The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. This observation identifies no Firsty-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cx.BlestPain_1.41.jar` (`DISCREPANCY (errors)`).

---
id: AN-245
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-095, AN-194]
title: Delitioner's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-245 — Delitioner's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether Delitioner's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/css.Delitioner_0.11.jar` has SHA-256 `6c718a72f59cec4464649d650469adfcccaf0de0744d7fd3e3ef7a2c5ce28c0b`. The previous current-pair observation `309b49ab69e7bfe5` completed on 2026-10-06; the new official observation `4ffc2a51192c3e63` completed on 2026-10-07 with bridge commit `1a2cb799e18377347bf8ab3a70de2098c430e9b3` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with eight events, all in round 1 at turn 1.

## What was tried

The previous current-pair observation scored 115,459 in Classic with 378 errors and 112,892 in Tank Royale with no errors, a −2.2% delta. The new observation scored 115,313 in Classic with 798 errors and 113,417 in Tank Royale with 30 errors, a −1.6% delta. Both deltas are inside the 25% review threshold. Classic recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale recorded the ChumbaMini stream-limit exception and an unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. The Classic error count changed, but its named signatures are unchanged. This observation identifies no Delitioner-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cvt.Firsty_1.0.jar` (`DISCREPANCY (errors)`).

---
id: AN-243
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-093, AN-194]
title: Wren's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-243 — Wren's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether Wren's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/cs.Wren_1.0.jar` has SHA-256 `89f30ea0d295bbc4516e9dad72426663fdf68707b2c733555e408c35679770ea`. The previous current-pair observation `d6682247ccce0d91` completed on 2026-10-06; the new official observation `f33ed69c769eca7b` completed on 2026-10-07 with bridge commit `1a2cb799e18377347bf8ab3a70de2098c430e9b3` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with no events.

## What was tried

The previous current-pair observation scored 113,381 in Classic with 102 errors and 112,764 in Tank Royale with no errors, a −0.5% delta. The new observation scored 115,326 in Classic with 590 errors and 111,662 in Tank Royale with 30 errors, a −3.2% delta. Both deltas are inside the 25% review threshold. Both engines recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale's remaining signature is unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The Classic error count increased, but its named signatures are unchanged. The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. This observation identifies no Wren-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cs.sheldor.Talon_1.1.jar` (`DISCREPANCY (errors)`).

---
id: AN-241
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-091, AN-194]
title: Electron's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-241 — Electron's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether Electron's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/conscience.Electron_1.3g.jar` has SHA-256 `a8d12ab9530cd0f748593ac3d04cd2cf0b563713905457257c3395b7ab7aaf9b`. The previous current-pair observation `244529b8cc8da0fe` completed on 2026-10-06; the new official observation `9fe148d04d9e314a` completed on 2026-10-07 with bridge commit `4bf166335e27f7f0d79b841ecfb32186ba5b5de1` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with four events, all in round 1 at turn 1.

## What was tried

The previous current-pair observation scored 115,062 in Classic with 610 errors and 112,794 in Tank Royale with no errors, a −2.0% delta. The new observation scored 115,176 in Classic with 1,012 errors and 113,680 in Tank Royale with 30 errors, a −1.3% delta. Both deltas are inside the 25% review threshold. Both engines recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale's remaining signature is unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. The Classic error count increased, but its named signatures are unchanged. This observation identifies no Electron-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cs.Grudge_1.0.jar` (`DISCREPANCY (errors)`).

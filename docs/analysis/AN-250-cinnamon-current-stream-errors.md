---
id: AN-250
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-100, AN-194]
title: Cinnamon's current run has no ShizzleStiX signature and records ChumbaMini errors in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-250 — Cinnamon's current run has no ShizzleStiX signature and records ChumbaMini errors in Tank Royale

## Risk investigated

Whether Cinnamon's previous Classic ShizzleStiX exception recurs in the current official M-006 pair, and whether the pinned ChumbaMini stream-limit error appears in Tank Royale.

## Evidence boundary

The read-only subject jar `meleerumble/dans.Cinnamon_1.2.jar` has SHA-256 `cb352fa2412994d4160c5161124036464a48cc73b1725178a7ee048609c8b7da`. The previous current-pair observation `453a1f4ed8473f61` completed on 2026-10-06; the new official observation `68a19df6119cb9ac` completed on 2026-10-07 with bridge commit `86cd96970cca5e437b78f5aa34965942e33add7d` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with seven events, all in round 1 at turn 1.

## What was tried

The previous current-pair observation scored 115,102 in Classic with 299 errors and 110,596 in Tank Royale with no errors, a −3.9% delta. The new observation scored 114,438 in Classic with 1,216 errors and 109,433 in Tank Royale with 30 errors, a −4.4% delta. Both deltas are inside the 25% review threshold. Classic recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; these signatures remain associated with the pinned opponent pool in AN-015. The prior Classic `ConcurrentModificationException` at `amk.ShizzleStiX.Navigator.run` does not appear in the new observation's error signatures. Tank Royale recorded the ChumbaMini stream-limit exception and an unknown-origin `SecurityException`.

The previous current-pair run had no Tank Royale errors, while the new run records the ChumbaMini signature. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. This run identifies no Cinnamon-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. The earlier ShizzleStiX signature does not recur in the latest observation; Tank Royale now reports the pinned ChumbaMini stream-limit error. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/darkcanuck.B26354_1.06.jar` (`DISCREPANCY (errors)`).

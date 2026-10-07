---
id: AN-248
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-098, AN-194]
title: Princess repeats its matched missing-file error and now records ChumbaMini stream-limit failures
provenance: inferred
reversal-cost: low
---

# AN-248 — Princess repeats its matched missing-file error and now records ChumbaMini stream-limit failures

## Risk investigated

Whether Princess's current official melee run still has a bridge-only missing-file error, and whether the pinned ChumbaMini stream-limit failure appears in the current Tank Royale run.

## Evidence boundary

The read-only subject jar `meleerumble/cx.Princess_1.0.jar` has SHA-256 `8a5137dcb2d3075bf8b4aa5ec609d499a981f1ed6650641a10e3c95cfe2e7cec`. The previous current-pair observation `b9e6b88d0f56d7ee` completed on 2026-10-06; the new official observation `fa7070f07b20d104` completed on 2026-10-07 with bridge commit `1a2cb799e18377347bf8ab3a70de2098c430e9b3` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with 34 events.

## What was tried

The previous current-pair observation scored 114,012 in Classic with 265 errors and 112,160 in Tank Royale with one error, a −1.6% delta. The new observation scored 114,383 in Classic with 609 errors and 111,232 in Tank Royale with 31 errors, a −2.8% delta. Both deltas are inside the 25% review threshold. Both logs record `FileNotFoundException` for `Princess.data/score.dat`; the Classic and Tank Royale paths identify Princess's own data directory, consistent with the matching first-run behavior documented in AN-098. Both engines also record the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale's 30 stream-limit errors come from the pinned ChumbaMini fixture identified in AN-015.

The prior run had only the matching missing-file error in Tank Royale; the new run adds the ChumbaMini stream-limit signature. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. The missing-file message remains matched across engines, so this run does not identify a bridge-only failure.

## Finding

Retain `DISCREPANCY (errors)` for the pinned fixture errors. Princess's missing-file message remains present on both engines, and the current Tank Royale run also records ChumbaMini's stream-limit failure. The score delta is inside the review threshold. No new bridge defect or code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cx.mini.Nimrod_0.55.jar` (`DISCREPANCY (errors)`).

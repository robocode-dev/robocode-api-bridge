---
id: AN-240
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-090, AN-194]
title: Dreadnaught's current run has no timeout and now records ChumbaMini errors in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-240 — Dreadnaught's current run has no timeout and now records ChumbaMini errors in Tank Royale

## Risk investigated

Whether Dreadnaught's historical timeout returns in the current official M-006 pair, and whether its current errors indicate a new bridge defect.

## Evidence boundary

The read-only subject jar `meleerumble/com.syncleus.robocode.Dreadnaught_0.1.jar` has SHA-256 `4a2a9926375fca4cf32ea72bdc8ab43930223f2090bae428474eafb9a6dd823c`. The prior current-pair observation `e8f05311abdf7569` completed on 2026-10-06; the new official observation `7504f3f0550a9a51` completed on 2026-10-07 with bridge commit `4bf166335e27f7f0d79b841ecfb32186ba5b5de1` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with no events.

## What was tried

The prior current-pair observation scored 116,796 in Classic with 776 errors and 115,492 in Tank Royale with no errors, a −1.1% delta. The new observation scored 117,417 in Classic with 384 errors and 114,497 in Tank Royale with 32 errors, a −2.5% delta. Both deltas are inside the 25% review threshold. Classic recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale recorded the ChumbaMini stream-limit exception and an unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The previous timeout does not recur: the current pair completed all 35 rounds. The immediately preceding current-pair run had no Tank Royale errors, while this run records the ChumbaMini signature. AN-194 documents the bridge's corrected cross-round stream-limit behavior. This result is consistent with the pinned fixture errors and does not identify a new Dreadnaught or bridge defect.

## Finding

Retain `DISCREPANCY (errors)` for the current fixture-pool errors. The prior timeout remains absent, and the score delta is inside the review threshold. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/conscience.Electron_1.3g.jar` (`DISCREPANCY (errors)`).

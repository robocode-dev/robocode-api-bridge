---
id: AN-233
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-083, AN-194]
title: Dancer's Tank Royale run again records the ChumbaMini stream-limit failure
provenance: inferred
reversal-cost: low
---

# AN-233 — Dancer's Tank Royale run again records the ChumbaMini stream-limit failure

## Risk investigated

Whether Dancer's current official melee run reproduces the pinned ChumbaMini stream-limit error in Tank Royale, and whether its score delta indicates a confirmed parity gap.

## Evidence boundary

The read-only subject jar `meleerumble/cli.Dancer_1.1.jar` has SHA-256 `6126e668dd994c5d3b47f50cad7e28313ad0b67ef1d5def7746846a35de8e07b`. The previous current-pair observation `16e4155095f28971` completed on 2026-10-06; the new official observation `02d8559d6d924209` completed on 2026-10-07 with bridge commit `4d9ae425f6d7fd12f3ffdab26b3433b2b7a90ef1` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was captured with no events in this run.

## What was tried

The previous current-pair observation scored 115,805 in Classic with 596 errors and 114,227 in Tank Royale with no errors, a −1.4% delta. The new observation scored 115,342 in Classic with 516 errors and 113,595 in Tank Royale with 30 errors, a −1.5% delta. Both deltas are within the 25% review threshold. Classic recorded the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale recorded the ChumbaMini stream-limit exception and an unknown-origin `SecurityException`. AN-015 maps the named classes to the pinned opponent pool.

The prior current-pair run had no Tank Royale ChumbaMini error, while the new run does. AN-194 documents that the bridge's round-boundary cleanup had hidden ChumbaMini's accumulated stream-limit failure; its removal restores Classic's five-open-stream behavior. This Dancer observation is consistent with that finding and identifies no Dancer-specific or new bridge failure.

## Finding

The score delta remains inside the review threshold, but the latest run has errors on both engines, so retain `DISCREPANCY (errors)`. Tank Royale now also reports the pinned ChumbaMini stream-limit error in this subject's run. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cli.WasteOfAmmo_1.0.jar` (`DISCREPANCY (errors)`).

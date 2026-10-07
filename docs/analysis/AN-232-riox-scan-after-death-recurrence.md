---
id: AN-232
type: analysis
status: active
links: [P-001, CAP-004, CAP-007, AN-015, AN-082, AN-194]
title: RiOx's scan-after-death index error returns without identifying a bridge defect
provenance: inferred
reversal-cost: low
---

# AN-232 — RiOx's scan-after-death index error returns without identifying a bridge defect

## Risk investigated

Whether RiOx's historical Tank Royale `Index 9` failure recurs in the current official M-006 pair, and whether the result identifies a bridge defect.

## Evidence boundary

The read-only subject jar `meleerumble/cf.RiO.RiOx_4.2.1.jar` has SHA-256 `da01319579e24798cf6d7a05cfd13f5c8ba9fe4c704006c8fb4b8ff6fe5f9b9e`. The official observation `e3248f1f1b0b06a3` completed on 2026-10-07 with bridge commit `4d9ae425f6d7fd12f3ffdab26b3433b2b7a90ef1` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`; the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. Skipped-turn telemetry was incomplete.

## What was tried

The preceding current-pair observation `5c54d07d0c37d704` scored 113,848 in Classic and 111,623 in Tank Royale with no Tank Royale errors, so the `Index 9` failure did not recur in that run. The new observation scored 113,975 in Classic with 898 errors, then Tank Royale stopped without a score after three errors. Tank Royale recorded `ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 9` at `cf.OPs.RiOxM_OP.onScannedRobot`, an unknown-origin array-bounds error, and the known `SecurityException` at `amk.ChumbaMini.saveData`.

AN-015's earlier trace captured a `RobotDeathEvent(victim=3)` followed by `ScannedRobotEvent(target=3)` before the same RiOx failure. This observation confirms recurrence of the subject-side signature but does not capture that event sequence or establish that the bridge violates classic behavior. The ChumbaMini stream-limit exception is part of the previously documented opponent-pool issue and is expected after AN-194's round-boundary cleanup change.

## Finding

The historical RiOx error has returned in the latest official pair, and Tank Royale produced no score. Retain `DISCREPANCY (outcome)`. The run provides no new evidence that the bridge caused the exception; the controlled comparison of the exact death/scan sequence identified in AN-015 remains open. No code change is indicated by this observation alone.

## M-006 handoff

Continue in registry order with `meleerumble/cli.Dancer_1.1.jar` (`DISCREPANCY (errors)`).

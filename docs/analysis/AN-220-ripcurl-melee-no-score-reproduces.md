---
id: AN-220
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-015, AN-070]
title: RipCurl's melee no-score outcome reproduces with the same negative-index error
provenance: inferred
reversal-cost: low
---

# AN-220 — RipCurl's melee no-score outcome reproduces with the same negative-index error

## Risk investigated

Whether the current `meleerumble/bts.wiki.RipCurl_0.9b.jar` Tank Royale no-score outcome changes after the bridge stream-limit fix, and whether a new failure points to the bridge.

## Evidence boundary

The read-only subject jar has SHA-256 `1f9a52c3ea49c22e5755defaaf43f355801169537f7a9636d1708bd00064e4c9`. The prior observation `0bf6db46a5da3a18` completed on 2026-10-06 with bridge commit `1db331504c394b7100d891b3165a34e3c82bf458`; the new official M-006 observation `ff081352701afac8` completed on 2026-10-07 with bridge commit `5c2f10d635365d22777228d2bb25a750d1fa199a` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 112,766 in Classic with 534 errors and produced no Tank Royale score with two errors. The new observation scored 112,897 in Classic with 272 errors and again produced no Tank Royale score with two errors, so the delta remains unavailable and the registry status remains `DISCREPANCY (outcome)`. Both Tank Royale observations contain `ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 2` at `us.bluetorch.robocode.movement.MinimumRisk.onScannedRobot`; the new harness result also records that the worker produced no score. Classic recorded the fixed-pool ChumbaMini and Aristocles errors. Requested skipped-turn telemetry was `incomplete` with `events: null`.

## Finding

The missing-score outcome and negative-index exception recur. AN-070 places the failing method in the read-only subject jar and identifies its unchecked indexing; this run does not show a new bridge failure or explain the input that becomes negative. The score discrepancy cannot be quantified without a Tank Royale score. Keep `DISCREPANCY (outcome)` and make no code or rumble-jar change.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.fnr.Fenrir_0.36l.jar` (`DISCREPANCY (errors)`).

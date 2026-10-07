---
id: AN-187
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Mooserwirt2's Classic-only stream error remains in the melee opponent
provenance: inferred
reversal-cost: low
---

# AN-187 — Mooserwirt2's Classic-only stream error remains in the melee opponent

## Risk investigated

Whether the historical robot-file stream-limit discrepancy for `agd.Mooserwirt2_2.7.jar` recurs in the M-006 melee setup under current matched artifacts.

## Evidence boundary

The read-only subject jar `agd.Mooserwirt2_2.7.jar` has SHA-256 `377e6a4c674d0436ee7d3cf8e29c2581fc37e55238e6fefef86394c55a35b556`. The official one-pair observation `9274dda799073ff0` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `828b3140f5937cea2a331e6609484efce88164ca`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected melee opponents and their hashes; the subject and opponent jars remained read-only.

## What was tried

Classic scored 115,244 and logged 56 errors; Tank Royale scored 115,085 and logged none, for a −0.1% score delta. Classic's error signatures were `SecurityException` from `amk.ChumbaMini.saveData` and unknown origin. The current Classic log shows `amk.ChumbaMini.saveData` failing at the five-stream limit, and `amk.ChumbaMini_0.2.jar` is in the selected opponent pool. The registry status remains `DISCREPANCY (errors)`.

The earlier latest observation `778cd898ff919dd9` recorded 610 Classic errors and no Tank Royale errors after the registered `robot-file-stream-limit` repair `e663103`. The current Tank Royale run also reported zero errors. The `--capture-skipped-turns` option was requested, but the observation stores skipped-turn telemetry as null rather than a captured event list.

This finding is limited to one selected subject in one ten-participant melee match. The observed Classic error count fell from 610 to 56, which shows that the opponent failure count varies between runs; one pair does not estimate its rate or broader score behavior.

## Finding

The current main bridge build completed the Tank Royale match without errors, consistent with the earlier stream-limit repair holding for this run. Classic still reported the selected melee opponent `amk.ChumbaMini` exceeding the classic five-stream limit. C-004's error comparison remains unchanged and the row stays `DISCREPANCY (errors)`; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/agrach.Dalek_1.0.jar` (`DISCREPANCY (errors)`).

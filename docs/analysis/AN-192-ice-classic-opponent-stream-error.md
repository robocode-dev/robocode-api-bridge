---
id: AN-192
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ice's Classic melee run still logs the opponent stream-limit error
provenance: inferred
reversal-cost: low
---

# AN-192 — Ice's Classic melee run still logs the opponent stream-limit error

## Risk investigated

Whether the historical robot-file stream-limit discrepancy for `ahr.ice.Ice_1.0.2.jar` recurs in the M-006 melee setup under current matched artifacts.

## Evidence boundary

The read-only subject jar `ahr.ice.Ice_1.0.2.jar` has SHA-256 `46da6577681b39d9a472ff9559a8e29fa9fa0dde914b2854b22dde5c4d89a507`. The official one-pair observation `dd6a0f86e90c38ef` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `5102861a55ab005ed48b5021b889114263e4fcb7`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected melee opponents and their hashes; the subject and opponent jars remained read-only.

## What was tried

Classic scored 113,932 and logged 606 errors; Tank Royale scored 112,602 and logged none, for a −1.2% score delta. Classic error signatures included `SecurityException` from `amk.ChumbaMini.saveData`, `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare`, and unknown origins. The current Classic log shows the ChumbaMini save-data call failing at the five-stream limit, and `amk.ChumbaMini_0.2.jar` is in the selected opponent pool. The registry status remains `DISCREPANCY (errors)`.

The earlier latest observation `55fdd09abb52d8f4` recorded 376 Classic errors and no Tank Royale errors after the registered `robot-file-stream-limit` repair `e663103`. Tank Royale again reported zero errors in the current run. The Classic text log contains a ChumbaMini skipped-turn line, but the requested `--capture-skipped-turns` telemetry fields are null rather than a captured event list.

This finding is limited to one selected subject in one ten-participant melee match. Classic logged 606 errors in the current run versus 376 previously; this spread does not estimate a stable error count or behavior across the corpus.

## Finding

The current main bridge build completed the Tank Royale match without errors, consistent with the earlier stream-limit repair holding for this run. Classic still logged the selected melee opponent `amk.ChumbaMini` exceeding the classic five-stream limit. C-004's error comparison remains unchanged and the row stays `DISCREPANCY (errors)`; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ChumbaMini_0.2.jar` (`DISCREPANCY (errors)`), following AN-193's Fermat retest.

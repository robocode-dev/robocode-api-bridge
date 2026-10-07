---
id: AN-189
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Glacier's Classic melee run still logs the opponent stream-limit error
provenance: inferred
reversal-cost: low
---

# AN-189 — Glacier's Classic melee run still logs the opponent stream-limit error

## Risk investigated

Whether the historical robot-file stream-limit discrepancy for `ags.Glacier_0.2.11.jar` recurs in the M-006 melee setup under current matched artifacts.

## Evidence boundary

The read-only subject jar `ags.Glacier_0.2.11.jar` has SHA-256 `37122320b1f4f8572edd27b8fa5549af5b55b89738e855945cb69ff0713cb8b0`. The official one-pair observation `50580090ac2c2136` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `e96cf8b3a93178266377e06d6eaa524424f24f42`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected melee opponents and their hashes; the subject and opponent jars remained read-only.

## What was tried

Classic scored 114,310 and logged 680 errors; Tank Royale scored 112,135 and logged none, for a −1.9% score delta. Classic error signatures included `SecurityException` from `amk.ChumbaMini.saveData`, `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare`, and unknown origins. The current Classic log shows the ChumbaMini save-data call failing at the five-stream limit, and `amk.ChumbaMini_0.2.jar` is in the selected opponent pool. The registry status remains `DISCREPANCY (errors)`.

The earlier latest observation `9e12c9052df5dcdf` recorded 364 Classic errors and no Tank Royale errors after the registered `robot-file-stream-limit` repair `e663103`. Tank Royale again reported zero errors in the current run. The Classic text log contains a ChumbaMini skipped-turn line, but the requested `--capture-skipped-turns` telemetry fields are null rather than a captured event list.

This finding is limited to one selected subject in one ten-participant melee match. Classic logged 680 errors in the current run versus 364 previously; that spread does not estimate a stable error count or behavior across the corpus.

## Finding

The current main bridge build completed the Tank Royale match without errors, consistent with the earlier stream-limit repair holding for this run. Classic still logged the selected melee opponent `amk.ChumbaMini` exceeding the classic five-stream limit. C-004's error comparison remains unchanged and the row stays `DISCREPANCY (errors)`; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ags.surreptitious.MiniSurreptitious_0.0.1.jar` (`DISCREPANCY (errors)`).

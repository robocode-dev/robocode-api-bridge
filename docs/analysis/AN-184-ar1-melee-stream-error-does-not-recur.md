---
id: AN-184
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Ar1's historical bridge stream error does not recur in the melee check
provenance: inferred
reversal-cost: low
---

# AN-184 — Ar1's historical bridge stream error does not recur in the melee check

## Risk investigated

Whether the historical robot-file stream-limit error recorded for `adt.Ar1_2.1.jar` recurs in the M-006 melee setup under current matched artifacts.

## Evidence boundary

The read-only subject jar `adt.Ar1_2.1.jar` has SHA-256 `37857b3fe3d082f121da8dd8ca81a5ea58edcab65fad3c552d71dfa65f58b14e`. The official one-pair observation `c8e2e2f4ab93c568` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `9de7f87a8f3e494f99e4720d6eb2b8d731e74e3b`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected melee opponents and their hashes; the subject and opponent jars remained read-only.

## What was tried

Classic scored 114,884 and logged 74 errors; Tank Royale scored 113,395 and logged none, for a −1.3% score delta. Classic error signatures included `SecurityException` from `amk.ChumbaMini.saveData`, `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare`, and unknown origins. The current match's selected opponent pool includes `amk.ChumbaMini_0.2.jar`; the error log shows its save-data call failing at the classic five-stream limit. The registry status remains `DISCREPANCY (errors)`.

The earlier observation `a07510045f88384d` recorded 402 Classic errors and no Tank Royale errors after the registered `robot-file-stream-limit` repair `e663103`. The current Tank Royale run again reported zero errors, so the historical bridge-side stream-limit error did not recur in this one-pair check. The `--capture-skipped-turns` option was requested, but the observation stores skipped-turn telemetry as null rather than a captured event list.

This M-006 row uses a ten-participant melee match. The earlier analysis AN-012 measured this same jar in a two-participant RoboRumble setup and found no material score gap; that result does not establish melee error parity. This one match does not estimate score variation or the behavior of the entire opponent pool.

## Finding

The current main bridge build completed the Tank Royale melee match without errors, consistent with the earlier stream-limit repair holding for this run. Classic still reported 74 errors, including the selected `amk.ChumbaMini` opponent's data-file failure and other partly unknown origins. C-004's error comparison remains unchanged and the row stays `DISCREPANCY (errors)`; no bridge code or rumble jar change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/adt.Ar2_1.0.jar` (`DISCREPANCY (errors)`).

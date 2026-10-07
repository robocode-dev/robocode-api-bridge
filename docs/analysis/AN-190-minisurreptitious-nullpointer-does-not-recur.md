---
id: AN-190
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: MiniSurreptitious's historical Tank Royale NullPointerException does not recur
provenance: inferred
reversal-cost: low
---

# AN-190 — MiniSurreptitious's historical Tank Royale NullPointerException does not recur

## Risk investigated

Whether the historical Tank Royale `NullPointerException` in `ags.surreptitious.MiniSurreptitious_0.0.1.jar` recurs after the registered `initial-status-before-run` repair in the M-006 melee setup.

## Evidence boundary

The read-only subject jar `ags.surreptitious.MiniSurreptitious_0.0.1.jar` has SHA-256 `0c1cdf2e268c5e96d3a24063df946d694c879d499bf2df55a987539cdfbc18ad`. The official one-pair observation `1fcf4ed36080fdc1` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `e796636ca4bc3a43fa3f92f52ecde17d88012a52`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected melee opponents and their hashes; the subject and opponent jars remained read-only.

## What was tried

Classic scored 113,983 and logged 82 errors; Tank Royale scored 111,042 and logged none, for a −2.6% score delta. Classic error signatures included `SecurityException` from `amk.ChumbaMini.saveData`, `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare`, and unknown origins. The current Classic log shows the ChumbaMini save-data call failing at the five-stream limit, and `amk.ChumbaMini_0.2.jar` is in the selected opponent pool. The registry status remains `DISCREPANCY (errors)`.

The previous latest observation `dc8e698cbe4541f8` completed after the registered `initial-status-before-run` repair `8e50c0b`; it recorded 302 Classic errors and no Tank Royale errors. Earlier observations recorded a Tank Royale `NullPointerException` with origin `ags.surreptitious.MiniSurreptitious.move`. Tank Royale reported zero errors in both post-repair observations, including this current run. The Classic text log contains a ChumbaMini skipped-turn line, but the requested `--capture-skipped-turns` telemetry fields are null rather than a captured event list.

This finding is limited to one selected subject in one ten-participant melee match. The current match confirms that the prior Tank Royale failure did not recur in this run; it does not establish its absence under every participant pool or match condition.

## Finding

The historical Tank Royale `NullPointerException` did not recur after the registered bridge repair in the latest earlier run or the current run. Classic still reported melee-opponent errors, including the selected `amk.ChumbaMini` robot exceeding the classic five-stream limit, so the registry row remains `DISCREPANCY (errors)`. No bridge code or rumble jar change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/ahf.NanoAndrew_.4.jar` (`DISCREPANCY (errors)`).

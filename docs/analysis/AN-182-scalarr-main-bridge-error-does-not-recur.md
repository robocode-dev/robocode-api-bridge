---
id: AN-182
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ScalarR's historical bridge stream error does not recur on main
provenance: inferred
reversal-cost: low
---

# AN-182 — ScalarR's historical bridge stream error does not recur on main

## Risk investigated

Whether the historical robot-file stream-limit error associated with `aaa.r.ScalarR_0.005g.047.jar` recurs under current matched artifacts, and whether the row still has an error discrepancy under C-004.

## Evidence boundary

The read-only subject jar `aaa.r.ScalarR_0.005g.047.jar` has SHA-256 `deb332f422c39ac0ff3c0996df2396834bca50533f2a414c09aa3a9fb14c1c79`. The official one-pair observation `aab77f2fc23d346a` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `b1f5e3c4c766d1bbb132d7ac1345f49a72a44c28`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the nine selected opponents and their hashes; the selected pool included `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The subject jar and opponent jars remained read-only.

## What was tried

The official pair completed with a Classic score of 113,387 and a Tank Royale score of 112,462, a −0.8% delta. Classic logged 230 errors; Tank Royale logged none. Classic error signatures included `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare` and unknown origin, plus `SecurityException` from `amk.ChumbaMini.saveData` and unknown origin. The current Classic log shows the `ChumbaMini.saveData` stack ending at the classic five-stream limit. The registry status remains `DISCREPANCY (errors)`.

The earlier latest observation `9390bec53e59bd32` recorded the same Classic-only error imbalance after the registered `robot-file-stream-limit` repair `e663103`. The current Tank Royale run again had zero errors, so the historical bridge-side stream-limit error did not recur in this one-pair check. The `--capture-skipped-turns` option was requested, but the observation stores skipped-turn telemetry as null rather than a captured event list; the bridge-only signature field is also null.

This is one selected melee robot in one configured ten-participant match. The error classifier identifies some opponent frames and leaves other origins unknown; this does not prove the cause of every Classic error. One pair gives no statistical estimate of score variation, and the −0.8% score difference is not the reason the row remains open.

## Finding

The current main bridge build completed the Tank Royale match without errors, consistent with the earlier file-stream repair holding for this run. The row remains an error discrepancy because Classic reported 230 errors, including a stack trace in the selected `amk.ChumbaMini` opponent and other partly unknown origins. C-004's error comparison remains unchanged; no bridge code or rumble jar change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/abud.ThirdRobo_1.0.jar` (`DISCREPANCY (errors)`).

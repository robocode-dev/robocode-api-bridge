---
id: AN-183
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ThirdRobo's non-serializable team message is rejected by both engines
provenance: inferred
reversal-cost: low
---

# AN-183 — ThirdRobo's non-serializable team message is rejected by both engines

## Risk investigated

Whether the historical `team-message-nonserializable-payload` outcome for `abud.ThirdRobo_1.0.jar` persists on current matched artifacts, and whether it indicates a bridge behavior change or the same legacy serialization failure in both engines.

## Evidence boundary

The read-only subject jar `abud.ThirdRobo_1.0.jar` has SHA-256 `124b7067cca1b407845176fd26ad2388aab1ad20085b34bd5561539515272a16`. The official one-pair observation `f26439e4738d1a27` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `7eebd93c35d8d5623cbe4d14eb5f087d411b433c`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 10 participants, 35 rounds, and a 1000×1000 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected opponents and their hashes; all robot jars remained read-only.

## What was tried

Classic scored 115,641 and logged 920 errors; Tank Royale scored 113,088 and logged 10,483 errors, for a −2.2% score delta. Both error logs contain repeated `java.io.NotSerializableException: abud.EnemyInfo`. Classic attributes this path to `abud.ThirdRobo.onScannedRobot` and `TeamRobotProxy.sendMessage`; Tank Royale's classifier records unknown origin, while its log names the rejected `abud.EnemyInfo` payload. Classic also logged errors from melee opponents, including `amk.ChumbaMini.saveData` and `amk.guns.Aristocles.prepare`. The registry status remains `DISCREPANCY (errors)`.

The bridge API accepts `Serializable` team-message payloads. `BridgeTeamMessage.encode()` uses Java object serialization, and `testROUTE009_UnitNegative_PreservesLegacySerializationFailure` preserves the corresponding failure for an invalid payload. This matches the `NotSerializableException` visible in the Classic log; it does not support suppressing or converting the error into a successful send. The latest earlier observation `d274a46e9d0ff8b7` also had a Tank Royale error discrepancy, but its recorded signatures included errors from the shared `amk.ChumbaMini` opponent. The current message-payload errors are separately identifiable by the `abud.EnemyInfo` name.

The official `--capture-skipped-turns` option was requested, but the observation stores skipped-turn telemetry as null on both sides rather than a captured event list. This is one match in a ten-participant melee setup; the error totals and score delta do not estimate run-to-run variation or establish why the engines invoke the failing send path different numbers of times.

## Finding

The current Tank Royale bridge rejects `abud.EnemyInfo` with `NotSerializableException`, matching the legacy serialization failure reported by Classic. The large error-count difference remains visible and the registry correctly stays `DISCREPANCY (errors)` under the current harness; this one-pair observation does not establish whether event frequency or another factor explains the count. No bridge code, criterion, or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/adt.Ar1_2.1.jar` (`DISCREPANCY (errors)`).

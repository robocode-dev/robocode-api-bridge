---
id: AN-194
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004]
title: ChumbaMini's five-stream failure exposed round-boundary cleanup
provenance: inferred
reversal-cost: low
---

# AN-194 — ChumbaMini's five-stream failure exposed round-boundary cleanup

## Risk investigated

Whether the Classic-only five-stream error from `amk.ChumbaMini_0.2.jar` is caused by the bridge releasing open stream slots at each round boundary, despite Classic retaining them for the robot's battle.

## Evidence boundary

The read-only subject jar `amk.ChumbaMini_0.2.jar` has SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The Classic source repository was at commit `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`; it constructs one `RobotFileSystemManager` per `HostingRobotProxy`, retains stream objects in that manager until `close()`, and starts each round through the same proxy. `javap` of the read-only ChumbaMini jar shows `saveData()` creates a `RobocodeFileOutputStream` inside object and gzip streams and never closes it.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. The pre-fix bridge commit was `f2d845e11b05d67f5dd6bcb88fb086adaa034199`; the corrected bridge commit is `2bd47f15395b7d6e177ab60db78cfe713717ec2e`. All three observations used Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. The corrected bridge API jar SHA-256 is `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`; the rumble subject and opponents remained read-only.

## What was tried

Before the fix, official observation `9b5ea5337a0ee08a` scored 113,662 in Classic with 254 errors and 113,397 in Tank Royale with none, a −0.2% delta. The Classic signature included `SecurityException` at `amk.ChumbaMini.saveData`; the bridge's round-start cleanup released the leaked stream slots before they could accumulate across rounds.

The fix removed automatic stream closure at round boundaries, leaving slots reserved until the robot explicitly closes its stream. `./gradlew :robocode-api:jar :robots-wrapper:jar` succeeded; Gradle reported the wrapper jar task as skipped. No unit tests were run. The official M-006 retest `a4eea4df8525940e` scored 114,085 in Classic with 600 errors and 113,879 in Tank Royale with 30 errors, a −0.2% delta. Tank Royale now records `SecurityException` at `amk.ChumbaMini.saveData`; Classic also reports `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare`. Skipped-turn telemetry was captured on both sides, with two Tank Royale events in round 1 at turn 1.

The cross-row retest `be2f27c5669782f3` for Ice scored 113,605 in Classic with 580 errors and 112,875 in Tank Royale with 30 errors, a −0.6% delta. Its previous observation `dd6a0f86e90c38ef` had 606 Classic errors and no Tank Royale errors. The corrected run includes the same ChumbaMini `SecurityException` signature in Tank Royale; its skipped-turn telemetry was captured. Both corrected observations used bridge commit `2bd47f15395b7d6e177ab60db78cfe713717ec2e`.

## Rejected explanations and options

The stream error is not limited to an opponent contaminating ChumbaMini's own row: the robot's bytecode opens the stream in its own `saveData()` method, and Classic's per-robot manager retains it across rounds. The same normalized signature appearing in Tank Royale on both ChumbaMini and Ice after the bridge stopped closing streams supports the round-boundary cleanup as the cause.

Changing FIO-005 or C-004 was rejected because their accepted meaning already covers this correction: a robot may not keep more than five robot file streams open, and Classic-only errors remain unresolved parity cases. No new acceptance criterion is needed.

## Finding

The bridge's automatic round-boundary cleanup reset the five-stream quota even though Classic retains unclosed streams across rounds. Removing that cleanup restores the existing stream-limit behavior: Tank Royale now reports the ChumbaMini `SecurityException` in both the subject run and the Ice opponent run. The two registry rows remain `DISCREPANCY (errors)` because their Classic totals are still much higher and Classic also reports other signatures, including the Aristocles array-bounds error.

A registry scan found 70 latest observations after 2026-09-28 with a Classic `amk.ChumbaMini.saveData` signature; only the new ChumbaMini and Ice observations currently show the same Tank Royale origin. The other records remain evidence pinned to earlier bridge commits and were not retested in this finding.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ChumbaWumba_0.3.jar` (`DISCREPANCY (errors)`).

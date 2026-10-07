---
id: AN-215
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-015, AN-065, AN-194]
title: Squirrel's no-score outcome changes to a score with a remaining error discrepancy
provenance: inferred
reversal-cost: low
---

# AN-215 — Squirrel's no-score outcome changes to a score with a remaining error discrepancy

## Risk investigated

Whether the historical no-score result for `meleerumble/bayen.nut.Squirrel_1.615.jar` persists with the current bridge and whether its repeated robot and opponent errors identify a new bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `16e179f041de685fbe45ebcc94b8f2f57cde891552e364d1f58b7779067dc13c`. The previous observation `beacf992d9a2c81c` completed on 2026-10-06 with bridge commit `a7a6b145902a977555a215884b16cd344c9266d6` and recorded no Tank Royale score; the new official M-006 observation `62b89c98b4cc7cee` completed on 2026-10-07 with bridge commit `b581b8b6b0c887cdfb53131bab91a8155708b2f5` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The previous current-artifact observation scored 114,843 in Classic with 966 errors and had no Tank Royale score with 440 errors, so the registry status was `DISCREPANCY (outcome)`. The new observation scored 115,255 in Classic with 1,123 errors and 113,465 in Tank Royale with 213 errors, a −1.6% delta; the registry status is now `DISCREPANCY (errors)`. Both engines recorded `ArrayIndexOutOfBoundsException` at `bayen.nut.GFTWave1.setSegmentations`; Tank Royale also recorded an unknown-origin array-bounds error, `IOException`, and the ChumbaMini stream-limit `SecurityException`. Classic additionally recorded `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare`. The earlier Tank Royale `NullPointerException` at `bayen.nut.Squirrel.onScannedRobot` did not recur. Requested skipped-turn telemetry was captured with an empty event list.

## Finding

The historical Tank Royale no-score outcome does not recur in this official run. The score delta is inside the 25% review threshold, but the registry retains `DISCREPANCY (errors)` because Tank Royale still reports errors and Classic has additional signatures and a higher error count. The repeated `GFTWave1.setSegmentations` failure on both engines matches AN-065's robot-owned unchecked-index diagnosis; this run provides no evidence of a new bridge defect. No code change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bigpete.Stewie_1.0.jar` (`DISCREPANCY (errors)`).

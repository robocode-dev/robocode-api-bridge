---
id: AN-341
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-065, AN-215]
title: Squirrel's Tank Royale null-scan exception recurs before a score
provenance: inferred
reversal-cost: low
---

# AN-341 — Squirrel's Tank Royale null-scan exception recurs before a score

## Risk investigated

Whether `bayen.nut.Squirrel_1.615.jar`'s historical melee errors persist under the latest matched artifacts, and whether the current failure identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `16e179f041de685fbe45ebcc94b8f2f57cde891552e364d1f58b7779067dc13c`. Observation `acd823109782f334` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d02611ff9f6d1c39c98f193dfefc8e4de9636fef`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The run stopped after one attempt with no score from either engine, zero completed confirmation samples, and incomplete skipped-turn telemetry. Tank Royale reported a bridge-only `java.lang.NullPointerException` at `bayen.nut.Squirrel.onScannedRobot`; the registry also records an unknown-origin null-pointer signature. The status remains `DISCREPANCY (errors)`.

The preceding observation `62b89c98b4cc7cee` completed five pairs, with a −1.6% delta, 1,123 Classic errors, and 213 Tank Royale errors. It did not report the current `onScannedRobot` null-pointer signature. Read-only source bundled in the subject jar assigns `lastScan` only when `getTime() < 30`, then later dereferences `lastScan` in `e.getDistance() < lastScan.getDistance()` without a null check. The run does not record the first scan time, so it does not show why `lastScan` was still null.

## Finding

The current retest reproduces a Tank Royale-only null-scan failure before either engine produces a score. The named method and unchecked field access match the robot-owned diagnosis recorded in AN-065. The evidence does not establish why scan timing leaves `lastScan` unset, and does not demonstrate a bridge defect. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bigpete.Stewie_1.0.jar` (`DISCREPANCY (errors)`).

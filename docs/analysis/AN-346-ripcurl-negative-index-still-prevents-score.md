---
id: AN-346
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-070, AN-220]
title: RipCurl's minimum-risk negative index still prevents a score
provenance: inferred
reversal-cost: low
---

# AN-346 — RipCurl's minimum-risk negative index still prevents a score

## Risk investigated

Whether `bts.wiki.RipCurl_0.9b.jar`'s historical Tank Royale no-score outcome persists under the latest matched artifacts, and whether the repeated error identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `1f9a52c3ea49c22e5755defaaf43f355801169537f7a9636d1708bd00064e4c9`. Observation `fc84d31d9e9918ca` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `546d7c330d13999567a07197916d7d8e199a153b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The run stopped after one attempt with no score from either engine, zero completed confirmation samples, and incomplete skipped-turn telemetry. Tank Royale reported a bridge-only `java.lang.ArrayIndexOutOfBoundsException` at `us.bluetorch.robocode.movement.MinimumRisk.onScannedRobot`; the status remains `DISCREPANCY (errors)`.

The preceding observation `ff081352701afac8` scored 112,897 in Classic with 272 errors and no Tank Royale score with two errors. It reported `ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 2` at the same `MinimumRisk.onScannedRobot` method. The current registry signature preserves the exception type and origin but not the index value. AN-070 identifies this method as code in the read-only subject jar and diagnoses its unchecked negative index.

## Finding

The current retest again produces no Tank Royale score, with the same subject-method array-bounds signature recorded in AN-220. The repeated failure matches the robot-owned diagnosis; the evidence does not identify a bridge defect or explain the negative input. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.fnr.Fenrir_0.36l.jar` (`DISCREPANCY (errors)`).

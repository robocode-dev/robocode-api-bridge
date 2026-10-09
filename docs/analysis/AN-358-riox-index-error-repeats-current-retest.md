---
id: AN-358
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-015, AN-082, AN-232]
title: RiOx's scan index error recurs in the current M-006 retest
provenance: inferred
reversal-cost: low
---

# AN-358 — RiOx's scan index error recurs in the current M-006 retest

## Risk investigated

Whether `cf.RiO.RiOx_4.2.1.jar`'s historical Tank Royale no-score outcome persists under the latest M-006 artifacts, and whether the current signature identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `da01319579e24798cf6d7a05cfd13f5c8ba9fe4c704006c8fb4b8ff6fe5f9b9e`. Observation `268099c2e1c14c3f` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `17ea6230758302718d6dd4b2c996214759d1205d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The run stopped after one attempt with no score from either engine, zero completed confirmation samples, and incomplete skipped-turn telemetry. Tank Royale reported a bridge-only `java.lang.ArrayIndexOutOfBoundsException` at `cf.OPs.RiOxM_OP.onScannedRobot`; the registry status is `DISCREPANCY (errors)`.

The preceding observation `e3248f1f1b0b06a3` also stopped without a Tank Royale score and recorded `ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 9` at the same robot method, along with an unknown-origin array-bounds error and the known ChumbaMini stream-limit error. AN-015's earlier trace recorded a death event for target 3 followed by a scan of that target. The current observation does not preserve the index value or event sequence, so it confirms the method-level recurrence but does not establish why the index is invalid or whether scan timing violates classic behavior.

## Finding

RiOx's historical scan-index failure recurs and prevents the current M-006 retest from producing a score. The signature originates in the subject robot, but the existing evidence does not prove whether a stale post-death scan or another condition supplies the invalid index. This observation does not demonstrate a bridge defect. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cli.Dancer_1.1.jar` (`DISCREPANCY (errors)`).

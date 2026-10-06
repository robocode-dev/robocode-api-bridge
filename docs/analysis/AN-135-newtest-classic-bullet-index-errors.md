---
id: AN-135
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: NewTest's recurring Classic errors use extracted bullet bytes as two-entry array indexes
provenance: inferred
reversal-cost: low
---

# AN-135 — NewTest's recurring Classic errors use extracted bullet bytes as two-entry array indexes

## Risk investigated

Whether com.arsenic.NewTest's historical Classic-only exceptions recur with current matched artifacts and whether their origin points to the bridge.

## Evidence boundary

The read-only subject jar `com.arsenic.NewTest_1.0.jar` has SHA-256 `0a5a9d382489ace685399cf9c5d709daa70f7e545ac85d54a1b0e81bf7d3a412`. The current official observation `645d204963358c18` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `9cfabcd821c989161f96090f795c6fb185c12007`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 6,882 and reported 12 `ArrayIndexOutOfBoundsException` errors in `com.arsenic.NewTest.onBulletHit` and `onBulletMissed`; observed indexes include 103, −51, −52, and −46 against arrays of length 2. Tank Royale scored 6,548 with no errors and captured an empty skipped-turn event list. The score delta was −4.9%; the registry status is `DISCREPANCY (errors)`.

The earlier observation `d6d74c72cf5e95ab` also reported 12 Classic array-index errors at the same methods and no Tank Royale errors. The earlier observation `bec9ffc1e65a95d1` reported 10 Classic errors. Read-only disassembly of the subject jar shows `onBulletHit` and `onBulletMissed` use `DataBullet.extract(bullet.getPower())` directly as indexes into `hitCount` and `missCount`, each initialized with length 2. `DataBullet.extract` returns a signed byte, matching the out-of-range positive and negative indexes. The registry records the cause as `robot-bullet-metadata-byte-outside-two-bucket-array`, owned by the robot.

## Finding

NewTest's Classic-only errors recur, and its own bytecode uses an extracted bullet-power byte as an unchecked index into two-entry arrays. This explains the observed out-of-range values; Tank Royale reported no errors in the current pair. The difference in which bullet powers produce those values remains unexplained. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/com.blogspot.malinkody.DestrobotMalin_1.0.jar` (`score-review`).

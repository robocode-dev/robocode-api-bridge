---
id: AN-362
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-086, AN-236]
title: DestrobotMalin confirmation stops on a selected opponent error
provenance: inferred
reversal-cost: low
---

# AN-362 — DestrobotMalin confirmation stops on a selected opponent error

## Risk investigated

Whether `com.blogspot.malinkody.DestrobotMalin_1.0.jar`'s historical melee error imbalance persists under the latest artifacts, and whether the current failed attempt identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `d3205656d01007c800f5fa5549d247aa7030a40380277752ecd590e091339c27`; selected opponent hashes are recorded in the registry. Observation `296b8b5363eb2eb2` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `a1c16d67b05995899c2ef033a6495b365f0361ad`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The confirmation attempted three pairs but stopped during the third Tank Royale attempt, after two completed score pairs. Those pairs averaged 115,087 Classic points and 112,916 Tank Royale points, for a partial mean delta of −1.9%; both pair deltas were −1.9%. The third pair is incomplete, so this is not a five-pair confirmation. The registry status is `DISCREPANCY (errors)`.

Tank Royale's bridge-only signature was `java.util.ConcurrentModificationException` at `amk.ShizzleStiX.Navigator.run`. `amk.ShizzleStiX.ShizzleStiX_0.6.jar` is in the selected opponent pool. The preceding observation `835662a02ced1697` recorded 360 Classic errors and 31 Tank Royale errors with a −1.2% delta; AN-236 documents the known selected-pool ChumbaMini stream-limit error from that run. This current attempt names another selected-opponent failure and does not demonstrate a new bridge defect.

Skipped-turn telemetry was captured with three events in the first attempt and one in the second; the failed third attempt is incomplete.

## Finding

DestrobotMalin's five-pair confirmation is blocked by an exception from a selected opponent, so its current score and error parity remain unconfirmed. The evidence supports the existing opponent-pool contamination diagnosis; it does not identify a DestrobotMalin or bridge defect. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.cgarias.rc.AdvancedTrackerII_1.0.jar` (`DISCREPANCY (errors)`).

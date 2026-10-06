---
id: AN-105
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Mijit's persistent score gap accompanies a robot-owned wave-distance index error
provenance: inferred
reversal-cost: low
---

# AN-105 — Mijit's persistent score gap accompanies a robot-owned wave-distance index error

## Risk investigated

Whether DM.Mijit_.3's historical score gap persists under current matched artifacts and whether its current Tank Royale-only exception identifies a bridge defect.

## Evidence boundary

The read-only subject jar `DM.Mijit_.3.jar` has SHA-256 `7340c3a1b751cc33f2323f13cfb1e5c499a25e23d682e7aa77f5b99538146f9d`. Current observations `ed69371a4fe64176` and `bc9081091ba6807a` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `8e0e0fe4313ff6ad8420144a0de12207a6b5e203`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. Both used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 6,207 in Classic and 3,631 in Tank Royale, a −41.5% delta, with zero reported errors on either side. The earlier observation `25e2318cd74f0417` scored 6,094 versus 3,553, a −41.7% delta, using older artifacts.

The official five-repeat confirmation stopped after its fourth attempt: three valid pairs produced a mean of 5,831.7 in Classic and 3,555.3 in Tank Royale, a −38.8% mean delta. The valid deltas were −35.2%, −43.5%, and −37.8%. Attempt four produced a Tank Royale-only `ArrayIndexOutOfBoundsException` with the first attributed robot frame `DM.GFTWave.setSegmentations`; a fifth pair was not run. No skipped turns were captured in the three completed Tank Royale runs. The registry therefore records `DISCREPANCY (errors)`, and the five-repeat score confirmation is incomplete.

The bundled `DM/Mijit.java` source and `DM.GFTWave` bytecode show `distanceIndex = (int)(distance / (900 / 5))`, followed by indexing a `[5][5][5][25]` statistics table without clamping. A distance of at least 900 produces index 5, outside the table's valid indices 0–4. The 800×600 arena's corner-to-corner distance is 1,000, so that input is possible. The harness labels the exception bridge-only because Classic did not produce the same signature in that attempt; the failing frame and source show that the unchecked index is in the robot itself.

## Finding

The historical score gap persists in the current single pair and in all three valid confirmation pairs, but the official five-pair confirmation did not complete. The Tank Royale-only exception is caused by the robot's unchecked distance bucket, not by a bridge frame. This source defect explains the failed attempt, but it does not establish the cause of the substantial score gap in the other three attempts. Keep the score discrepancy open and do not mark it confirmed from this incomplete series. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/Grystrion.RandomTrackerNOREV_1.0.jar`.

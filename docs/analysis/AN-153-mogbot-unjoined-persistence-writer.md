---
id: AN-153
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: MogBot's repeated no-score outcome follows an unjoined persistence writer
provenance: inferred
reversal-cost: low
---

# AN-153 — MogBot's repeated no-score outcome follows an unjoined persistence writer

## Risk investigated

Whether MogBot's historical Tank Royale no-score outcome recurs with current matched artifacts, and whether its file errors are caused by the bridge or by robot persistence behavior.

## Evidence boundary

The read-only subject jar `dam.MogBot_2.9.jar` has SHA-256 `7afc716ca13e8918c6f19d5d04fb4119a1f8ce825cc44f873e48329233edc646`. The current official observation `5a9273b5247b8765` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `d676feb40ebaef1095016ae6619b19e631292b79`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 6,530 with 40 errors, including `InvalidClassException`, `StreamCorruptedException`, `UTFDataFormatException`, `ClassCastException`, and `ZipException`. Tank Royale produced no score with five errors: two `IOException: Stream Closed` entries from `dam.MogBot.saveSettings`, two `EOFException` entries, and a worker-without-result entry. Skipped-turn telemetry was incomplete. The registry status is `DISCREPANCY (outcome)`.

Earlier observations `d949ac75405a14c5` and `6003e69f1e0dcce6` also had Tank Royale no-score outcomes, with 3 and 5 errors; the latter included `EOFException`. Classic completed with scores of 6,025 and 5,996 while recording 24 and 42 file/serialization errors, respectively.

## Finding

The bundled robot starts a new `SaveThread` from both `onDeath()` and `onWin()` and does not wait for it to finish. Each thread serializes the shared target list to the same `mogbot.dat` through `ZipOutputStream` and `ObjectOutputStream`. The bridge closes leftover robot file streams at the next round start, after the previous bot thread has stopped; the robot's separate save thread is not joined by the robot. This matches the current `Stream Closed` origin and provides a robot-owned explanation for the repeated serialization failures. The diagnosis is `robot-unjoined-asynchronous-persistence-writer`, owner `robot`. The evidence does not prove this is the only reason the Tank Royale worker returned no result. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dans.Cinnamon_1.2.jar` (`score-review`).

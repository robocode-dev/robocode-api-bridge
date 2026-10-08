---
id: AN-316
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: MiniSurreptitious's old bridge error stays absent, but an opponent iterator aborts confirmation
provenance: inferred
reversal-cost: low
---

# AN-316 — MiniSurreptitious's old bridge error stays absent, but an opponent iterator aborts confirmation

## Risk investigated

Whether `ags.surreptitious.MiniSurreptitious_0.0.1.jar`'s historical Tank Royale startup `NullPointerException` recurs under the latest artifacts, and whether the current error discrepancy identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `0c1cdf2e268c5e96d3a24063df946d694c879d499bf2df55a987539cdfbc18ad`. The official retry `76f7a7897e8e6c2e` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the selected opponent jars recorded in the registry; all jars remained read-only.

## What was tried

The five-pair confirmation stopped after four attempts, with three completed pairs and no fifth score. The completed deltas were −3.8%, −3.6%, and −2.1%, for a partial mean of −3.17%. The fourth attempt recorded a Tank Royale-only `ConcurrentModificationException` from `amk.ShizzleStiX.Navigator.run` at line 40; the registry status is `DISCREPANCY (errors)`, and skipped-turn telemetry for that attempt is incomplete.

The failing class belongs to selected opponent `amk.ShizzleStiX.ShizzleStiX_0.6.jar`, not the subject. Read-only source inspection shows `Navigator.run()` iterating `bot.Sensors.field.enemies` and calling `bot.execute()` inside that iteration. The opponent's `Sensory.onScannedRobot()` callback adds to the same enemy list. That callback mutation invalidates the iterator and explains the exception in the opponent's own code. The registry's `bridge_only_signatures` field means the exception appeared only in Tank Royale; its stack points to the opponent robot, not bridge code.

AN-190's earlier run confirmed that MiniSurreptitious's historical `NullPointerException` in `move` did not recur after the `initial-status-before-run` repair. It did not recur in the three completed pairs here, and the current error signature is from a different robot.

## Finding

The historical MiniSurreptitious bridge error remains absent in the completed pairs, but a selected opponent's unsafe iteration across `execute()` stopped the confirmation before five pairs. The partial score mean is not a five-pair confirmation. The current stack does not establish a bridge defect, and no bridge or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/ahf.NanoAndrew_.4.jar` (`DISCREPANCY (errors)`).

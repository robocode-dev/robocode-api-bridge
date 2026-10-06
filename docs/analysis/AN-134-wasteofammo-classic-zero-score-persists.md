---
id: AN-134
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: WasteOfAmmo's Classic zero score persists without runtime errors
provenance: inferred
reversal-cost: low
---

# AN-134 — WasteOfAmmo's Classic zero score persists without runtime errors

## Risk investigated

Whether cli.WasteOfAmmo's historical Classic zero-score result recurs with current matched artifacts, and whether the robot bytecode explains the score difference.

## Evidence boundary

The read-only subject jar `cli.WasteOfAmmo_1.0.jar` has SHA-256 `c899395217eb5b9a8c9bdcd3e8869616ec23576e787e4ce3e2c7c3263695b36f`. The current official observation `400dfd93c4f1fa3c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `2508decaaa83f4f2c022c1c55c50ece0d3259bbb`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 0 and Tank Royale scored 540. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `DISCREPANCY (no score)`.

Earlier observations `ad72f39287f5b880` and `2f903f1fb948749b` also scored 0 in Classic, while Tank Royale scored 1,380 and 1,400, with no errors. Read-only disassembly shows the robot's `run()` loop issues radar turns and gun turns, with conditional firing, but does not call `execute()`. This is an observed behavior of the robot; the disassembly alone does not establish why its Classic score is zero.

## Finding

The Classic zero-score result recurs without runtime errors, while Tank Royale scores 540 in the current pair. Earlier Tank Royale scores were higher, so the size of that side's score is also variable. The robot bytecode provides a behavioral lead but does not establish the score cause. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/com.arsenic.NewTest_1.0.jar` (`DISCREPANCY (errors)`).

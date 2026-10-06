---
id: AN-112
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Okami's Classic-only file-read errors follow its unchecked missing-history-file path
provenance: inferred
reversal-cost: low
---

# AN-112 — Okami's Classic-only file-read errors follow its unchecked missing-history-file path

## Risk investigated

Whether axeBots.Okami's Classic-only file-read errors recur with current artifacts and whether they identify a bridge defect.

## Evidence boundary

The read-only subject jar `axeBots.Okami_1.04.jar` has SHA-256 `608f42ae73765a352e9034e41f36af721cd745835a19a20f8fe7e8bbe3be30fa`. The current official observation `abb124f1b2d515b0` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `e88ad2ae7f60d13e5840febdd209eba37ac38b66`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 5,364 in Classic and 5,253 in Tank Royale, a −2.1% delta. Classic reported two `IOException trying to read: java.lang.NullPointerException: name can't be null` messages; Tank Royale reported zero errors. Tank Royale captured skipped-turn events for both bots on turn 1 of round 1. The registry status remains `DISCREPANCY (errors)`.

The same two Classic file-read messages appeared in historical observations `74bce683db58a4ec` and `cf091ab89a700f92`, while their Tank Royale runs reported zero errors. The bundled source shows that `AxeFiles.findFile()` returns null when no matching file exists. Both `SegmentedGFs.load()` and `FlatPilot.load()` pass that result to `new FileInputStream(f)` without a null check, catch the resulting throwable, and print the same `IOException trying to read` label. This accounts for the logged NPE message in the robot's missing-history-file path. The exact reader used for each of the two messages was not captured.

After the current run, the staged Tank Royale data directories contained `.gfs` files and the Classic worker home contained none. This is post-run state and does not show what files existed when the reads occurred, so the reason the error appeared only on Classic remains unresolved.

## Finding

Okami's current score is within the review threshold, and the historical Classic-only file-read messages recur. The bundled robot code explains how a missing history file becomes the caught NPE message; the evidence does not establish why file lookup differed between engines. Keep the error discrepancy open as `robot-unchecked-null-history-file`; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/az.Ololobot_0.2.4.jar`.

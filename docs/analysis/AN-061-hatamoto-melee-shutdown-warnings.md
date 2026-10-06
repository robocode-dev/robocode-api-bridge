---
id: AN-061
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-055, AN-060]
title: HataMoto's old outcome discrepancy no longer reproduces, but Classic logs stop warnings
provenance: inferred
reversal-cost: low
---

# AN-061 — HataMoto's old outcome discrepancy no longer reproduces, but Classic logs stop warnings

## Risk investigated

Whether the current `meleerumble/axeBots.HataMoto_3.09.jar` result still has an outcome gap, and whether the remaining errors identify the subject robot, bridge, or pinned melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `293a92846b40195a5de25c7379f72e0dcb08297f79d2b6fffd7d3a95b50afb2c`. The current official one-pair observation `30002d21cb0e30ae` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `06be2efa9742916e870916b4920d86243530a14d`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The current official run scored 114,495 in Classic with 796 errors and 110,962 in Tank Royale with zero errors. The single-pair score delta was −3.1%, within the 25% review threshold, and does not confirm a score gap. The earlier observation `f02a222e13047011` had no Tank Royale score and 31 Tank Royale errors; that outcome symptom did not recur with the current local artifacts.

The bundled `axeBots.HataMoto` source has a conventional `while (true)` run loop that calls `execute()`. The current Classic log contains 14 `Unable to stop thread: axeBots.HataMoto 3.09` warnings. Classic's `RobotThreadManager.stopSteps()` interrupts and then attempts to stop a live robot thread, logging this warning if it remains alive; Classic's own `TestUndeadThread` expects this warning in its test case. Tank Royale recorded no errors in the current run, consistent with the wrapper's run-loop stop transformation. These Classic shutdown warnings remain in the observation and are not attributed to the bridge.

The updated exception parser names `amk.ChumbaMini.saveData` for the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` for the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named exceptions match the fixed-pool failures established in AN-015 and observed in AN-049 through AN-060.

## Finding

The old missing-score outcome does not reproduce. The current score is within the review band, while the error discrepancy consists of Classic shutdown warnings for HataMoto's unbounded run loop and named Classic exceptions from the fixed opponent pool; Tank Royale reports no errors. Tag the pool errors `melee-opponent-pool-contamination`, owner `harness`, and the Classic warnings `classic-undead-thread-stop-warning`, owner `classic`. Retain `DISCREPANCY (errors)` while the official run includes those errors. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/az.Ololobot_0.2.4.jar`.

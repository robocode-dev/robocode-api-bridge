---
id: AN-031
type: analysis
status: active
links: [P-001, CAP-005, CAP-006]
title: SittingDroidTeam confirms a survival-score gap without locating its cause
provenance: inferred
reversal-cost: low
---

# AN-031 — SittingDroidTeam confirms a survival-score gap without locating its cause

## Question

Does `teamrumble/logiblocs.SittingDroidTeam_1.0.jar` still show a score discrepancy on matched current artifacts, and do its score components identify the cause?

## Evidence boundary

The read-only team jar has SHA-256 `2a0e5f3ad9529ba8daa18ab5e6f12710751e04ed6ac5e60ea5054a3f513601b1`; the selected Classic member is `logiblocs.SittingDroid 1.0`, whose class implements `Droid` and returns from `run()` without movement or fire commands. The five-repeat confirmation used official teamrumble settings (1200×1200, 10 rounds, two teams), bridge commit `dbb5c5525637f174bec01176677fcbfab70aa395`, Tank Royale source commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, local Runner 1.4.0 (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API 1.4.0 (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The Classic installation reported version 1.11.1; the reviewed Classic source checkout is commit `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`.

The component capture was a separate isolated pair on the same official parameters, run on 2026-10-05 with `COMPAT_DATA_DIR` and `COMPAT_WORK_DIR` under `%TEMP%`; it is diagnostic evidence and is not an additional confirmation sample. The collection jar remained read-only.

## Results

The latest tracked observation `61afa98612b51fb6` is `CONFIRMED (score)`: all five official samples scored 4,000 in Classic and 0 in Tank Royale, each with a −100.0% delta. The recorded runs contain no bridge-only error signatures.

In the separate component capture, Classic completed with two team results at 2,000 points each, entirely from survival; last-survivor, bullet, and ram components were zero. Each of the ten Classic robot consoles recorded one death in each of ten rounds. Tank Royale completed ten rounds with two team results at rank 0 and zero in every score component; it recorded no runtime errors.

The five-repeat bridge telemetry recorded skipped turns as late as round 4, turns 1444 and 1445. This is scheduling evidence and does not identify bot deaths or their scoring order.

## Source review

Classic `RobotStatistics.scoreSurvival()` awards 50 points to an active robot, and `Battle.handleDeadRobots()` invokes it for live robots on other teams. Tank Royale source at the pinned revision applies 0.1 inactivity damage to every bot after the shared inactivity counter exceeds 450 turns. `ScoreTracker.registerDeaths()` removes all newly defeated participants before awarding survival to the participants still alive on other teams, and round processing can end as a draw when no bots or teams remain active. The existing score criteria and tests require points for each newly defeated opponent when another team remains alive and require no last-survivor award for a draw.

## Finding

The result confirms a large score gap, but it does not show a last-survivor bonus discrepancy: both engines reported zero for that component. Tank Royale's score path can produce a zero-scoring draw when no opposing participant remains alive at the death event, which is compatible with the observed result; the captured output does not prove that this exact death sequence occurred. The cause of Classic awarding survival points while Tank Royale awarded none therefore remains unlocalized, and the current evidence does not justify changing score criteria or Tank Royale code.

The source review and its limits are recorded in `clue:robocode-dev/tank-royale/AN-003`.

## M-006 handoff

Keep observation `61afa98612b51fb6` as a confirmed score discrepancy with no diagnosis event. A follow-up must capture per-bot disabled/death turns in Tank Royale and compare them with Classic before assigning the discrepancy to scoring or lifecycle behavior. Continue in teamrumble registry order with `teamrumble/lxx.ConceptATeam_0.8.jar`.

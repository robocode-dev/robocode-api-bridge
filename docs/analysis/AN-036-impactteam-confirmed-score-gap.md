---
id: AN-036
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CRIT-005, AN-002, AN-020, AN-021]
title: ImpactTeam repeats a higher Tank Royale score without locating the cause
provenance: inferred
reversal-cost: low
---

# AN-036 — ImpactTeam repeats a higher Tank Royale score without locating the cause

## Question

Does the `teamrumble/mn.nano.perceptual.ImpactTeam_1.3.0.jar` score gap persist on current matched artifacts, and does the read-only robot source or score breakdown locate its cause?

## Evidence boundary

The collection jar has SHA-256 `909094b34b95955fee95cab355a2a8589fa54ad65ee848b91e781fc538c70391`; its selected robot is `mn.nano.perceptual.Impact 1.3.0`. Observation `d6c0e206a3083e6a` records five official pairs on a 1200×1200 field, 10 rounds, and two teams of five members, with Classic Robocode 1.11.1. The bridge commit was `e7f974562cd55d2f60ef21f936d6b271648c1eac`. Runner 1.4.0 SHA-256 was `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`; Bot API 1.4.0 SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`. Bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`. The Tank Royale checkout was at `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`; the tested artifacts were built from the unchanged production source at `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`.

The five paired deltas were +14.4%, +16.7%, +19.6%, +12.6%, and +17.2%. Mean Classic score was 24,947.6 and mean Tank Royale score was 28,957.0, a +16.1% mean. The repeated-score rule classifies this as `CONFIRMED (score)`. The confirmation recorded no bridge-only exception signatures; the worker results for the fifth pair contain no battle errors. The collection jar remained read-only.

## Score components

The worker result files retained for the fifth pair report these team totals:

| Component | Classic | Tank Royale |
|---|---:|---:|
| Total score | 24,769 | 29,038 |
| Survival | 12,500 | 12,400 |
| Last survivor bonus | 950 | 800 |
| Bullet damage | 5,514 | 7,496 |
| Bullet damage bonus | 544 | 492 |
| Ram damage | 4,564 | 7,361 |
| Ram damage bonus | 697 | 488 |

This pair's survival scores are close, while Tank Royale records more bullet and ram damage. The component values are rounded per participant, so their displayed Tank Royale sum is one point below its reported total. One pair's component mix narrows the observed score difference but does not establish whether it comes from robot trajectories, engine physics, or damage accounting.

## Source review

The outer team descriptor lists five copies of the same `Impact` robot. Its bundled source scans for opponents, ignores names returned by `getTeammates()`, selects a bullet power based on distance, aims with linear prediction, continuously moves forward or backward, and turns its radar. It does not call the team-message API. No wrapper startup failure appeared in the current batch; the Tank Royale runner log for the fifth pair reports all ten bots ready before the battle began.

The bridge routes the robot's fire, move, body-turn, gun-turn, radar-turn, and teammate-name calls through the current peer. This source review did not identify a concrete bridge defect in those paths. The score components are compatible with a trajectory or damage difference, but the independent stochastic battles do not compare matching trajectories or projectiles, so assigning that cause to either engine or the robot would exceed the evidence.

## Finding

The current artifacts reproduce a systematic Tank Royale score advantage. The fifth pair places most of its positive score movement in bullet and ram damage rather than survival. The source and available measurements do not locate why those damage totals differ. The registry retains the earlier `nested-team-jar-discovery` wrapper diagnosis and appends `unresolved-confirmed-team-score-gap`, owner `unknown`, for the score discrepancy.

## M-006 handoff

Keep the subject open as `CONFIRMED (score)` with unknown ownership. A useful next diagnostic must compare Impact's scan observations, requested movement and turns, fire requests, and projectile outcomes across engines; current score totals and one pair's components cannot select a bridge or Tank Royale repair. Do not change bridge behavior until such evidence identifies a classic behavior the bridge fails to reproduce.

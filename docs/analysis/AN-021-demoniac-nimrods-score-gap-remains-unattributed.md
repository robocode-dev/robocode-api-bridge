---
id: AN-021
type: analysis
status: active
links: [P-001, CAP-005, CRIT-005, AN-002, AN-016, AN-020]
title: Demoniac Nimrods repeats the higher score gap without identifying its cause
provenance: inferred
reversal-cost: low
---

# AN-021 — Demoniac Nimrods repeats the higher score gap without identifying its cause

## Question

Does the confirmed Tank Royale score advantage for `teamrumble/cx.mini.DemoniacNimrods_0.50.jar` identify a bridge defect or a robot defect, or does it remain an unlocalized parity signal?

## Evidence boundary

This was a prepared Windows environment using PowerShell, Python 3.13.15, JDK 17.0.17 for classic Robocode 1.11.1, bridge commit `8e93649f3f4415cbcacbc6479a46eb8556a6d7a6`, and [Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`](https://github.com/robocode-dev/tank-royale/tree/5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb). The local runner and Bot API were both 1.4.0 from that Tank Royale revision; their SHA-256 values were `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` and `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752` respectively. The bridge API and wrapper artifacts had SHA-256 values `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The read-only collection JAR had SHA-256 `742983458acb65cb939258dbaaf6a931870606383852284df77c3374a860c098`. Its nested `cx.mini.Nimrod_0.50.jar` and bundled source were copied or extracted only under `%TEMP%` for inspection. Each official pair used teamrumble settings: a 1200×1200 field, 10 rounds, and two teams of five Nimrod processes. Tank Royale does not expose a deterministic seed for replay across engines; these scores are stochastic evidence, not a deterministic acceptance result (see AN-002).

## What was tried

Five current official pairs were captured with score confirmation and skipped-turn telemetry. The classic team scores were 21,234, 21,412, 21,960, 22,051, and 21,145; the Tank Royale scores were 24,777, 24,496, 25,459, 24,728, and 25,485. The paired deltas were +16.7%, +14.4%, +15.9%, +12.1%, and +20.5%, for a +15.92% mean. The repeated-score rule labels this `CONFIRMED (score)`; the sweep's ordinary single-pair report threshold remains ±25%.

The previous five-pair observation `f9da598653e8d726` had a +15.50% mean on earlier bridge and Tank Royale commits. The current five pairs reproduce a similar direction and magnitude on the pinned commits above; the batches are not pooled because their artifacts differ.

The last pair had no classic or Tank Royale runtime errors. Its team score components were:

| Component | Classic | Tank Royale |
|---|---:|---:|
| Total score | 21,145 | 25,485 |
| Bullet damage | 6,505 | 11,120 |
| Bullet damage bonus | 867 | 1,013 |
| Survival | 12,500 | 12,450 |

The higher bullet-damage component accounts for most of that pair's score difference. It does not establish why more damage was recorded: the engines began from independent random battle states, and this evidence does not compare the same bullet trajectories.

All five Tank Royale captures completed. Their unique skipped-turn event counts were 0, 2, 1, 4, and 6; every recorded event was round 1, turn 1. The six events in the final capture were split across both teams. The runner log says all ten bots were ready before the game started. The varying, symmetric turn-1 events may add noise, but these captures do not link them causally to the score advantage.

The nested source shows that `Nimrod` selects a non-teammate from scan callbacks, learns gun offsets from `MeleeWave` history, and continuously turns its radar. Its `doShoot()` catches exceptions. It does not use team-message APIs. Source inspection identified no specific robot defect or bridge call that explains the higher Tank Royale bullet damage.

Two bridge-semantics candidates were checked because they affect Nimrod's targeting loop. Tank Royale's event queue dispatches each turn's `TickEvent` at priority 130 before `ScannedBotEvent` at priority 20, and the bridge sets `deliveredTurn` in its tick callback; a scan callback therefore reads the current turn from `getTime()`. The Tank Royale 1.4.0 API also clamps maximum speed to 8 and radar turn rate to the legal maximum, including Nimrod's infinite radar-turn request. These paths reveal no direct timing or speed mismatch.

Static state was checked against the installed classic 1.11.1 `robocode.host` bytecode and the [classic Robocode source at commit `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`](https://github.com/robo-code/robocode/tree/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976). Classic caches the robot class in its classloader and creates a new instance from that same class at each round start; static references are cleared when the classloader is cleaned up after the battle. Nimrod's static wave history can persist between rounds on both engines, so it is not a bridge-only lifecycle difference.

## Findings

This is a reproducible score discrepancy on the current pinned build, with no asymmetric runtime error and no located cause. The existing registry diagnosis remains `unresolved-confirmed-team-score-gap`, owner `unknown`. The source and score components do not justify assigning the cause to the robot, the bridge, or the Tank Royale engine.

The skipped-turn records are a separate measured signal. Their turn-1 clustering and variation across samples are consistent with first-turn timing sensitivity, but the evidence here does not prove that interpretation or show that it explains the score gap.

## Rejected conclusions

Higher bullet damage alone is not evidence of a scoring-mapper defect; this run lacks projectile-level evidence showing whether the difference comes from targeting, collisions, or damage accounting. The turn-1 skipped-turn events are not an established root cause because they vary between repeats, occur on both teams, and were not correlated with per-round score changes. Different static-state lifetimes and stale `getTime()` during scan callbacks were considered and rejected using classic 1.11.1 bytecode and the pinned Tank Royale event queue. The bundled source contains assumptions that deserve review, but no assumption inspected here reproduces the observed cross-engine score direction by itself.

## M-006 handoff

Keep this case open with unknown ownership. A useful next diagnostic must compare scan observations, requested radar and gun turns, fire requests, and bullet outcomes on both engines across independent official pairs; this batch did not collect those traces. Do not change bridge behavior until such evidence identifies a classic behavior the bridge fails to reproduce.

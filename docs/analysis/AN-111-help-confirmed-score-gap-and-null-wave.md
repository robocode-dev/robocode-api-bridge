---
id: AN-111
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Help's score gap confirms while its historical null-wave error is robot-owned
provenance: inferred
reversal-cost: low
---

# AN-111 — Help's score gap confirms while its historical null-wave error is robot-owned

## Risk investigated

Whether ary.Help's historical outcome and score discrepancies persist with current matched artifacts and whether its old Tank Royale-only exception identifies a bridge defect.

## Evidence boundary

The read-only subject jar `ary.Help_1.0.jar` has SHA-256 `53b6ea8218bd5d68255505bee581a741a88511e58840a395b855ec8f69c2355d`. Current observations `fd4f39093bf5873f` and `802cd57fbdc37a4d` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `efd36148675a074a9ebeb991198b4e6844cfeb6d`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. Both used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 4,913 in Classic and 3,332 in Tank Royale, a −32.2% delta, with zero errors on either engine and no skipped turns. The official five-pair confirmation produced mean scores of 4,893.2 and 3,265.2, with a −33.32% mean delta. Its five deltas were −36.3%, −28.4%, −35.3%, −36.3%, and −30.3%. All five pairs were valid, no skipped turns were captured, and no bridge-only failure interrupted the confirmation. The registry status is `CONFIRMED (score)`.

The earlier observation `a5fc6afd26a475b9` scored 4,834 in Classic and 2,960 in Tank Royale, a −38.8% delta, with no reported errors. It used Bot API 1.2.0 and the older examples runner.

The historical observation `3e13e72d5b4a27ae` had a Tank Royale-only `NullPointerException` in `ary.Help.onHitByBullet`: the worker reported that `surfWave` was null when calling `distanceToPoint`. The jar bundles bytecode but no Java source. Disassembly shows `onHitByBullet()` copies the `_surfWave` field to local `surfWave` and immediately invokes `distanceToPoint` without a null check. The registry records this exception as robot-owned under `robot-null-surf-wave-on-hit-by-bullet`. The current five-pair confirmation did not reproduce a bridge-only failure.

## Finding

Help has a confirmed current score gap, while its historical null-wave exception did not recur in the current pair or interrupt the five-pair confirmation. The bytecode shows the exception comes from the robot dereferencing its nullable `_surfWave` field without a guard. That robot defect does not explain the separate confirmed score gap. Keep the score cause open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/axeBots.Okami_1.04.jar`.

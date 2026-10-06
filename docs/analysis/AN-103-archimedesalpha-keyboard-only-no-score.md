---
id: AN-103
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: ArchimedesAlpha's no-score result comes from keyboard-only controls
provenance: inferred
reversal-cost: low
---

# AN-103 — ArchimedesAlpha's no-score result comes from keyboard-only controls

## Risk investigated

Whether ArchimedesAlpha's repeated zero-score result identifies a bridge discrepancy or a robot that requires interactive input.

## Evidence boundary

The read-only subject jar `ArchAlpha.ArchimedesAlpha_1.0.jar` has SHA-256 `dcb4cdb1a904ba42b0747af1b29c660fe5fd01c225f4f079875cc07499b36554`. The current official observation `3120c221c1dbfb93` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `f78861464dbea90c65e2d35c6d339892ac03d4d9`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 2 participants, 35 rounds, and an 800×600 arena. The matched local Bot API and runner artifacts are recorded in the observation. The jar remained read-only.

## What was tried

The harness force-ran ArchimedesAlpha at official parameters. Classic and Tank Royale both scored zero, reported zero errors, and completed all 35 rounds. Tank Royale captured no skipped-turn events. The older observation `5db318c40ca0955b` had the same zero-score, zero-error result on both engines with older bridge and Tank Royale artifacts.

The jar bundles `ArchAlpha/ArchimedesAlpha.java`. Its movement and firing fields start at zero; the `run()` loop only applies those fields and executes a turn. `onKeyPressed()` is the only code that assigns nonzero movement, turning, or fire values. Since the headless compatibility harness supplies no keypresses, both engine runs leave the robot stationary and never fire. This explains the shared zero score without implicating the bridge.

## Finding

ArchimedesAlpha's no-score outcome is caused by its keyboard-only controls in a headless run. The current registry status remains `DISCREPANCY (no score)` under the existing harness policy, with a robot-owned diagnosis; this observation does not establish a Classic-versus-Tank-Royale behavior gap. No code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/DM.Chicken_4.0.jar`.

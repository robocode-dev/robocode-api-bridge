---
id: AN-048
type: analysis
status: active
links: [P-001, CAP-006, TEAM-001, AN-039]
title: Bakery's duplicate robot properties class name aborts team staging
provenance: inferred
reversal-cost: low
---

# AN-048 — Bakery's duplicate robot properties class name aborts team staging

## Question

Does the current `teamrumble/vuen.Bakery_2.51.jar` wrapper failure mean a team member is missing, or can the bridge stage the class that the team descriptor names despite a stale `robot.classname` property?

## Evidence boundary

The read-only team jar has SHA-256 `e7b4b6eb7945f581a6feb8be489ca79859abd18efbcaf0b6127c3cb79249c367`. It contains `vuen/Bakery.team`, `vuen/Cake.properties`, `vuen/CupCake.properties`, `vuen/Cake.class`, and `vuen/CupCake.class`. The team descriptor lists two `vuen.Cake 2.51` members and three `vuen.CupCake 2.51` members, but both properties files declare `robot.classname=vuen.Cake`; the jar was not rewritten.

The pre-fix official observation `6fe78f0f9056f18d` completed on 2026-10-05 with Classic Robocode 1.11.1, bridge commit `0394ce03429f854d5d5fc600f45cc968a6a551aa`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API 1.4.0, Runner 1.4.0, and wrapper 0.3.1. It used official teamrumble parameters: two teams of five, 1200×1200 arena, and 10 rounds. Classic scored 19,456 with no errors. Tank Royale produced no score and failed before the battle with `robots-wrapper produced 0 team entries, expected one`; the registry status was `DISCREPANCY (outcome)`.

A read-only wrapper reproduction logged two `vuen.Cake 2.51` entries, then aborted while copying `CupCake.properties` to the already-created `vuen.Cake_2.51/vuen.Cake.properties` path. `processJar` keyed output directories by the declared `robot.classname`; the duplicate declaration collides before the second pass processes `Bakery.team`. That accounts for the harness finding no team entry even though the jar contains the descriptor and both classes.

## Finding

This is a wrapper defect against the existing `TEAM-001` requirement that a team jar produce a runnable Tank Royale bot directory. When a duplicate declared class name collides, the wrapper now derives the member class from the `.properties` entry path only if the corresponding `.class` entry exists in the same jar. This lets `CupCake.properties` resolve to the `vuen.CupCake` class named by `Bakery.team` while preserving the normal metadata path for non-colliding properties. Tag the cause `team-member-properties-classname-collision`, owner `bridge`.

The repair is in commit `b1c6ef36b734b21a86b95ab66572fe98e7f800ea`; `CHANGELOG.md` records the bugfix. The wrapper fat jar SHA-256 is `bf5fa0ba77a8a7454a53f57dedbdf7b74cb7be3940ee806db1b490aa8a8f6b9d`.

The post-fix official observation `f30b9524f3fdf385` completed on 2026-10-05 against bridge commit `b1c6ef36b734b21a86b95ab66572fe98e7f800ea`, the same local Tank Royale commit, Bot API 1.4.0, Runner 1.4.0, and wrapper 0.3.1. Classic scored 19,120 and Tank Royale scored 19,314, a +1.0% delta against the 25% threshold. Both engines completed with zero errors and the registry status is `PASS`; skipped-turn telemetry was captured. The bridge API jar SHA-256 was `7b9694ef4dc67d4be47e36be69a5bba55f7c9006cc43ce27afa1747857afbab3`.

## Route

Recommended route: simple. This restores the existing `TEAM-001` runnable-team behavior without changing an acceptance criterion, capability, or policy.

## M-006 handoff

`vuen.Bakery_2.51.jar` is the final teamrumble registry entry. Continue the open M-006 parity campaign with the next unresolved registry subject in collection order; keep the pre-fix outcome and post-fix pass in history.

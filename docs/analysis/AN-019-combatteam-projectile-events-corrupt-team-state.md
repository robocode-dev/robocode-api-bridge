---
id: AN-019
type: analysis
status: active
links: [P-001, CAP-003, CAP-006, CAP-007, PDR-002]
title: CombatTeam exposes duplicate projectile outcomes and missing callback timestamps
provenance: inferred
reversal-cost: low
---

# AN-019 — CombatTeam exposes duplicate projectile outcomes and missing callback timestamps

## Question

Why does the read-only `teamrumble/mn.CombatTeam_3.25.0.jar` fail with `NullPointerException` at `mn.c.e.equals` after the bridge starts batching team messages?

## Findings

Read-only bytecode inspection follows the failure through `mn.c.i.a`, `mn.c.h.c`, and `ArrayList.removeAll`. A received `mn.c.b` payload contains a null entry in its removal collection. Its constructor copies that collection before transmission, and its Java serialization has no custom hooks. The originating collection is populated by `mn.c.a.b(mn.c.e)`. Its caller, `mn.c.d.a`, handles `BulletMissedEvent` and appends the result of `mn.g.b.a(Bullet,long)` without checking for null. That lookup returns null when Combat's collection of recorded firings is empty. The observed stack therefore does not by itself identify message corruption or an ordering defect in the batch decoder.

The bridge constructed projectile callback events without setting their time, although the corresponding event-list mappers preserve it. Combat uses `event.getTime()` when matching projectile outcomes to recorded firing state. Bridge commit `a921661` preserves the original Tank Royale turn in the four projectile callbacks. One official-parameter rerun with this repair alone still failed: classic scored 21,526 without errors, while Tank Royale stopped with five errors. The repair is a concrete clock correction, but it does not establish the NPE's cause or elimination.

A subsequent diagnostic run logged only repeated terminal outcomes for a projectile, rather than every message. It captured `BRIDGE_DUPLICATE_BULLET bot=8 round=2 turn=155 bullet=64 kind=hit`, as well as repeated hit and projectile-collision callbacks for other bots. That run had classic score 21,173 without errors and four Tank Royale errors. The temporary diagnostic code was removed before the next build.

Tank Royale's `CollisionDetector.detectBulletHits` collects all geometric intersections before applying any outcome. `applyBulletHitResults` previously applied every collected outcome even after another outcome removed the projectile. Thus one projectile could produce multiple terminal events, damage awards, and firing-record removals. The local Tank Royale repair `1765c6803` ignores a projectile collision when either projectile has already been removed, and ignores a bot hit when its projectile has already been removed. It retains the existing projectile-collision-before-bot-hit order. The causal link from these repeated outcomes to the null removal entry remains an inference until repaired runs establish the behavior.

## Evidence boundary

The investigation ran on Windows on 2026-10-04 against Tank Royale revision `c8ad3a8d19a843f6258d6f6f9db7f29229963903` and the official teamrumble parameters. The collection jar was never changed. Bot API and runner artifacts for the repaired experiment were rebuilt locally from the same Tank Royale checkout; bridge artifacts were rebuilt against Bot API version 1.4.0. No Tank Royale release is required by this experiment. Generic same-turn batch ordering evidence is recorded in bridge commit `19c4c1d`; it does not prove the full Combat event stream matches classic.

## Campaign status

The first official-parameter battle with both repairs completed without errors on either engine: classic 21,023, Tank Royale 19,707, delta −6.3%, observation `964298d9806878d6`. Its manifest pins bridge `a92166100e7ec11c37924f10067a7aef35346674`, Tank Royale `1765c6803048ba0fd1c8802fc255162511b558d2`, and runner SHA-256 `cabb15257ad2090e90352be8e5d494235842b6594352b5024f008521280bd3f0`. This is consistent with the repeated-outcome diagnosis; it does not prove that the intermittent exception is eliminated.

Five further official-parameter pairs completed without asymmetric errors, observation `5f85b23d94a6826a`. Their score deltas were −7.0%, −6.2%, −6.4%, −7.7%, and −6.3%; the mean was −6.72%. The harness classified the case as `MATCHED (score noise)`. The registry retains the failed observations and adds the diagnosis `tank-royale-duplicate-bullet-outcomes`, owned by Tank Royale. These results support the repair for this case, without claiming exact score equality or proving an intermittent failure can never recur.

M-006 remains open for the other collection cases. The current CombatTeam case matches under the pinned local runner containing the collision repair; the result does not apply to an unrepaired published runner.

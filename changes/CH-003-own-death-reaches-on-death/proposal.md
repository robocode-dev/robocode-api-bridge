---
id: CH-003
type: change
status: open
links: [P-001, CAP-001, AN-006, C-006]
title: Establish why a robot's own death never reaches onDeath, and fix it where the cause is
---

# CH-003 — Establish why a robot's own death never reaches `onDeath`, and fix it where the cause is

## What

Take `AN-006` from a measured symptom to an established cause, repair it wherever the cause turns out to live, and re-enable the conformance test that detects it. Promote the criteria that then have passing evidence out of `@draft`.

Serves `M-001`.

## Why

`AN-006` records that `onDeath` is never called under the bridge, measured with classic's `BattleWin` robot: the death marker appears on classic and on neither bridge participant, while the win, round-end, and battle-end markers all arrive. The test that detects it is disabled, naming that record.

`EVT-004` is the criterion that promise belongs to, and it is `@draft` because the defect is live. `M-001`'s fourth clause — that the criteria the tiers prove are active rather than `@draft` — cannot be met while a criterion in its own capability has a known, unrepaired counterexample. This is the only known live defect in the repository, and every subsequent milestone is read against a foundation that still contains it.

The other reason to take it now: `AN-006` names a suspect it never confirmed, and a wrong suspect in an active record is worse than an absent one. It points at the Bot API event queue's age and criticality filter. Reading the Bot API source refutes that half directly — `DeathEvent.isCritical()` returns true, and the queue's age filter drops only non-critical events. Leaving that standing sends the next reader down a path already known to be empty.

## The hypothesis this change tests

The Bot API's `BotInternals` subscribes an *instant* handler to `DeathEvent` whose whole body stops the bot thread. The user-facing `onDeath` callback is dispatched from the event queue, on that same thread. The bridge's bot implementation extends `Bot`, so it inherits the instant handler.

If that ordering is what happens, the thread that would run the queued dispatch is stopped before it runs, and no `Bot` subclass overriding `onDeath` ever sees its own death — a Bot API defect rather than a bridge defect, affecting every consumer of the Java Bot API and not only this bridge.

The hypothesis is stated so it can be wrong. The change begins by establishing which of three things is true: the death event never reaches the queue, it reaches the queue and is discarded, or it is dispatched onto a thread that has already stopped.

## Scope

In scope:

- Instrumented measurement that distinguishes those three outcomes, run through the existing conformance tier against `tested.robots.BattleWin`.
- Correcting `AN-006`: the established cause, what was refuted and by what evidence, and the disposition of its second, separate divergence — classic splits `BattleWin`'s rounds between the two instances while the bridge gives one instance all of them. That divergence was recorded alongside the death finding without either being claimed to explain the other; it is either resolved here or it becomes its own record, not a loose end left inside a corrected one.
- The repair, if it belongs in this repository.
- Re-enabling `testEVT004_IntegrationPositive_OwnDeathReachesTheDeathHandler`.
- Promoting `EVT-004` out of `@draft` once it passes, and `EVT-005`, which is already proven by passing tests in the same class and is `@draft` only because nothing has promoted it.

Out of scope:

- The upstream repair itself. If the cause is in the Bot API, the fix belongs in the Tank Royale repository across all four language implementations under `C-006`, and that is work in that repository, not this one. This change consumes a released Bot API; it does not ship against a locally published one.
- The remaining `EVT` criteria. They need conformance robots that are not yet ported, and porting them is separate work.
- `M-001` itself. Its physics half is untouched here, so the milestone does not close in this change.

## What happens if the fix is not available in time

If the cause is upstream and no released Bot API carries the repair when this change is otherwise complete, the change still lands: the established cause, the corrected record, and the criterion's reason for staying `@draft`. The test stays disabled, with a reason that names a cause rather than an absence. That is a smaller result than a repair, and it is an honest one — what it must not do is depend on a `mavenLocal` build that no other checkout can reproduce.

## Decision boundaries

Authorized: how the instrumentation is built and where it is placed, and whether the round-split divergence is resolved here or carried to its own record.

Not authorized: shipping the bridge against an unreleased Bot API, changing anything under `robocode-api/src/main/java/robocode/` (`ARCH-002`), or promoting a criterion whose test does not pass.

## Route

Full. It promotes acceptance criteria out of `@draft`, which is accepted-contract meaning, and it may carry a decision about where the repair belongs.

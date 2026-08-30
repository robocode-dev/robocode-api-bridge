---
id: AN-006
type: analysis
status: active
links: [CAP-001, IDR-001, ADR-001, PDR-001]
title: A robot's own death still does not reach onDeath under the bridge
provenance: inferred
reversal-cost: low
---

# AN-006 — A robot's own death still does not reach onDeath under the bridge

## What was investigated

Whether `EVT-004` holds: a robot's own death reaches its death handler. The conformance tier was pointed at classic's `BattleWin` robot, which prints a distinct marker from each of `onWin`, `onDeath`, `onRoundEnded`, and `onBattleEnded`.

## What was found

`onDeath` is never called under the bridge.

Over a five-round battle of the robot against a copy of itself:

| Marker | Classic (instance 1 / 2) | Bridge (instance 1 / 2) |
|---|---|---|
| `Win!` | 3 / 2 | 0 / 5 |
| `Death!` | 2 / 3 | **0 / 0** |
| `RoundEnded!` | 5 / 5 | 5 / 5 |
| `BattleEnded!` | 1 / 1 | 1 / 1 |

Two things follow from the shape of that table.

**The death handler is the only one missing.** `onWin`, `onRoundEnded`, and `onBattleEnded` all fire under the bridge, so the robot's output is being captured, the process is alive at the end of each round, and outcome events reach it in general. The instance that lost every round printed `RoundEnded!` five times and `Death!` never.

**There is a second, separate divergence.** Classic splits the rounds 3–2 between the two instances; the bridge gives one instance all five. Identical robots should not split that way, and `BattleWin`'s handlers are independent — `onDeath` only prints — so the missing handler does not cause the lopsided outcome. These are two findings, and this record does not claim one explains the other.

## Why this was believed fixed

`TODO.md` recorded own-death reaching `onDeath` as one of the gains of the event-dispatch redesign, and the reasoning was sound: the old hand-written dispatch switch had no case for the robot's own death, so adding delegation to the Bot API's handler should have supplied one. `BotPeer`'s bot implementation does override the Bot API's `onDeath` and does call through to the mapping helper.

The claim was never tested. It was inferred from the shape of the change, and the evidence offered for it was a score report — which cannot show that a handler was not called. `EVT-004`'s justification in the extraction manifest already said this promise had never been tested; this is what that meant in practice.

## Where the cause is likely to be, and where it is not

Not a negative priority. The bridge registers `DeathEvent` at priority -1, matching classic, where the value is a fixed system-event priority. The Bot API's event queue does not filter on priority sign — it filters on event age and criticality, dispatching an event only when it is recent or marked critical.

That filter is the place to look. A death arrives at the very end of a round, and the queue is also cleared between rounds. An event that is judged old, or that is still queued when the round-boundary clear happens, is dropped without a trace — which matches the observation exactly: everything that fires mid-round or at round end fires, and only the one event that arrives at the moment the robot stops existing does not.

Confirming that requires instrumenting the Bot API's queue rather than reading it, and the fix may belong upstream under `C-006` rather than in this repository. That is `M-001` work.

## What it means

**The conformance tier paid for itself on its second robot.** This defect is invisible to the score-based instrument: the battle completes, both robots score, and the report shows a percentage. It took a test that asserts on what the robot reported.

**`EVT-004` stays `@draft`, and now has a reason rather than an absence.** The criterion was drafted because nothing tested it. It remains drafted because something now does, and the behaviour is missing.

**The test is present and disabled, naming this record.** Leaving it failing would make the build permanently red; deleting it would lose the only thing that detects the defect; asserting the current behaviour would certify it. Disabled with a reason is the one option that keeps the defect visible and the build honest.

## What this does not establish

The cause. The queue's age filter is where the evidence points, not something that has been demonstrated.

Whether robots in the collection are affected in ways that matter. A robot that cleans up or persists learned data in `onDeath` would silently stop doing so under the bridge, which is the profile of a bot that scores worse for no visible reason — but no flagged bot has been traced to this. `M-002` has a second named candidate to check, alongside `AN-005`.

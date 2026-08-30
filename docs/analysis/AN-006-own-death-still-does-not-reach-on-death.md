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

**A lopsided round split was recorded alongside it and did not survive re-measurement.** Classic split the rounds 3–2 between the two instances while the bridge gave one instance all five. Re-measured after the event-dispatch redesign and the Bot API upgrade, wins split across the instances in every battle, so it is not carried forward. A different round-outcome divergence did survive, and has its own record in [`AN-008`](AN-008-tank-royale-declares-more-round-winners-than-rounds.md).

## Why this was believed fixed

`TODO.md` recorded own-death reaching `onDeath` as one of the gains of the event-dispatch redesign, and the reasoning was sound: the old hand-written dispatch switch had no case for the robot's own death, so adding delegation to the Bot API's handler should have supplied one. `BotPeer`'s bot implementation does override the Bot API's `onDeath` and does call through to the mapping helper.

The claim was never tested. It was inferred from the shape of the change, and the evidence offered for it was a score report — which cannot show that a handler was not called. `EVT-004`'s justification in the extraction manifest already said this promise had never been tested; this is what that meant in practice.

## Where the cause is

In the Tank Royale server, before the Bot API ever sees anything.

**No death message of any kind reaches any bot.** A probe in the Bot API's `WebSocketHandler.onText`, ahead of any parsing, printed the raw payload of every message whose text contained `death` in any casing. It fired zero times in every bot's log, in a two-participant battle and in a four-participant battle where the round continues past three deaths and the survivors should be told about each. The same logs show scan, wall-hit, bullet-hit and won-round events arriving and dispatching normally.

The server emits a death with `addPublicBotEvent`, which fans out over the turn's own bots. Every turn is constructed empty and only filled from the bots map at the end of `TurnProcessor.processTurn`, and the emission ran before that snapshot, so the event was delivered to nobody. The server's own unit test for the turn pipeline reproduces it with no engine, no network and no bots: a defeated bot yields an empty event map.

The repair moves the emission after the snapshot, which also still holds the dead bots, so a bot receives its own death. It is committed upstream on the branch `fix-death-events-never-reach-bots` in the Tank Royale repository, with a positive and a negative test in the server's own suite; the positive test fails without the change. `C-006` does not apply — this is server code, not the four-language Bot API.

## What was ruled out

**The Bot API's event queue.** `AN-006` named the queue's age and criticality filter as the suspect. It is refuted twice: `DeathEvent.isCritical()` returns true and the queue exempts critical events from the age filter, and a probe printing the queue's full contents at every dispatch never saw a `DeathEvent` in it. Nothing was dropped, because nothing arrived.

**A negative priority.** The bridge registers `DeathEvent` at priority -1, matching classic. The queue does not filter on priority sign.

**The Bot API's instant death handler.** `BotInternals` subscribes a handler to `DeathEvent` whose body stops the bot thread, and the bridge's bot inherits it, so it could in principle stop the thread before the queued dispatch ran. A probe on that handler never fired.

**The bridge.** It overrides the Bot API's `onDeath`, maps it, and calls the robot's handler. A probe inside that override never fires, because it is never called.

## What it means

**The conformance tier paid for itself on its second robot.** This defect is invisible to the score-based instrument: the battle completes, both robots score, and the report shows a percentage. It took a test that asserts on what the robot reported.

**`EVT-004` stays `@draft` until a Tank Royale release carries the repair.** The criterion was drafted because nothing tested it. It stays drafted because the repair exists upstream and unreleased, and the bridge builds against released Bot API and runner artifacts rather than local ones. The reason is now a named cause with a named fix, not an absence.

**The test is present and disabled, naming this record.** Leaving it failing would make the build permanently red; deleting it would lose the only thing that detects the defect; asserting the current behaviour would certify it. Disabled with a reason is the one option that keeps the defect visible and the build honest.

## What this does not establish

Whether robots in the collection are affected in ways that matter. A robot that cleans up or persists learned data in `onDeath` would silently stop doing so under the bridge, which is the profile of a bot that scores worse for no visible reason — but no flagged bot has been traced to this. `M-002` has a second named candidate to check, alongside `AN-005`.

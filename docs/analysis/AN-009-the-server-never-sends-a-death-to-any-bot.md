---
id: AN-009
type: analysis
status: active
links: [CAP-001, AN-006, AN-008, PDR-001, C-006]
title: The Tank Royale server never sends a death event to any bot, which is why onDeath never fires
provenance: inferred
reversal-cost: low
---

# AN-009 — The Tank Royale server never sends a death event to any bot

## What was investigated

The cause of [`AN-006`](AN-006-own-death-still-does-not-reach-on-death.md), which measured that `onDeath` is never called under the bridge and named a suspect it could not confirm: the Bot API event queue's age and criticality filter. This spike set out to establish which of three things is true — the death event never reaches the queue, it reaches the queue and is discarded, or it is dispatched onto a thread that has already stopped.

None of the three. The question was wrong, because it assumed a death event arrives at the bot at all.

## What was found

**No death message of any kind reaches any bot.** Not the dying bot, not the survivors.

A probe in the Bot API's `WebSocketHandler.onText`, ahead of any parsing, printed the raw payload of every message whose text contained `death` in any casing. Across a two-participant battle and a four-participant battle, five rounds each, it fired zero times in every bot's log. The same logs show scan, wall-hit, bullet-hit, bullet-hit-bot and won-round events arriving and dispatching normally.

The four-participant run is the one that settles it. Three bots die there while the round continues, so the survivors should be told about each death. They were not.

The cause is in the Tank Royale server, in `TurnProcessor.processTurn`. A death is emitted with `addPublicBotEvent`, which fans out over the turn's own bots. Every turn is constructed empty and only filled from the bots map at the end of that pipeline, and the emission ran before that snapshot, so the event was delivered to nobody. The server's own turn-pipeline unit test reproduces it with no engine, no network, and no bot processes: a defeated bot yields an empty event map.

## What was ruled out, and how

**The Bot API's event queue** — the suspect `AN-006` named. Refuted twice. By reading: `DeathEvent.isCritical()` returns true, and the queue exempts critical events from its age filter. By measurement: a probe printed the queue's full contents at every dispatch and no death event was ever in it. Nothing was dropped, because nothing arrived.

**A negative priority.** Ruled out by reading rather than by probe: the bridge registers `DeathEvent` at priority -1, matching classic, and the queue does not filter on priority sign.

**The Bot API's instant death handler.** `BotInternals` subscribes a handler to `DeathEvent` whose body stops the bot thread, and the bridge's bot inherits it, so it could in principle stop the thread before the queued dispatch ran. This was the hypothesis the change was opened on. A probe on that handler never fired.

**The bridge.** It overrides the Bot API's `onDeath`, maps it, and calls the robot's handler. A probe inside that override never fires, because it is never called. The bridge needs no change.

## The repair

Emitting after the turn's snapshot, which still holds the dead bots, so a bot also receives its own death. Committed in the Tank Royale repository (`https://github.com/robocode-dev/tank-royale`) on the local branch `fix-death-events-never-reach-bots`, with a positive and a negative test in the server's own turn-pipeline suite; the positive test fails without the change and the server suite passes with it. At the time of writing that branch is not pushed and has no pull request, so nothing downstream can consume it yet.

`C-006` does not apply. The obligation to land a change in all four language implementations is about the Bot API; this is server code, and the server exists once.

## The controlled comparison

Every Tank Royale build was rebuilt from clean and identified by the runner jar's checksum. The first attempt at this comparison was worthless: Gradle's up-to-date check reused a stale shrunk server jar and produced byte-identical runner jars for two different source trees. Six five-round battles per build, against nine classic battles, on 2026-08-30:

| Build | wins per battle | deaths per battle |
|---|---|---|
| The runner jar the bridge builds against | 4, 5, 5 | 0 in every battle |
| Current Tank Royale `main` | 5, 5, 5, 6, 7, 7 | 0 in every battle |
| Current `main` with the repair | 4, 4, 5, 5, 6, 7 | equal to the wins, in every battle |
| Classic Robocode | 5, 5, 5, 5, 5, 4, 5, 5, 4 | 5, 7, 6, 5, 5, 6, 6, 6, 6 |

Deaths appear only with the repair. Wins are untouched by it — totals above five occur on the unrepaired build too, which is [`AN-008`](AN-008-tank-royale-declares-more-round-winners-than-rounds.md) and not this.

## What supersedes AN-006

`AN-006`'s conclusions, not its measurement. Its marker table stands as what was observed then; its suspect is refuted above.

Its recorded second divergence — classic splitting the rounds 3–2 between the instances while the bridge gave one instance all five — did not reproduce. Wins split across the instances in every battle measured here. It was recorded before the event-dispatch redesign and the Bot API upgrade, and nothing here re-establishes it.

## What this means for the criteria

`EVT-004` stays `@draft`. The behaviour is missing, the cause is established, and the repair is unreleased; the bridge builds against released Bot API and runner artifacts rather than local ones, so nothing here can promote it. The reason is now a named cause with a named fix rather than an absence.

`EVT-007` — the death of another robot reaches the survivors — is blocked by the same cause, which nobody had written down. It was recorded as merely unported. Any classic robot that tracks enemies through `onRobotDeath` is running blind under the bridge, which is a wider fidelity gap than one handler on one robot.

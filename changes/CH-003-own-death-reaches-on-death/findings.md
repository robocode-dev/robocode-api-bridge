---
id: CH-003-findings
type: findings
status: open
links: [CH-003, AN-006]
title: CH-003 measurement log — where the death event stops, and what the fix does
---

# CH-003 — what the measurement established

All measurements ran `tested.robots.BattleWin` through the conformance harness at the roborumble division, five rounds per battle, on 2026-08-30. Classic Robocode is the reference.

## The cause

**No death message of any kind reaches any bot.** Not the dying bot, not the survivors.

A probe in `WebSocketHandler.onText`, ahead of any parsing, printed the raw payload of every message whose text contained `death` in any casing. Across a two-participant battle and a four-participant battle it fired zero times in every bot's log, while the same logs show `ScannedBotEvent`, `HitWallEvent`, `HitByBulletEvent`, `BulletHitBotEvent` and `WonRoundEvent` arriving and dispatching normally. The four-participant run is the one that settles it: three bots die there while the round continues, so the survivors should receive `BotDeathEvent` for each.

The cause is in the Tank Royale server, in `TurnProcessor.processTurn`. A death is emitted with `addPublicBotEvent`, which fans out over the turn's own bots; every turn is constructed empty and only filled from the bots map at the end of the pipeline, and the emission ran before that snapshot. The event was therefore delivered to nobody. A unit test against the server's own `TurnProcessor` reproduces it with no engine, no network and no bots: a defeated bot yields an empty event map.

## What this refutes

**`AN-006`'s suspect — the Bot API event queue's age and criticality filter — is refuted twice.** By reading: `DeathEvent.isCritical()` returns true, and the queue exempts critical events from the age filter. By measurement: a probe printed the queue's full contents at every dispatch and no `DeathEvent` was ever in it. Nothing was dropped, because nothing arrived.

**The instant-handler hypothesis this change was opened on is also refuted.** `BotInternals` does subscribe an instant handler to `DeathEvent` whose body stops the bot thread, and the bridge's bot inherits it, so the ordering concern was real in principle. The probe on that handler never fired either.

The bridge is correct throughout. It overrides the Bot API's `onDeath`, maps it, and calls the robot's handler; a probe in that override never fires because it is never called.

## What the fix does

The repair moves the emission after the turn's snapshot, where the bots are present — and the snapshot still holds the dead bots, so a bot receives its own death. It is committed upstream on the branch `fix-death-events-never-reach-bots` in the Tank Royale repository, with a positive and a negative test in the server's own `TurnProcessorTest`. The positive test fails without the change and passes with it; the server suite passes in full.

## The controlled comparison

Three server builds, six battles each, against nine classic battles. Every Tank Royale build was rebuilt from clean and identified by the runner jar's checksum, because Gradle's up-to-date check silently reused a stale server jar and made the first comparison meaningless.

| Build | wins per battle | deaths per battle |
|---|---|---|
| The runner jar the bridge ships against | 4, 5, 5 | 0 every battle |
| Current Tank Royale `main` | 5, 5, 5, 6, 7, 7 | 0 every battle |
| Current `main` + the fix | 4, 4, 5, 5, 6, 7 | equal to wins, every battle |
| Classic Robocode (reference) | 5, 5, 5, 5, 5, 4, and 5, 5, 4 | 5, 7, 6, 5, 5, 6, and 6, 6, 6 |

Deaths appear only with the fix. Wins are unchanged by it: totals above five occur on the unfixed build too, so the fix does not touch round outcomes.

## A second divergence, separate from this one

Classic never reported more wins than rounds in any battle measured; Tank Royale reported seven wins in a five-round battle, on both the fixed and the unfixed build. A robot can win a round only once, so a total above the round count means both robots were declared winners of the same round — consistent with a mutual kill in which classic declares one winner and Tank Royale declares two. With the fix, the bridge's win and death totals are equal in every battle, while classic's deaths exceed its wins.

This is pre-existing, unrelated to the death delivery, and invisible before it: with no deaths arriving there was nothing to pair the wins against. It needs its own analysis record.

## What did not reproduce

`AN-006` recorded a second divergence alongside the death finding: classic split `BattleWin`'s five rounds between the two instances while the bridge gave one instance all five. It does not reproduce — wins split across the instances in every battle measured here. The record notes it was measured before the event-dispatch redesign and the Bot API upgrade, so it is not carried forward as a live finding.

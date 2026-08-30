---
id: CH-003-findings
type: findings
status: open
links: [CH-003, AN-006]
title: CH-003 measurement log — where the death event actually stops
---

# CH-003 — what the measurement established

Measured 2026-08-30 against the Tank Royale runner jar at `C:\Code\tank-royale\runner\examples\lib\robocode-tankroyale-runner.jar` and Bot API 1.0.2, using `tested.robots.BattleWin` through the conformance harness.

## The result

**No death message of any kind reaches any bot.** Not the dying bot, not the survivors.

The probe sat in `WebSocketHandler.onText`, before any parsing, printing the raw payload of every message whose text contained `death` in any casing. Across a two-participant battle and a four-participant battle, over two rounds each, that probe fired zero times in every bot's log — while the same logs show `ScannedBotEvent`, `HitWallEvent`, `HitByBulletEvent`, `BulletHitBotEvent`, and `WonRoundEvent` arriving and dispatching normally.

The four-participant run matters: there, three bots die while the round continues, so the survivors should receive `BotDeathEvent` for each. None did.

## What this refutes

Two hypotheses, both now dead.

**`AN-006`'s suspect — the Bot API event queue's age and criticality filter — is refuted twice over.** By reading: `DeathEvent.isCritical()` returns true, and both `isOldAndNonCriticalEvent` and `isNotOldOrIsCriticalEvent` exempt critical events from the age filter. By measurement: the queue probe printed the queue's full contents at every dispatch, and no `DeathEvent` was ever in it. Nothing was dropped, because nothing arrived.

**The instant-handler hypothesis this change was opened on is also refuted.** `BotInternals` does subscribe an instant handler to `DeathEvent` whose body is `baseBotInternals.stopThread()`, and the bridge's bot does inherit it, so the ordering concern was real in principle. It is not what happens: the probe on that handler never fired either, for the same reason.

## Where the cause is

Above the Bot API entirely, at or below the server-to-bot protocol boundary. The bridge is correct: it overrides the Bot API's `onDeath`, maps it, and calls the robot's handler, and a probe placed directly in that override never fires because it is never called.

A lead for the upstream investigation, not a conclusion — the runner jar in use and the server source read here are not known to be the same build. In `TurnProcessor.applyDefeatedBots`, the death event is added to the turn with `addPublicBotEvent`, which fans out over `turn.bots`; `TurnToTickEventForBotMapper.map` returns null for a bot no longer in the turn, and `GameServer.sendTickToParticipants` skips a participant that is neither alive nor carrying events. Whether the death event is added before or after that turn's ticks are built and sent is the question to answer upstream, and `MutableTurn.resetEvents()` clears the map between turns.

## The consequence nobody had written down

`EVT-004` is not the only criterion this blocks. `EVT-007` — the death of another robot reaches the survivors — is unprovable for the same reason and by the same measurement, and it was recorded as merely unported. Any classic robot that tracks enemies through `onRobotDeath` is running blind under the bridge, and that is a much wider fidelity gap than one handler on one robot.

## What did not reproduce

`AN-006` recorded a second divergence alongside the death finding: classic split `BattleWin`'s five rounds between the two instances while the bridge gave one instance all five. It does not reproduce. Across the runs made here the wins split 2/3 and 3/2, as classic's do. The record notes it was measured before the event-dispatch redesign and the Bot API upgrade; nothing here re-establishes it, so it is not carried forward as a live finding.

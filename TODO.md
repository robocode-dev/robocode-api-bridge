# TODO

Remaining tasks for the Robocode → Tank Royale bridge, in priority order.
Status as of 2026-07-04, based on compat-test results (see `compat-test/compatibility_report.md`).

## DONE: Event-dispatch redesign (2026-07-04, uncommitted)

Events now flow through the Bot API `EventQueue` (priorities, interruptible events,
`ThreadInterruptedException`): the Bot API handler methods (`onScannedBot`, `onTick`, etc.)
are overridden in `BotPeer.BotImpl` and delegate to the existing mapping helpers; the manual
`dispatchBotEvents()`/switch and the `dispatchedEvents`/`dispatchingEvents` workaround are
removed. `setInterruptible()` is NPE-guarded. Own-death now reaches `onDeath()` (previously
never dispatched — no `DeathEvent` case existed in the old switch).

Along the way: **the bridge was upgraded from bot-api 0.33.1 to 1.0.2** (mavenLocal; publish
with `gradlew :bot-api:java:publishToMavenLocal` in the tank-royale repo). 0.33.1's event queue
POPPED-AND-DROPPED deferred same-priority events — bots calling a blocking method (e.g.
`fire()`) inside `onScannedRobot` lost every other scan event, radar locks broke, and mutually
blind bots stalled rounds. Fixed in current TR (peek, remove-on-dispatch only). 1.0.2 also
dispatches new-turn events at the end of `execute()` (classic timing), so the bridge needs no
`go()` hook for dispatch anymore.

Results: acid.Syzygy RC=1,784 vs TR=1,454 (−18.5%) → PASS (was TR=0/81, blind-stalemate
regression — root cause was the 0.33.1 queue bug, not `prepareRobotForRound()`).
ad.last.Bottom: the +621.9% inversion is gone; scan-interrupt semantics now work. Its
mirror-match scores are tiny and wildly noisy on the classic side too (RC swung 6 ↔ 274
between runs), so single-battle percentage deltas are meaningless for this bot — needs
multi-run averaging before drawing conclusions.

## 1. Commit pending bridge changes

Uncommitted: the event-dispatch redesign + bot-api 1.0.2 upgrade (BotPeer.java, both
build.gradle.kts files, compat_test.py/README defaults), and `BotPeer.getDataDirectory()`
now returning `RobotData.getDataDirectory()` so it matches the directory `getDataFile()`
resolves against (like classic Robocode; not yet re-tested against dz.Gir, the bot that
surfaced the inconsistency).

## 2. Score-gap bots (TR lower, no errors)

CodaFirst −43.4%, Bl4ck −85.6%, Aetos −37.5% score much lower under TR with no errors logged.
RE-TEST FIRST: these numbers predate the event-dispatch redesign and the 1.0.2 upgrade — the
0.33.1 dropped-scan-event bug plausibly explains much of this bucket. Then behavioral
comparison (movement/targeting traces) for whatever remains.

## 3. Score-gap bots (TR higher)

ScalarR +37.9%, Ar1 +64.2%, BasicSurfer +79.2% score much higher under TR (numbers also
predate the redesign/upgrade — re-test first). Bottom's +621.9% inversion is fixed.

## 4. File I/O sandboxing (RobocodeFileOutputStream emulation)

Classic Robocode sandboxes ALL robot file I/O into the robot's data directory; the bridge does
not. dz.Gir writes root paths like `\1_gundata.dat` → ~10,000 `IOException: Adgang nægtet`
errors under TR. Requires rewriting/redirecting paths in the `RobocodeFileOutputStream`/
`RobocodeFileWriter` wrappers like the classic engine does.

## 5. Team support in the robots wrapper

Team robots (droids, `TeamRobot`, `.team` files) are not yet supported by the wrapper/harness.

## 6. Full compat sweep

After #1 lands: bigger batch (re-run all previously flagged bots — their results predate the
redesign/upgrade), then a full roborumble-collection sweep with `--rounds 35`, one harness
instance at a time.

## Tank Royale side (done, pending release as 1.0.3)

Committed in tank-royale `4e579878f`: null-guard in `EventQueue.setCurrentEventInterruptible`
(Java/.NET/Python), protocol version compatibility check in all four Bot APIs, TypeScript
version stamped from `/VERSION` via `syncVersion`. Runner fat jar
(`runner/examples/lib/robocode-tankroyale-runner.jar`) refreshed to embed server 1.0.2.

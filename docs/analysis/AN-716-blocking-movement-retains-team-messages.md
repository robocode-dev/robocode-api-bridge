---
id: AN-716
type: analysis
status: active
links: [AN-714, AN-715, CAP-006, TEAM-002, TEAM-003, IDR-008, PDR-001, C-007, P-002, M-149]
title: Blocking movement retains team messages and leaves MyFirstTeam unarmed
provenance: inferred
reversal-cost: low
reality: contradicted
carried-by: [DES-006]
---

# Blocking movement retains team messages

## Finding and consumer

MyFirstTeam's intermittent zero scores expose a bridge delivery defect: the leaders queue team messages but their blocking `ahead()`/`back()` loop never flushes them. The droids receive no orders and never fire. Some battles still produce nonzero scores through collisions and survival, so a completed battle with ranked results does not establish that this team operates correctly. This finding serves the [preview installation and compatibility assessment](../plans/P-002-build-distribution-and-bot-acquisition.md), whose readiness milestone remains open.

The [team-message delivery and droid behavior criteria](../capabilities/CAP-006-team-robot-support/criteria.md) already require this behavior. Their current fixtures send messages followed by explicit `execute()` calls, which exercise the working path and miss blocking movement. This incident records a contradiction of that broader delivery claim without changing its meaning. The [three-tier evidence strategy](../decisions/PDR-001-three-tier-evidence-strategy.md) and [serializable message envelope](../decisions/IDR-008-preserve-serializable-team-messages.md) remain applicable; their existing evidence did not cover this trigger.

## Conditions and retained evidence

This was a **prepared environment**, not a clean installation walkthrough: Windows, PowerShell, Eclipse Temurin JDK 25.0.1+8, Python 3.14 and websockets 15.0.1. The bridge package was built from revision `0f53ea6bb74e` with `:robots-wrapper:previewDist`; its ZIP SHA-256 is `0e1fe28c6d006b47aa41a2d09fc3fbd20f6ab7e4cab1158022e0b780be7186b8`. The publicly downloaded [Tank Royale 1.4.0 runner](https://github.com/robocode-dev/tank-royale/releases/download/v1.4.0/robocode-tankroyale-runner-1.4.0.jar) matched its published `SHA256SUMS` and the previous investigations: `02f6d1d8e9346a4aeae1b91e7abb278c98ac1f1d30c605f867758566f5f327cf`.

The sample jar was read from `C:/Code/LiteRumble robots/teamrumble/sampleteam.MyFirstTeam_1.0.jar`, copied into disposable staging and imported by the packaged command. Both the collection original and staged input have SHA-256 `53c7888f54b626fec479a4f692fa946245c0c1720206590910f8a3e44569466f` after the runs. The [read-only collection rule](../constraints/C-007-rumble-jars-are-read-only.md) was preserved. Two separate generated five-member entries ran for one round in an 800×600 arena, with ten allowed participants and the classic preset's 30,000 µs turn timeout and 450-turn inactivity setting. Starting positions were not fixed.

A local WebSocket relay forwarded messages unchanged and captured each member's intents, inbound events and round/game result rows; it excluded handshakes. The runner separately recorded complete observer events. [The structured observations](AN-716-blocking-team-message-observations.json) contain per-bot identities, action counts, first/last state, queue samples and result rows. [The diagnostic evidence archive](AN-716-team-message-diagnostic-evidence.zip) contains the original captures, runner/member logs, observer recordings, probe scripts, the temporary bridge instrumentation patch and reproduction instructions. Its SHA-256 is `72886da17b3cc2a87003a9461513f85aecd4efa26916b9b04c93f8244420b9b2`.

## Comparison

Four fixed baseline trials used the unmodified packaged bridge. A fifth completed battle added queue logging only. Two further trials used that same diagnostic class with queue flushing enabled at entry to `move()`. These are individual diagnostic observations from one sample team, not a random population sample, a failure-frequency estimate or a Classic parity comparison. The relay, recording and instrumentation can affect timing; no score percentage is inferred.

| Run | Bridge variant | Team scores | Observer bullet-fired events | Intents carrying team messages |
|---|---|---|---|---|
| 1 | Packaged bridge | 0 / 0 | 0 | 0 |
| 2 | Packaged bridge | 0 / 0 | 0 | 0 |
| 3 | Packaged bridge | 1 / 1 | 0 | 0 |
| 4 | Packaged bridge | 254 / 204 | 0 | 0 |
| 6 | Queue logging only | 250 / 200 | 0 | 0 |
| 7 | Flush before movement | 1768 / 1183 | 120 | 52 |
| 8 | Flush before movement | 1598 / 1336 | 144 | 83 |

Run 5 was a failed diagnostic setup: attempting to subclass the final `BotPeer` prevented the two leaders from compiling, and the runner timed out with eight of ten members connected. Its logs are retained but it is excluded from the seven completed battles. The replacement diagnostic compiled a disposable copy of `BotPeer` and put those classes ahead of the packaged libraries in generated scripts; it changed no source robot jar or production bridge source.

In zero-score runs 1 and 2, **all ten per-bot round and game result rows also contain zero points**. Observer round and game results agree. There is no evidence of nonzero points being lost during team aggregation or runner conversion. Both leaders send movement and radar intents and receive enemy scans; the eight droids remain stationary. No bot sends a team-message intent or fires. Both zero-score rounds end at turn 1451 after energy declines, with no observer death events. The scoring and end-of-round details are retained as observations rather than a new verdict on Tank Royale's inactivity rules.

Run 3's only points are one ram point per team. Run 4's points are survival plus ram damage, and run 6's are survival only. None has bullet damage or transmitted team messages. Thus the earlier apparent intermittency of score zero can coexist with the same persistent absence of team orders.

## Mechanism and intervention

At the pinned bridge revision, `BotPeer.broadcastMessage()` appends to `pendingTeamMessages`. `executeRobotTurn()` calls `flushTeamMessages()` before `bot.go()`, whereas `move()` directly calls `bot.forward(distance)`. The sample leader's bundled source broadcasts its color once, broadcasts target positions from `onScannedRobot()`, and loops over `ahead(100)` and `back(100)` without calling `execute()`. The sample droids fire only after receiving a target-position message.

In logging-only run 6, one leader has 992 pending messages at robot time 1451; the other has 1033 at time 1441. Enemy scans are present, so lack of targets is not the explanation. No serialized packet reaches the relay, placing the failure before JSON transport or receiver deserialization.

The diagnostic intervention adds only logging and an optional `flushTeamMessages()` immediately before `bot.forward(distance)`. With flushing enabled, both runs transmit team messages, droids fire, and both teams acquire substantial bullet-damage points: 791/703 in run 7 and 786/751 in run 8. This supports the retained-queue mechanism. It is not a production repair: entry-only flushing can still delay messages generated inside a blocking call and does not cover other blocking methods or exact Classic turn timing.

## Consequence for the next work

Implement and verify a turn-boundary repair under the existing delivery criteria. A focused two-engine fixture should broadcast before a blocking movement call and from an event handler while that call remains active, with no explicit `execute()` in the sender's loop; it should assert message receipt and timing rather than an exact nondeterministic score. Inspect blocking turns and other paths that advance Tank Royale turns for the same omission, and preserve the current message ordering, per-turn bounds and recipient rules.

The investigation rejects runner conversion, team-result aggregation and absence of enemy scans as explanations for these captured unarmed battles. It does not establish that every historical zero-score battle had this cause, resolve the separate zero-only ranking behavior, or complete the clean Windows walkthrough required before the preview can be declared ready.

## Repair verification

The subsequent repair moves the existing queue flush from `executeRobotTurn()` to `BotImpl.go()`, which Tank Royale also invokes for every internal turn of blocking calls. The transport helper still owns batching, size splitting, ordering and directed recipients. Removing the old flush avoids draining the queue twice on explicit `execute()` turns. No reproduced `robocode.*` signature changes.

`BlockingTeamMessageConformanceTest.testTEAM002_IntegrationPositive_DeliversAtTheNextTurnDuringBlockingCalls` runs the same two-member team on both engines for five rounds. It checks delivery time and event time equal to send time plus one, sender identity, order and duplicate/missing messages across movement, body/gun/radar turns, waits and explicit execution. It also sends a broadcast and a directed message from a status handler while movement is active. A blocking radar revolution before the measured sequence lets all members subscribe, as in the existing team fixture; this test does not claim to resolve first-turn startup races.

Before the repair, Classic met those timing expectations, while the bridge retained messages sent at times 0 and 5 until time 58 in one captured round. After moving the boundary and stabilizing the fixture's startup, the five-round two-engine test passes without skips. The seven existing team-conformance tests also pass against the published 1.4.0 Bot API/runner pair, and all adapter unit tests pass, including a new ordered-overflow test at the logical message bound. The evidence export now fingerprints the dynamically compiled team/probe Java sources and team descriptors as well as the JUnit tests.

[Two prepared sample-team smoke runs](AN-716-team-message-repair-smoke.json) use the rebuilt production adapter and no diagnostic override classes. They record 109 and 102 observer bullet-fired events respectively, with nonzero bullet-damage scores on both teams in each run. The report pins the adapter source and jar hashes, and confirms the unchanged collection/staged sample jar hashes. These runs confirm that the original sample receives orders and fires after this repair; their scores are not a parity or frequency claim. Preview installation readiness remains separate.

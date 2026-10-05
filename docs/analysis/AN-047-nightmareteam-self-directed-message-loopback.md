---
id: AN-047
type: analysis
status: active
links: [P-001, CAP-001, CAP-003, CAP-005, CAP-006, TEAM-002, AN-046]
title: NightmareTeam needs direct messages to its own robot looped back
provenance: inferred
reversal-cost: low
---

# AN-047 — NightmareTeam needs direct messages to its own robot looped back

## Question

Does the current `teamrumble/ustimaw.NightmareTeam_3.3.jar` failure come from a misspelled teammate name, a Tank Royale API limit, or a bridge defect relative to Classic Robocode?

## Evidence boundary

The read-only team jar has SHA-256 `290ca8f9909a345a4830b51e25815d426b37cf56bc08266e31c920708ffee8f0`. Its descriptor contains four `ustimaw.Nightmare` members and one `ustimaw.NigitmareDroid` member; the team jar was not rewritten.

The pre-fix official observation `d03645e8acdade91` completed on 2026-10-05 with Classic Robocode 1.11.1, bridge base commit `aba3837b05c17cc60de9a91019d3fc10b3265900`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API 1.4.0, Runner 1.4.0, and wrapper 0.3.1. It used the official teamrumble setup: two teams of five, 1200×1200 arena, and 10 rounds. Classic scored 27,858 with no errors. Tank Royale produced no score and three recorded errors, including `sendMessage: Cannot find receiver of team message: ustimaw.NigitmareDroid 3.3 (1)` and `(2)`; its registry result was `DISCREPANCY (outcome)`.

Read-only bytecode inspection shows `ustimaw.NigitmareDroid.run` broadcasting a `MessageType.DROID` message and then sending that message directly to `getName()`. The error names are this sender's own runtime name with each battle suffix, so the target is not a typo or absent teammate. Its `Nightmare.onMessageReceived` callback adds the sender to the droid set for that message type.

At Classic commit `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`, `RobotPeer` adds itself to its `TeamPeer`, and `performScan` delivers directed messages to every matching team member. `checkDispatchToMember` excludes self only for broadcasts (`recipient == null`); a direct recipient matching the robot's own name is accepted and queued ([pinned Classic RobotPeer source](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/peer/RobotPeer.java#L159-L161), [message delivery path](https://github.com/robo-code/robocode/blob/9ea397b08fe0e3c9010b96c7f19bd65ef5e84976/robocode.battle/src/main/java/net/sf/robocode/battle/peer/RobotPeer.java#L953-L1003)). Tank Royale's Java Bot API validates `sendTeamMessage` recipients against teammate IDs, which do not include the sending bot. The bridge therefore cannot forward a self-directed message through the ordinary team-message transport.

## Finding

This is a bridge defect against the existing `TEAM-002` requirement that messages arrive as Classic delivers them. Classic accepts a direct message addressed to the sender itself; the bridge previously looked up only Tank Royale teammate IDs and rejected it. Tag the cause `classic-team-self-message-not-looped-back`, owner `bridge`.

Commit `19a8ecc2e683757d0a861bca26905c992925485e` adds a local self-message queue in `BotPeer`. It retains the message for the next turn, delivers it at Classic's priority 75 before the first equal-or-lower-priority callback, and drains it after event processing when no such callback occurs. The active synthetic `MessageEvent` is visible through the event-list API during its callback, and pending local messages are cleared between rounds. `CHANGELOG.md` records the bugfix.

The post-fix official five-pair confirmation is observation `1be38207150c4149`, completed 2026-10-05 against bridge commit `19a8ecc2e683757d0a861bca26905c992925485e`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API 1.4.0, Runner 1.4.0, and wrapper 0.3.1. Classic mean score was 28,308.4 and Tank Royale mean score was 24,780.4; the harness's five-pair mean delta was −12.44% against a 25% threshold. All five pairs completed without errors, the confirmation had no bridge-only error signatures, and the registry now reports `MATCHED (score noise)`. The bridge API jar SHA-256 was `7b9694ef4dc67d4be47e36be69a5bba55f7c9006cc43ce27afa1747857afbab3`.

## Route

Recommended route: simple. This restores the existing `TEAM-002` behavior from Classic Robocode without changing an acceptance criterion, capability, or policy.

## M-006 handoff

Keep the pre-fix outcome discrepancy and the post-fix five-pair confirmation in registry history. Continue in teamrumble registry order with `vuen.Bakery_2.51.jar`.

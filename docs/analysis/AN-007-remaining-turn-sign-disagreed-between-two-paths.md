---
id: AN-007
type: analysis
status: active
links: [CAP-003, CAP-002, ARCH-002, AN-005]
title: Remaining turn had opposite signs depending on which path a robot read it through
provenance: inferred
reversal-cost: low
---

# AN-007 — Remaining turn had opposite signs depending on which path a robot read it through

## What was investigated

The first call-routing test written for `CH-002` asserted that a Tank Royale remaining turn of 30 degrees reaches a robot as 30 degrees in radians. It failed: the peer returned the negation.

## What was found

Two paths carry the same quantity to a robot, and they disagreed.

| Path | What a robot calls | Conversion |
|---|---|---|
| `BotPeer.getBodyTurnRemaining()` | `AdvancedRobot.getTurnRemainingRadians()` | `-toRadians(...)` |
| `IBotToRobotStatusMapper` | `RobotStatus.getTurnRemainingRadians()`, from `onStatus` | `toRadians(...)` |

The same for the gun and radar remainders. A robot asking directly got the opposite sign from a robot reading its status event.

**The peer was right.** Tank Royale's own documentation for `getTurnRemaining` is explicit: *"When the turn remaining is positive, the bot is turning to the left. When the turn remaining is negative, the bot is turning to the right."* Robocode's convention is the reverse — positive is a right turn. A remainder is a signed rotation, so crossing between the frames flips it.

That is also why the command path is *not* symmetric with the read path, which is what makes this easy to get wrong. `turnBody(radians)` routes to `bot.turnRight(degrees)` with no negation, and correctly so: a command whose name carries its direction needs no sign flip. Only the reported remainder uses a signed convention, and only it flips.

## What made it survive

`AN-005` corrected a positional swap in this same mapper and closed with a warning that the same class of error could exist anywhere a mapper fills a wide constructor. This is that, one field-group over, and a sign rather than a position.

Worse, the test written alongside `AN-005` asserted the un-negated values. It was written to prove the mapper preserved a negative remainder's sign, and it did prove that — while the value it preserved was already inverted relative to what a robot elsewhere in the same adapter would see. **The test certified the defect.**

The lesson is narrow and worth stating: a test written from the implementation's behaviour confirms the implementation. Both of these tests were written by reading what the code did and asserting it. The one that caught this was written from the *other* path's behaviour and found the disagreement, which is a different act.

## Why the unit tier caught it and nothing else could

Both paths are pure conversions with no engine involved, so the disagreement is visible in microseconds once anything looks at both. Nothing did.

No battle could have shown it. A robot using status-based remaining turn for a gun or radar control loop steers the wrong way, scores slightly worse, and produces no error — the profile of a bot in the score-gap milestones. A robot using the direct getter behaves correctly, so the two never contradict each other visibly in one place.

## What was done

The status mapper now negates all three remainders, matching the peer and the documented conventions. The test that asserted otherwise is corrected, with a note saying what it used to certify.

Fixed here rather than deferred because it is an unambiguous routing error with a definite correct answer, which `CH-002`'s proposal admits as a defect correction restoring an unchanged criterion. `AN-006` remains deferred by the same rule, since its cause is not established.

## What this does not establish

That the two paths agree everywhere else. This change tests the peer's routing method by method; the status mapper has its own tests, and nothing yet asserts that the two agree *with each other* field by field. That cross-path check is the obvious next thing and is not in this change.

Nor does it establish which robots were affected. Any robot reading remaining-turn values from its status event was steering on an inverted quantity; no flagged bot has been traced to it.

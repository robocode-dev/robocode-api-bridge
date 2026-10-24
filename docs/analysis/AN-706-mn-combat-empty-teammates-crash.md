---
id: AN-706
type: analysis
status: active
links: [P-001, PDR-001, C-007]
title: mn.Combat's solo TeamRobot crash came from an empty teammate array
provenance: inferred
reversal-cost: low
---

# AN-706 — mn.Combat's solo TeamRobot crash came from an empty teammate array

## Risk investigated

Whether the Tank Royale-only failure of `roborumble/mn.Combat_3.25.0.jar` was caused by its own unchecked array access or by the bridge violating Classic's no-team result from `getTeammates()`.

## Evidence boundary

The read-only robot jar SHA-256 is `48ee3e5181f21644e2a2f04c75d8359d189af0f519d8aef065398f60dce443db`. Its pre-fix official observation completed at `2026-10-22T17:24:47Z` on bridge commit `cfbba5d1da6951afb64983c16d1d6c111b00ccb0`; the bridge API jar hash was `2f297823d320a3fe4046b48e4ee73976357abb46c4cdd3ceb14ee66e01e6414c`. The post-fix retest completed at `2026-10-22T17:49:17Z` from main commit `97aa0cb8135d7ec517393e5ed9ebec86b3135df2` with the fix in the working tree; its bridge API jar hash was `f0a25c61534ee55b0deb657c7e37b801783bb379de3f3a261efae8aa461da32f`. Both observations used Classic Robocode 1.11.1 and official RoboRumble parameters: two participants, 35 rounds, and an 800×600 arena. The runner jar hash (`4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`), Bot API 1.4.0 jar hash (`ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`), and wrapper jar hash (`5cd3fe473ef46410d177243507f581ad00cc26fcd16c4a2e8e58fbed7e0ae9a3`) were the same for both observations. The read-only jar was copied for the retest and its SHA-256 was checked against the staged source.

## What was tried

The first observation scored 4,316 in Classic with no errors. Tank Royale produced no score and five `ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0` errors, with the bridge-only signature originating at `mn.b.b.a`. Read-only bytecode inspection showed that `mn.Combat` is a `TeamRobot`; its helper obtains `getTeammates()`, sorts the returned array, and reads element zero without checking for an empty array.

Classic's `RobotStatics` sets its teammate array to `null` when the robot has no team, and `getTeammates()` preserves that `null`. Tank Royale represents a bot with no teammates as an empty ID set. Before the fix, `BotPeer.getTeammates()` converted every non-null set to an array, so the empty set became `new String[0]` and violated the Classic API contract.

After changing the bridge to return `null` for a null or empty teammate ID set, the official retest scored 4,283 in Classic and 4,183 in Tank Royale, with zero errors on both sides and a −2.3% delta. The parity result was `PASS`. The unit test also covers an empty teammate ID set. No rumble jar was changed.

## Finding

The Tank Royale crash was triggered by the robot's unchecked `teammates[0]` access, but the bridge caused it by returning an empty array where Classic returns `null` for a solo, non-team `TeamRobot`. The direct bridge correction restored the existing API behavior, and the official retest removed the asymmetric crash.

The evidence covers a solo RoboRumble battle. It does not separately exercise a one-member team configuration.

## Implications for M-006

Record the post-fix `PASS` for `mn.Combat` as a focused retest of the repaired solo-team API behavior. Other TeamRobot cases remain independent until their official observations are retested.

## M-006 handoff

Continue with the next unresolved case in the selected RoboRumble/TeamRumble queue. Confirm score-only gaps with repeated official battles, and attribute error-only cases from their robot and bridge stack frames before proposing a bridge change.

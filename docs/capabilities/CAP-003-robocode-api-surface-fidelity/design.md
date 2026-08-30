---
id: DES-003
type: design
status: active
links: [CAP-003, ARCH-002, PDR-001]
title: Robocode API surface fidelity — design
provenance: inferred
reversal-cost: low
---

# CAP-003 — design

## Shape

The translation layer is a set of small, mostly stateless mappers in `dev.robocode.tankroyale.bridge`, each owning one conversion: angles, colours, bullets, results, bot state to robot status, and one mapper per event type behind a dispatching class mapper.

They are deliberately plain. A mapper takes values and returns values, holds no reference to a battle or a peer where it can avoid one, and does no I/O. That is what makes the unit tier possible, and the property is worth protecting: a mapper that acquires a dependency on live battle state stops being testable without an engine and quietly moves its criteria from `active` to something that needs a battle to check.

## The angle rule: a heading and a rotation are not the same conversion

The engines disagree about angles twice over, and the two disagreements compose differently depending on what is being converted. Getting this wrong has now caused a defect, so it is written down rather than left to be re-derived each time.

Tank Royale is mathematical: origin bottom-left, zero degrees east, angles increasing **anti-clockwise**. Robocode puts zero at north and increases **clockwise**.

That gives two distinct transforms:

| Kind of angle | What it is | Transform | Where |
|---|---|---|---|
| Absolute heading | A direction in a frame — body, gun, radar, bullet heading | `normalize(90 − d)`: reflect the handedness *and* move the zero | `AngleConverter.toRobocodeHeadingRad` |
| Signed rotation | A relative angle, or an amount still to turn — bearings, turn remaining | `−d`: reflect the handedness only | `AngleConverter.toRobocodeBearingRad` |

A signed rotation has no origin, only a handedness, so it takes the reflection alone. Applying the heading transform to it would move a zero that was never there; applying no transform at all — converting the unit and leaving the sign — silently inverts every direction.

**Commands are a third case and need neither.** `turnBody()` routes to `bot.turnRight()`, and a command whose method name carries its own direction has no sign to flip. So the outbound and inbound paths are genuinely asymmetric: the same quantity flips coming in from the engine and does not flip going out to it.

That asymmetry is the trap, and `AN-007` is what it cost. The status mapper converted the unit and left the handedness, so a robot reading its remaining turn from `onStatus` saw the opposite direction from one asking the peer — in the same battle, on the same turn. Measured on both engines: after a commanded right turn, classic reports `+80` on both paths, and the bridge reported `−80` on one of them.

## Two paths, one answer

A robot can read its own state through the peer's getters or through the `RobotStatus` handed to it in `onStatus`. Those are separate code paths over the same Bot API state, and they must agree.

Both had unit tests before `AN-007`, both passed, and neither looked at the other — which is precisely how they came to disagree. A test written from an implementation's behaviour confirms that implementation; the guard that matters is therefore not a better test of either path but a test that the two agree. That guard now exists and compares signed values field by field, with a distinct value per field so that agreement by coincidence fails too.

## Why the conversions are the natural test seam

`ARCH-002` divides the module into a frozen `robocode.*` surface and a free implementation behind it. The mappers are the narrowest point of that implementation — everything a robot observes passes through one — so testing them covers a large surface with small tests.

It also localises. A failure in `CAP-002` or `CAP-005` says a robot behaved differently; a failure here says which conversion is wrong. Given that the current instrument reports score deltas minutes or hours after the fact, having any layer that fails with a precise cause is a substantial change in how this repository can be debugged.

## Evidence, and what the criteria are written to catch

Each criterion carries positive and negative directions, and the negative half is where the real risk sits. A conversion is usually right in the middle of its range and wrong at an edge: the angle where the circle wraps, the colour that is absent rather than black, the participant with no score, the bullet whose owner has left the battle, the Tank Royale event that has no Robocode counterpart.

Those edges are exactly the cases a whole-battle test reaches rarely and reports vaguely. They are also the cases a mapper's author is least likely to have considered, which is why the criteria name them rather than leaving direction as an unwritten obligation.

Evidence attaches through the test method name, carrying the criterion identity, the proof type, and the direction together on the executable that proves it.

## The limit of this capability

Correct conversions do not imply a correct surface. `ARCH-002` records that parts of the reproduced `robocode.*` API have never been exercised by any tested robot and are therefore unverified. A mapper can be provably correct while the API surface above it is missing a method some robot in the collection calls, and nothing here would notice.

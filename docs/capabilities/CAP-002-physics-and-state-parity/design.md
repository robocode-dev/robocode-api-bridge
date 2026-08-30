---
id: DES-002
type: design
status: active
links: [CAP-002, ARCH-001, ARCH-002]
title: Robot physics and state parity — design
provenance: inferred
reversal-cost: low
---

# CAP-002 — design

## Where the physics actually lives

Not here. The Tank Royale server simulates the battle, and it implements Robocode's rules directly. The bridge does not compute acceleration, turn rates, or gun heat, and it must not: a second implementation of the rules would be a second thing that can be wrong, and the two would drift.

What the bridge does is narrower and easier to get wrong than it looks. It receives the server's view of the robot's state each turn, converts it into the values the classic API exposes, and passes the robot's control calls back down. Physics parity is therefore mostly a claim about **conversion and control routing**, not about simulation.

## The two places a difference can enter

**Unit and frame conventions.** Robocode and Tank Royale do not agree on how angles are expressed. Bearings, headings, and turn directions all pass through conversion on the way in and on the way out, and a sign error or an off-by-one-quadrant mistake produces a robot that turns the wrong way in a specific circumstance rather than one that is obviously broken. `CAP-003` holds the criteria for the conversions themselves; this capability is the claim that a robot driven through them moves as it did on classic.

**Control call semantics.** Classic distinguishes blocking calls from their `set`-prefixed queued counterparts, and the queued form takes effect at the next `execute()`. The adapter must preserve that distinction exactly, because a robot's whole turn structure is built on it. `RateControlRobot` is the same question in a different shape: rates rather than distances, resolved against the same turn boundary.

## Why the criteria compare rather than assert

The criteria state that the two engines agree, not that a turn rate equals a particular number. Writing the number here would create a third copy of a constant that already exists in classic Robocode and in the Tank Royale server, and the copy in a markdown file is the one that would silently fall out of date. Comparison also catches the case that matters most — the two engines disagreeing — without needing to know which is right, since `G-001` already settles that.

## What this design cannot check, and why that is recorded elsewhere

Position over time. Comparing exact positions after many turns would be the strongest possible physics evidence, and classic supports it through a seeded battle. Tank Royale exposes no seed, so a long battle diverges for reasons that have nothing to do with the bridge. The criteria therefore compare short, forced sequences — accelerate from rest, turn at a known velocity, fire and watch the bullet — where the robot's own reports are deterministic enough to compare directly. `AN-002` records the underlying gap and what closing it would buy.

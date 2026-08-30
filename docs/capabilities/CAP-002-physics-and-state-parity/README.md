---
id: CAP-002
type: capability
status: draft
links: [G-001, ARCH-001]
goal: G-001
title: Robot physics and state parity
provenance: inferred
reversal-cost: low
---

# CAP-002 — Robot physics and state parity

Robocode's rules are arithmetic: a robot accelerates at a fixed rate, decelerates faster than it accelerates, turns more slowly the faster it moves, and cools its gun at a fixed rate per turn. This capability is the promise that those numbers are the same under the bridge as under classic Robocode.

## Why it exists as its own capability

The physics is the substrate every robot's strategy is built on, and small errors in it are both invisible and decisive.

A targeting algorithm predicts where an opponent will be by applying the same movement rules the engine applies. If the engine's turn rate differs from the one the robot assumes, the robot's aim degrades in a way that produces no error and no exception — just a slightly worse robot. Multiply by a whole collection and the result is a compatibility report full of unexplained score gaps.

That is the current situation. The bots flagged as scoring materially differently under the bridge produce no errors on either side, which means the divergence is behavioural, and physics is the largest candidate.

## What it covers

The movement and turret rules a robot can observe: acceleration and deceleration including the deceleration corner cases classic treats specially, maximum velocity, the velocity-dependent body turn rate, gun and radar turn rates together with the independence settings that decouple them, direction reversal, the `RateControlRobot` variants of the same controls, and gun heat with its cooling rate. It also covers the bullet state a robot can read back after firing.

## What it does not cover

When the robot is told about any of it. Event delivery and timing belong to `CAP-001`, and the conversion of a Tank Royale value into the Robocode value a robot reads belongs to `CAP-003`.

Exact positions after a given number of turns are deliberately outside this capability. Classic can produce them reproducibly through a seeded battle; Tank Royale cannot, so no criterion asserts them. `AN-002` records the gap.

## Status

`draft`. None of this has ever been checked. The bridge's physics has been exercised only through whole battles whose outcome is a score, and a score cannot distinguish a turn-rate error from a targeting difference. `M-001` is the plan door.

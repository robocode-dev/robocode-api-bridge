---
id: CAP-001
type: capability
status: draft
links: [G-001, ARCH-001, C-002]
goal: G-001
title: Event dispatch and timing parity
provenance: inferred
reversal-cost: low
---

# CAP-001 — Event dispatch and timing parity

A robot experiences a Robocode battle as a stream of events: it was scanned, it was hit, its bullet landed, a turn ended. Almost everything a robot does is a reaction to one. This capability is the promise that the bridge delivers those events the way classic Robocode delivers them — the same events, in the same order, at the same moments, interruptible in the same places.

## Why it exists as its own capability

Event delivery is where this bridge has already been most badly wrong, and the failure was invisible in exactly the way that matters.

An earlier Bot API version popped deferred same-priority events off its queue and discarded them. A robot that called a blocking method inside `onScannedRobot` — which is to say, a large fraction of all robots ever written — lost every other scan event. Radar locks failed to hold. Two robots that could not see each other stalled the round. The scores that came out were plausible numbers describing a game nobody was playing.

Nothing in the score-based instrument could name that. It reported that some bots scored differently, which is true of bots that score differently for any reason at all.

## What it covers

Delivery of the whole event vocabulary — scans, hits, bullet outcomes, robot deaths including the robot's own, custom events, skipped turns, exceptions thrown out of a handler — and the two properties that are easy to get subtly wrong: the **priority order** events are dispatched in, and the **interruptibility** that lets a higher-priority event pre-empt a running handler.

It also covers the melee case explicitly. Ten robots on a field means a turn carries many scan events at a single priority, which is precisely the shape the queue defect destroyed, and it is the case the project has never once measured.

## What it does not cover

The physics the events describe. That a `ScannedRobotEvent` arrives at the right moment is this capability; that its bearing and distance are correct belongs to `CAP-002`, and that the Tank Royale event was converted into the right Robocode event class belongs to `CAP-003`.

## Status

`draft`. The redesign that routed events through the Bot API's own event queue is implemented and believed correct, but it was verified by running battles and reading scores. Every criterion here is unproven in the sense that matters: nothing would tell us if it broke again. `M-001` is the plan door.

The conformance tier now reaches some of them, and the capability still holds at `draft` for two separate reasons. `EVT-004` and `EVT-007` have an established cause and an unreleased repair — [`AN-009`](../../analysis/AN-009-the-server-never-sends-a-death-to-any-bot.md): the Tank Royale server sends no death to any bot, so neither the dying robot nor the survivors are told. And the tests that do pass here are not all tagged for what they prove; [`G-002`](../../goals/G-002-conformance-evidence-proves-the-criterion-it-names.md) carries that, and until it is repaired a passing test in this capability is not on its own grounds for promoting the criterion it names.

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

`draft`. The conformance tier now proves the event queue, timing, custom-event, skipped-turn, exception, and melee behaviors with named positive and negative tests on classic and Tank Royale, so every non-retired criterion is active. The capability remains draft until its high-cost inferred runtime topology and protocol-compatibility dependencies are explicitly verified. The matched local Bot API and runner pair used for these engine runs is required by [`PDR-002`](../../decisions/PDR-002-locally-built-tank-royale-artifacts-for-conformance.md). `M-001` is the plan door.

The conformance tier now reaches every non-retired criterion. `EVT-004`, `EVT-011`, `EVT-012`, `EVT-013`, `EVT-014`, and `EVT-015` were already active — the tests that proved them were retagged after [`G-002`](../../goals/G-002-conformance-evidence-proves-the-criterion-it-names.md) found them mistagged, and `EVT-001`/`EVT-003`/`EVT-007` retired rather than be credited with evidence they cannot honestly claim ([`IDR-003`](../../decisions/IDR-003-evt-003-scoped-to-what-classic-actually-proves.md), [`IDR-004`](../../decisions/IDR-004-evt-007-scoped-to-observable-survivor-delivery.md), [`IDR-005`](../../decisions/IDR-005-evt-001-scoped-to-classic-filter-behavior.md)). `EVT-002`, `EVT-005`, `EVT-006`, `EVT-008`, `EVT-009`, and `EVT-010` are proven by bridge-owned probes and classic's authoritative test robots in the same matched pair. `IDR-006` records the callback boundary required to preserve classic exception reporting while retaining Bot API queue semantics.

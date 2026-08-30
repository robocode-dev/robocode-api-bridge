---
id: CAP-007
type: capability
status: draft
links: [G-001, ARCH-003, C-003, C-004, C-007]
goal: G-001
title: The compatibility harness
provenance: inferred
reversal-cost: low
---

# CAP-007 — The compatibility harness

The instrument itself: the orchestrator that stages a robot, runs it on both engines, collects scores and errors, and writes the report everything else is judged from.

## Why the instrument is a capability rather than tooling

Because `CAP-005`'s promises are only as good as it is, and because it has already been wrong in ways that were mistaken for the bridge being wrong.

The harness measured at ten rounds while its own documentation advised thirty-five for stability. It measured every division in a one-versus-one setup. It reported single-battle percentage deltas for bots whose scores swing by a factor of forty between runs. None of those are bridge defects, and all of them produced numbers that looked like bridge defects.

An instrument whose failures are indistinguishable from findings needs promises of its own.

## What it covers

Staging a robot for each engine without modifying it, running the battle at the division's official parameters, surviving robots that hang or crash, checkpointing so a multi-hour sweep can be interrupted and resumed, classifying each result, averaging repeats for a regression verdict, stopping a battle on a Tank-Royale-only exception, and emitting per-turn traces for diagnosis.

## What it does not cover

What the results mean. `CAP-005` owns the parity claims; this capability owns whether the measurement was taken correctly.

## Status

`draft` — and for a reason that is worth naming rather than glossing.

Some of these promises describe behaviour that exists and works: process isolation, checkpointing, result classification. They are not `Human` and they are not proven either. They are mechanical properties that deserve machine checks, and the harness is written in Python, which is not one of the evidence carriers this methodology supports.

Calling them `Human` would have been available and would have read as complete. It would also have described a maintainer's judgment where what actually exists is an untested function. `AN-003` records the gap and what closing it would take; `M-001` is the plan door.

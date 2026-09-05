---
id: CAP-006
type: capability
status: draft
links: [G-001, ARCH-001, AN-013, ADR-002]
goal: G-001
title: Team robot support
provenance: inferred
reversal-cost: high
---

# CAP-006 — Team robot support

Robocode's team division runs teams rather than individual robots: a `.team` descriptor naming several robots that start together, message each other, and win or lose as a unit. Droids are part of the same feature — robots with no radar that depend entirely on teammates for targeting.

The wrapper produces a runnable Tank Royale bot directory per team member and a team boot entry the Tank Royale booter reads to group them, and `BotPeer` gives a droid team member the `Bot` subclass Tank Royale's droid detection actually keys on. The compatibility harness stages team entries with independent member directories on both engines, and purpose-written conformance probes cover startup, teammate messaging, and droid semantics.

## Why it exists as its own capability

It is the only part of this corpus that was greenfield rather than repair, and the only one where the original failure was a clean absence rather than a subtle difference. The capability now has direct two-engine evidence, while the broader collection sweep remains a separate baseline task.

That makes it lower-risk than the score gaps and higher-effort than any of them, which is why `P-001` places it late.

## What it covers

The wrapper producing runnable Tank Royale bot directories from a team jar, teammate messaging arriving as classic delivers it, and droid semantics — a robot that receives no scan events of its own and acts on what its teammates tell it.

## What it does not cover

The TwinDuel division. It is a team division with its own official parameters and no collection directory alongside the other three, so it is out of scope until there is something to run.

## The architectural pressure this creates

`ARCH-001` describes a wrapper that turns one jar into one bot directory. A team jar is several robots plus a descriptor, so that assumption is what has to give — this capability is the first real test of the wrapper's shape rather than an addition to it.

The interesting question was where a team becomes several Tank Royale bots. `AN-013` found Tank Royale's own team model corresponds closely to classic's at every point checked, and `ADR-002` records the resulting choice: map onto it directly (one Tank Royale bot process per member, grouped by the booter's own team mechanism) rather than reconstructing team behaviour above individual bots.

## Status

`draft`. `CH-010` landed the wrapper and peer groundwork, and `CH-011` added team-battle staging plus purpose-written conformance probes. `TEAM-001` through `TEAM-003` now have two-engine integration evidence; `M-005` is complete. The capability remains draft until its high-cost inferred meaning is explicitly verified. The team collection remains useful for the broader `M-006` baseline rather than as a prerequisite for these criteria.

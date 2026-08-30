---
id: P-001
type: plan
status: active
links: [G-001]
title: Make the bridge's parity claims measurable, then close the gaps it measures
provenance: inferred
reversal-cost: high
---

# P-001 — Make the bridge's parity claims measurable, then close the gaps it measures

## The campaign

`G-001` asks for behavioural fidelity against classic Robocode. This campaign is the work of getting there, and it is sequenced around one observation: the bridge's parity claims cannot currently be checked cheaply enough to act on.

The instrument that exists is a sweep that plays every rumble jar against a copy of itself on both engines and compares scores. It takes a night, it is statistical, and it reports a quantity rather than a cause. That has already cost the project: the event-queue defect that broke radar locks and stalled rounds was first attributed to the wrong function, and was found only after a redesign that was undertaken for other reasons.

So the campaign builds the instrument before it closes the gaps. `M-001` comes first not because it is the most valuable milestone but because every milestone after it is guesswork without it.

The milestone order after `M-001` follows the priority order the project had already set for itself, which this plan preserves rather than re-derives.

## Milestones

| Milestone | Exit criterion | Status |
|---|---|---|
| M-001 — test foundation | The unit tier runs in CI over the adapter's mapper classes; the conformance tier runs classic's test robots against both engines from one stated expectation; the harness runs each division at its official parameters, averages repeats, and aborts on a Tank-Royale-only exception. The criteria those tiers prove are active rather than `@draft`. | todo |
| M-002 — score gaps where Tank Royale scores lower | Every bot the report flags as scoring materially lower has been re-measured under the current bridge at official parameters, and each is either within the band or has a named cause recorded in analysis. | todo |
| M-003 — score gaps where Tank Royale scores higher | The same, for bots scoring materially higher. A bot scoring *better* under the bridge is as much a fidelity defect as one scoring worse, and is easier to overlook. | todo |
| M-004 — file I/O sandboxing | Robot file I/O is confined to the robot's data directory as classic confines it, and the bot that surfaced the defect completes a battle without the access errors it currently produces. | todo |
| M-005 — team robot support | The wrapper produces runnable Tank Royale bot directories for team jars, and the team division is no longer skipped by the harness. | todo |
| M-007 — every peer method is proven to route correctly | Each method on the five `robocode.robotinterfaces.peer` interfaces has a unit test proving which Bot API call it makes and with what arguments, and a reflective coverage check fails the build when a method has none. | todo |
| M-006 — full sweep across all three divisions | A sweep at official parameters has run across the one-versus-one, melee, and team collections, and its report is the baseline the regression watch list is measured against. | todo |

## Why this order

`M-002` and `M-003` sit immediately after the foundation because their bots are the only concrete evidence of divergence the project has, and because their current numbers are uninterpretable: they were measured before the event-dispatch redesign and the Bot API upgrade, and the project's own note on them is to re-test before concluding anything. They are cheap to re-measure once `M-001` lands and expensive to reason about before then.

`M-004` follows because it is a known, located, unambiguous defect — the bridge does not sandbox robot file I/O at all — rather than an unexplained divergence. It is deferred behind the score gaps only because a robot writing outside its data directory produces loud errors that are already visible, while a robot turning at the wrong rate does not.

`M-005` is last of the implementation milestones because it is the only one that is greenfield rather than a repair, and because the team division is the smallest.

`M-006` closes the campaign by producing the baseline everything afterwards is measured against. It is deliberately not first: sweeping at official parameters before the foundation exists would produce another set of numbers nobody can act on.

## What would change this plan

Evidence that the score gaps in `M-002` and `M-003` share a single cause would collapse both into one milestone. The event-queue defect already demonstrated that a single engine-level bug can produce divergence across many unrelated bots, so this is a live possibility rather than a hypothetical, and `M-001`'s conformance tier is the thing most likely to reveal it.

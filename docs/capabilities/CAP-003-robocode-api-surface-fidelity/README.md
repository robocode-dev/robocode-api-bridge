---
id: CAP-003
type: capability
status: active
links: [G-001, ARCH-002]
goal: G-001
title: Robocode API surface fidelity
provenance: inferred
reversal-cost: low
---

# CAP-003 — Robocode API surface fidelity

The adapter sits between a robot written against classic Robocode's API and a Tank Royale bot runtime that uses different types, different conventions, and different names for related things. This capability is the promise that the translation between them is correct.

## Why it exists as its own capability

It is the only part of the bridge that can be tested without an engine.

Everything else this repository promises requires a battle: two engines, several processes, minutes of wall time, and a comparison at the end. The conversions — an angle from one convention into another, a Tank Royale event into its Robocode event class, a battle result into a `BattleResults` — are ordinary functions over values. They can be checked in milliseconds, on a machine with no Robocode installed, on every pull request.

That makes this capability the foundation of the whole evidence structure, and the reason it is `active` while its neighbours are `draft`. It is also the layer where a defect is cheapest to find and most expensive to miss: an angle conversion that is wrong in one quadrant will surface as a physics divergence in `CAP-002` and a score gap in `CAP-005`, having passed through two layers that cannot localise it.

## What it covers

The value translations the adapter performs: angle and bearing conventions between the two coordinate systems, colours, bullets, the mapping from a Tank Royale bot state to the status fields a robot reads, the mapping from a Tank Royale event to the Robocode event class it becomes, and battle results.

## What it does not cover

That the reproduced `robocode.*` surface is complete or correctly shaped. That is `ARCH-002`, and it is architecture rather than a testable promise — the surface is fixed by what robots were compiled against, so there is nothing to verify against except classic itself.

Nor does it cover what the robot does with a converted value once it has it. A correctly converted bearing that arrives at the wrong moment is `CAP-001`; one that produces the wrong movement is `CAP-002`.

## Status

`active`. Its criteria are proven by unit tests that run in CI — the only criteria in this corpus with machine evidence, and the first tests this repository has ever had.

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

| ID | Milestone | Exit criterion | Status |
|---|---|---|---|
| M-001 | test foundation | The unit tier runs in CI over the adapter's mapper classes; the conformance tier runs classic's test robots against both engines from one stated expectation; the harness runs each division at its official parameters, averages repeats, and aborts on a Tank-Royale-only exception. The criteria those tiers prove are active rather than `@draft`. | todo |
| M-002 | score gaps where Tank Royale scores lower | Every bot the report flags as scoring materially lower has been re-measured under the current bridge at official parameters, and each is either within the band or has a named cause recorded in analysis. | todo |
| M-003 | score gaps where Tank Royale scores higher | The same, for bots scoring materially higher. A bot scoring *better* under the bridge is as much a fidelity defect as one scoring worse, and is easier to overlook. | todo |
| M-004 | file I/O sandboxing | Robot file I/O is confined to the robot's data directory as classic confines it, and the bot that surfaced the defect completes a battle without the access errors it currently produces. | todo |
| M-005 | team robot support | The wrapper produces runnable Tank Royale bot directories for team jars, and the team division is no longer skipped by the harness. | todo |
| M-007 | every peer method is proven to route correctly | Each method on the five `robocode.robotinterfaces.peer` interfaces has a unit test proving which Bot API call it makes and with what arguments, and a reflective coverage check fails the build when a method has none. | done |
| M-006 | full sweep across all three divisions | A sweep at official parameters has run across the one-versus-one, melee, and team collections, and its report is the baseline the regression watch list is measured against. | todo |

## Evidence doors

One door per criterion the extraction could not prove. The campaign milestones above say what is being built and in what order; these say exactly which promise each piece of that work discharges, so no unproven promise can hide inside a milestone that closes for other reasons.

They are bookkeeping rather than a second plan. A door closes when its criterion stops being `@draft`.

| ID | Proves | Exit criterion | Status |
|---|---|---|---|
| M-101 | `EVT-001` | Dropped: `EVT-001` retired (`IDR-005`); see `M-143` for its successor `EVT-015`. | dropped |
| M-102 | `EVT-002` | `EVT-002` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-103 | `EVT-003` | Dropped: `EVT-003` retired (`IDR-003`); see `M-141` for its successor `EVT-013`. | dropped |
| M-104 | `EVT-004` | `EVT-004` is active, with evidence attributable to it. Work lands under M-001. | done |
| M-105 | `EVT-005` | `EVT-005` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-106 | `EVT-006` | `EVT-006` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-107 | `EVT-007` | Dropped: `EVT-007` retired (`IDR-004`); see `M-142` for its successor `EVT-014`. | dropped |
| M-108 | `EVT-008` | `EVT-008` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-109 | `EVT-009` | `EVT-009` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-110 | `EVT-010` | `EVT-010` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-111 | `PHY-001` | `PHY-001` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-112 | `PHY-002` | `PHY-002` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-113 | `PHY-003` | `PHY-003` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-114 | `PHY-004` | `PHY-004` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-115 | `PHY-005` | `PHY-005` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-116 | `PHY-006` | `PHY-006` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-117 | `PHY-007` | `PHY-007` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-118 | `PHY-008` | `PHY-008` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-119 | `FIO-001` | `FIO-001` is active, with evidence attributable to it. Work lands under M-004. | todo |
| M-120 | `FIO-002` | `FIO-002` is active, with evidence attributable to it. Work lands under M-004. | todo |
| M-121 | `FIO-003` | `FIO-003` is active, with evidence attributable to it. Work lands under M-004. | todo |
| M-122 | `FIO-004` | `FIO-004` is active, with evidence attributable to it. Work lands under M-004. | todo |
| M-123 | `SCORE-001` | `SCORE-001` is active, with evidence attributable to it. Work lands under M-006. | todo |
| M-124 | `SCORE-002` | `SCORE-002` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-125 | `SCORE-003` | `SCORE-003` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-126 | `SCORE-004` | `SCORE-004` is active, with evidence attributable to it. Work lands under M-002. | todo |
| M-127 | `SCORE-005` | `SCORE-005` is active, with evidence attributable to it. Work lands under M-003. | todo |
| M-128 | `SCORE-006` | `SCORE-006` is active, with evidence attributable to it. Work lands under M-006. | todo |
| M-129 | `TEAM-001` | `TEAM-001` is active, with evidence attributable to it. Work lands under M-005. | todo |
| M-130 | `TEAM-002` | `TEAM-002` is active, with evidence attributable to it. Work lands under M-005. | todo |
| M-131 | `TEAM-003` | `TEAM-003` is active, with evidence attributable to it. Work lands under M-005. | todo |
| M-132 | `HARN-001` | `HARN-001` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-133 | `HARN-002` | `HARN-002` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-134 | `HARN-003` | `HARN-003` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-135 | `HARN-004` | `HARN-004` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-136 | `HARN-005` | `HARN-005` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-137 | `HARN-006` | `HARN-006` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-138 | `HARN-007` | `HARN-007` is active, with evidence attributable to it. Work lands under M-001. | todo |
| M-139 | `EVT-011` | `EVT-011` is active, with evidence attributable to it. Work lands under M-001. | done |
| M-140 | `EVT-012` | `EVT-012` is active, with evidence attributable to it. Work lands under M-001. | done |
| M-141 | `EVT-013` | `EVT-013` is active, with evidence attributable to it. Successor to `EVT-003` (`M-103`). Work lands under M-001. | done |
| M-142 | `EVT-014` | `EVT-014` is active, with evidence attributable to it. Successor to `EVT-007` (`M-107`). Work lands under M-001. | done |
| M-143 | `EVT-015` | `EVT-015` is active, with evidence attributable to it. Successor to `EVT-001` (`M-101`). Work lands under M-001. | done |

## Why this order

`M-002` and `M-003` sit immediately after the foundation because their bots are the only concrete evidence of divergence the project has, and because their current numbers are uninterpretable: they were measured before the event-dispatch redesign and the Bot API upgrade, and the project's own note on them is to re-test before concluding anything. They are cheap to re-measure once `M-001` lands and expensive to reason about before then.

`M-004` follows because it is a known, located, unambiguous defect — the bridge does not sandbox robot file I/O at all — rather than an unexplained divergence. It is deferred behind the score gaps only because a robot writing outside its data directory produces loud errors that are already visible, while a robot turning at the wrong rate does not.

`M-005` is last of the implementation milestones because it is the only one that is greenfield rather than a repair, and because the team division is the smallest.

`M-006` closes the campaign by producing the baseline everything afterwards is measured against. It is deliberately not first: sweeping at official parameters before the foundation exists would produce another set of numbers nobody can act on.

## What would change this plan

Evidence that the score gaps in `M-002` and `M-003` share a single cause would collapse both into one milestone. The event-queue defect already demonstrated that a single engine-level bug can produce divergence across many unrelated bots, so this is a live possibility rather than a hypothetical, and `M-001`'s conformance tier is the thing most likely to reveal it.

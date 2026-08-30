---
id: PDR-001
type: decision
status: verified
author: agent
accepted-by: [Flemming N. Larsen]
links: [ARCH-003, CAP-003, CAP-007, C-003, C-004, C-007, AN-002, AN-003]
title: Evidence comes in three tiers, separated by cost and by what each can prove
---

# PDR-001 — Evidence comes in three tiers, separated by cost and by what each can prove

## Decision

Evidence for this repository's parity claims is produced at three tiers:

1. **Unit** — the adapter's value conversions, no engine, running in CI on every change.
2. **Conformance** — classic Robocode's own test robots, run on both engines from a single stated expectation, asserting on what the robot reports rather than on what it scored. Local.
3. **Sweep** — the rumble collections at official division parameters, averaged, gated by `C-004`. Local, and long.

## Context

Before this, the project had one instrument: the sweep. It is genuinely valuable — it is the only thing that exercises two decades of robots doing things nobody modelled — and it is a poor judge on its own.

It reports a quantity where the question is a cause. A score delta says a robot behaved differently, which is true of robots that differ for any reason. It is noisy enough that a single battle is not evidence. And it takes a night, so it cannot answer a question while the answer would change what someone does next.

The cost of that has already been paid: an engine-level defect was misattributed, and was found only in the course of a redesign undertaken for other reasons.

## Why three, and why these

The tiers are separated by **what they can prove**, and the cost ordering follows from that rather than driving it.

The unit tier is the only one that runs without an engine, which makes it the only one that can gate a pull request. It also localises perfectly: a failing conversion names itself.

The conformance tier exists because classic Robocode already contains the executable specification of the behaviour this bridge is trying to reproduce, in a form that ports. Most of its test robots assert by printing markers to the robot console rather than by comparing numbers, and a marker is engine-independent in a way a coordinate is not. This tier is the largest single gain available, and it was sitting in the neighbouring repository.

The sweep stays because the first two prove only what someone thought to specify. The collection is the part of the surface nobody modelled, and there is no substitute for running it.

## Considered and rejected

**Porting classic's exact-value tests.** Classic can assert that a robot is at a specific coordinate on a specific turn because a seed makes its battles reproducible. Tank Royale exposes no seed, so those tests cannot cross engines. `AN-002` records what this costs and what adding a seed would buy — this is a rejection driven by a missing upstream capability rather than by a judgment about the tests.

**Making the sweep the regression gate on its own.** Rejected as a matter of arithmetic: a bot whose classic score swings by a factor of forty between runs cannot be gated on, and `AN-001` is the record of that.

## Consequences

Only one tier runs in CI, so a pull request is gated on the narrowest evidence. That is a real limitation and it is stated rather than hidden: the conformance and sweep tiers need two engine installations and the rumble collection, none of which exist on a CI runner.

The sweep's orchestration is Python, which is not a supported evidence carrier, so the harness's own promises stay `@draft` even where the behaviour works. `AN-003` records that rather than letting the criteria be badged `Human`, which would have described a maintainer's judgment where an untested function is what actually exists.

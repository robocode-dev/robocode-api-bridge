---
id: CAP-005
type: capability
status: draft
links: [G-001, C-003, C-004, C-007]
goal: G-001
title: Score parity across the rumble collections
provenance: inferred
reversal-cost: low
---

# CAP-005 — Score parity across the rumble collections

The end-to-end claim: take a robot from the rumble collection, run it on both engines at its division's official parameters, and get comparable results.

## Why it exists as its own capability, given the others

`CAP-001` through `CAP-004` decompose fidelity into things that can be checked precisely. This capability is the one that cannot be decomposed, and it is not redundant with them.

Conformance tests prove the behaviours someone thought to write a test for. The collection contains two decades of robots doing things nobody anticipated — reflection tricks, timing exploits, deliberate rule-edge abuse, and a great deal of ordinary code that happens to depend on a corner of the engine no test covers. Running them is the only way to find out about the parts of the surface nobody has modelled.

The relationship between this capability and its neighbours is therefore diagnostic rather than hierarchical: this one detects, and the others localise. A score gap here with no failing conformance test is a signal that something is unmodelled, and that is a finding in itself.

## What it covers

Score comparability per division at official parameters, the regression gate over a pinned watch list of bots that have diverged, and the rule that a bot throwing only under the bridge ends its battle immediately rather than producing a score.

## What it does not cover

Why a divergence happened. This capability reports that a bot behaves differently; naming the cause is the work of the milestones and, where a cause is found, of an analysis record.

Exact score equality, which is not a coherent target. Battles are stochastic, the engines seed differently, and `AN-002` records that cross-engine determinism is unavailable. `C-004` defines what comparability means instead.

## Status

`draft`. The instrument this capability depends on exists but measures one approximated setup rather than three official ones, does not average, and does not fail fast. `M-001` builds it; `M-006` produces the baseline it measures against.

The current flagged bots are a good illustration of why the criteria are drafted rather than claimed: their numbers predate both the event-dispatch redesign and the Bot API upgrade, so the report says they diverge and the project's own note says not to believe it yet.

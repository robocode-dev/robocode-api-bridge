---
id: IDR-003
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-001, G-002]
title: EVT-003's higher-priority claim is retired; interruptible re-entry evidence is scoped to what classic's own robot proves
---

# IDR-003 — EVT-003's higher-priority claim is retired; interruptible re-entry evidence is scoped to what classic's own robot proves

## Decision

`EVT-003` retires. Its successor, `EVT-013`, claims that an interruptible handler is re-entered for a **same-priority** event once `setInterruptible(true)` is called — not a higher-priority one. `InterruptibleEventConformanceTest` is retagged to `EVT-013` unchanged; it already proves this.

## Context

`EVT-003` said a higher-priority event re-enters an interruptible handler. The only robot in the source tree written to exercise this, classic's own `tested.robots.InteruptibleEvent`, does the opposite on purpose: `setEventPriority("HitWallEvent", getEventPriority("ScannedRobotEvent"))` — same priority, by explicit comment ("make same as scan"). Classic's own suite (`TestInteruptibleEvent`) asserts only that a scan marker appears, the identical weak assertion the bridge's copy carries. The higher-priority claim was never classic's claim; it was invented when `CAP-001` was drafted, and no robot exists to prove it.

`G-002` named this as needing a decision rather than a mechanical retag: either narrow the name to match the test (retiring `EVT-003`), or strengthen the assertion to separate re-entry from ordinary delivery (keeping it). Strengthening is not available here — the robot's source is fixed (classic is the specification, `ARCH-002`), so the priorities cannot be changed to produce a genuine higher-priority case, and no substitute robot exercises one.

## Why this way

A criterion this repository can never gather evidence for, because no available robot exercises it, is worse than a narrower criterion the repository actually enforces. `EVT-013`'s scope is exactly what the fixed evidence source can prove, so a passing test means what it claims for as long as it passes.

## Consequences

Genuine higher-priority interruption re-entry is now uncovered rather than falsely covered. If a future analysis finds or writes a robot that produces one, mint a new criterion for it rather than reopening `EVT-013` — that would be a new behaviour, not a repair to this one.

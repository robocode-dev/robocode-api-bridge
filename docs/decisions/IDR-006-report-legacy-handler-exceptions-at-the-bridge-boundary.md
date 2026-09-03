---
id: IDR-006
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-001, ARCH-003]
title: Report legacy handler exceptions at the bridge callback boundary
---

# IDR-006 — Report legacy handler exceptions at the bridge callback boundary

## Decision

`BotPeer` invokes every legacy robot event callback inside a bridge-owned boundary. If the callback throws a runtime exception, the boundary prints it to the bot process's standard error and returns so the surrounding event queue can continue dispatching. Errors used by the Bot API for control flow, such as interrupting an event handler, are not caught.

## Context

The matched Tank Royale Bot API catches subscriber exceptions during event publication and otherwise provides no observable error to the conformance harness. Classic Robocode reports a `NullPointerException` thrown from `onStatus` while continuing the battle, so delegating event ordering to the Bot API is not enough to preserve the classic observable contract.

## Why this way

The boundary is the narrowest place that sees the legacy callback before the Bot API swallows its runtime exception. Reporting there preserves the Bot API's priority and queue implementation, keeps the robot lifecycle unchanged, and gives the harness the same error signature that classic exposes. Leaving control-flow errors untouched preserves interruptible-handler semantics. Replacing the upstream dispatcher or stopping the bot would alter unrelated event and lifecycle behavior.

## Consequences

Future event adapters must route legacy callbacks through this boundary so handler failures remain observable. Error evidence comes from the isolated bot process logs under `ARCH-003`; the bridge does not add a second event queue or change the frozen `robocode.*` surface.

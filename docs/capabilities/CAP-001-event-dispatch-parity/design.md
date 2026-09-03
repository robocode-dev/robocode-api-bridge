---
id: DES-001
type: design
status: active
links: [CAP-001, IDR-001, IDR-005, IDR-006, ADR-001, ARCH-002]
title: Event dispatch and timing parity — design
provenance: inferred
reversal-cost: low
---

# CAP-001 — design

## How events reach a robot

The Bot API owns an event queue with priorities, interruptible events, and a thread-interruption mechanism for pre-empting a running handler. The bridge does not reimplement any of that. `BotPeer`'s inner bot implementation overrides the Bot API's own handler methods — `onScannedBot`, `onTick`, and the rest — and each override delegates to the mapping helper that converts the Tank Royale event into its Robocode counterpart and hands it to the robot.

The consequence is that priority ordering and interruptibility are the Bot API's behaviour, not the bridge's. `IDR-001` records why that delegation replaced the alternative.

One compatibility edge remains at the boundary: classic expires lower-priority scan events while a higher-priority `onHitWall` handler is blocked by a radar turn, while the Bot API can retain those events until the handler returns. `BotPeer` observes the configured priorities and suppresses only those lower-priority scans during the blocked interval and its current-turn remainder; same-priority re-entry remains delegated to the Bot API. `IDR-005` records why this narrow boundary rule is needed.

## What this replaced, and why the shape matters

The bridge previously dispatched events itself: a manual switch over event types, driven from the turn loop, with bookkeeping to track which events had already been dispatched and which were mid-dispatch.

That design had two failures, and only one of them was a bug.

The bug was an omission — no case existed for the robot's own death, so `onDeath` was never called at all. A manual switch fails silently on the event type nobody remembered.

The deeper failure was structural. Reimplementing dispatch meant reimplementing priority ordering and interruptibility, which meant the bridge's event semantics could drift from the Bot API's without anything noticing. When the real defect turned out to live in the Bot API's queue, the bridge's parallel implementation made it harder to see, because there were two candidate explanations for every symptom.

Delegating leaves one implementation to be correct. `ADR-001` covers the version it had to be correct in.

## Timing

Since Bot API 1.0.2, new-turn events dispatch at the end of `execute()`, which is classic Robocode's timing. The bridge previously needed a hook into the turn loop to force dispatch at the right moment; it no longer does, and that hook is gone. `TurnBoundaryProbe` records the event time, the status snapshot time, and the peer clock on both engines across multiple turns so a dispatch at the wrong boundary is observable.

## Handler exceptions

The Bot API's event publisher catches subscriber runtime exceptions so one callback cannot prevent other subscribers from receiving the event. That is compatible with classic's continued battle processing, but it hides the failure from the process logs that form conformance evidence. `BotPeer` therefore reports runtime exceptions at the legacy callback boundary and then returns to the Bot API queue; control-flow errors used by the Bot API for interruptible handlers pass through unchanged. `IDR-006` records this narrow exception-reporting rule.

## The guard

`setInterruptible` is null-guarded. The Bot API permits the call at points where no event is currently being dispatched, and a robot may reach it through a code path where the current event is absent. The upstream fix landed in the Bot API across its language implementations under `C-006`; the guard here is the local defence.

## Where this design is most likely to be wrong

Melee. Everything above was reasoned about, and verified against, one-versus-one battles. A melee turn carries many same-priority scan events, and both the queue behaviour and the interaction between dispatch and blocking calls have more room to differ there. `EVT-010` now exercises the official melee participant count with a per-turn scan-count probe; the harness passes the participant count through to both engine runners.

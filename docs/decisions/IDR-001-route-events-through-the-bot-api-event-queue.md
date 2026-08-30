---
id: IDR-001
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-001, ADR-001]
title: Route robot events through the Bot API's event queue rather than dispatching them in the bridge
---

# IDR-001 — Route robot events through the Bot API's event queue

## Decision

The bridge does not dispatch events. `BotPeer`'s bot implementation overrides the Bot API's own handler methods, and each override converts the Tank Royale event and hands it to the robot. Priority ordering, interruptibility, and the thread interruption used to pre-empt a running handler are the Bot API's behaviour, used as it ships.

## Context

The bridge previously dispatched events itself: a manual switch over event types driven from the turn loop, with bookkeeping tracking which events had been dispatched and which were mid-dispatch. That meant the bridge held its own implementation of the event semantics — priority order, interruptibility, re-entry — alongside the one the Bot API already had.

Two problems followed, and they were different in kind.

A manual switch is silently incomplete. No case existed for the robot's own death, so `onDeath` was never called for any robot, ever. Nothing reports the branch nobody wrote.

The second problem cost more. Two implementations of the same semantics meant every symptom had two candidate explanations. When robots did lose events, the bridge's dispatcher was the natural suspect, and the investigation went there first. The defect was in the Bot API's queue, and finding it took longer than it should have because the bridge's parallel implementation was in the way.

## Why this way

The engine's semantics should have one implementation, and it should be the engine's. Reproducing them in the bridge means reproducing them *correctly and permanently*, tracking every upstream change — a commitment nobody made explicitly and nobody could have kept.

Delegation also makes upstream defects visible as upstream defects. Under this design, a queue bug produces a symptom in every bridge consumer and the fix lands where it belongs, under `C-006`.

## Consequences

The bridge's correctness here now depends on the Bot API version it links, which is why `C-002` exists and why `ADR-001` had to be decided alongside this one. Delegating to a queue that drops events is worse than dispatching badly yourself.

`setInterruptible` needs a null guard: the Bot API permits the call where no event is currently dispatching, and a robot can reach it that way. The upstream fix landed across the language implementations; the guard here is the local defence.

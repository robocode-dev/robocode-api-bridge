---
id: ADR-001
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-001, IDR-001, C-002, AN-001]
title: Link a Bot API whose event queue preserves deferred same-priority events
---

# ADR-001 — Link a Bot API whose event queue preserves deferred same-priority events

## Decision

The bridge links a Bot API version whose event queue peeks at the next event and removes it only on dispatch, rather than one that pops events and discards those it cannot deliver. Version 0.33.1 has the discarding behaviour and must not be used; the bridge moved to the 1.0 line, which does not.

## Context

The Bot API's event queue delivers events by priority. In 0.33.1, when an event was deferred — because the robot was inside a handler and the queue could not deliver it yet — the event had already been removed from the queue and was simply lost.

The robots this hits are the ordinary ones. A robot that calls a blocking method such as `fire()` from inside `onScannedRobot` is doing the most conventional thing in Robocode, and it is exactly the pattern that defers same-priority events. Those robots received every other scan event.

The damage was not a lost event. It was a different game: radar locks that could not hold, robots that could not see each other stalling out the round, and a compatibility report full of plausible numbers describing battles that were not Robocode. One bot scored zero across every round in a way that was first attributed to per-round robot construction, which is a real thing the bridge does and was not the cause.

## Why this way

Nothing else fixes it. The defect is in the shared queue that `IDR-001` delegates to, so the bridge cannot work around it without reintroducing the parallel dispatcher that decision removed. The two decisions only make sense together.

Upgrading also brought classic turn timing: new-turn events dispatch at the end of `execute()`, so the bridge no longer needs a hook into the turn loop to force dispatch at the right moment.

## Consequences

The Bot API version and the server embedded in the runner jar must stay protocol-compatible, and nothing checks that. `C-002` carries the rule and the cost.

The stricter rule this decision leaves behind: never select a Bot API version because it resolves. 0.33.1 resolves cleanly, builds cleanly, runs battles cleanly, and produces wrong results silently — which is the whole reason this record exists rather than being a line in a changelog.

Every score measured before this upgrade is uninterpretable and cannot serve as a baseline. `P-001` sequences re-measurement for that reason, and `AN-001` records what the old numbers can and cannot be used for.

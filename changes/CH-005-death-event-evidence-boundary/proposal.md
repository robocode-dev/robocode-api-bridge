---
id: CH-005
type: change
status: open
links: [P-001, CAP-001, AN-009, C-002]
title: Establish the dependency boundary for death-event conformance evidence
---

# CH-005 — Establish the dependency boundary for death-event conformance evidence

## What

`AN-009` established that the released Tank Royale server failed to deliver death events to every bot, leaving both `EVT-004` (a robot's own death reaches `onDeath`) and `EVT-007` (a survivor receives another robot's death) correctly marked `@draft`. The upstream server repair is now present on Tank Royale `main`, but no released tag contains it.

This change records the approved local-build policy, then uses a locally built Bot API and runner from the same Tank Royale revision to add conformance evidence for `EVT-004` and `EVT-007`. It will promote only the criteria that evidence proves.

## Why

The bridge currently compiles against Bot API `1.0.2`, while its conformance tier launches a separate Tank Royale runner. `C-002` requires those two sides to stay protocol compatible, so the local-build setup must use a matched pair and confirm that bots act before treating the results as evidence. A Tank Royale release is not needed for that work.

## Route

Full. The eventual decision determines whether two event-dispatch criteria may be accepted as proven and what upstream dependency boundary the bridge may rely on.

---
id: CH-005
type: change
status: proposed
links: [P-001, CAP-001, AN-009, C-002]
title: Establish the dependency boundary for death-event conformance evidence
---

# CH-005 — Establish the dependency boundary for death-event conformance evidence

## What

`AN-009` established that the released Tank Royale server failed to deliver death events to every bot, leaving both `EVT-004` (a robot's own death reaches `onDeath`) and `EVT-007` (a survivor receives another robot's death) correctly marked `@draft`. The upstream server repair is now present on Tank Royale `main`, but no released tag contains it.

This change will establish whether the bridge may consume that unreleased upstream repair for conformance evidence, or must wait for a compatible released server and Bot API pair. Until that decision, it will not promote either criterion or alter the bridge's declared dependency version.

## Why

The bridge currently compiles against Bot API `1.0.2`, while its conformance tier launches a separate Tank Royale runner. `C-002` requires those two sides to stay protocol compatible, and the repository has no check that proves a locally built Bot API and runner pair meet that boundary. Treating an upstream-main build as accepted evidence without a decision would make `EVT-004` and `EVT-007` look reproducible when the project cannot yet guarantee that.

## Route

Full. The eventual decision determines whether two event-dispatch criteria may be accepted as proven and what upstream dependency boundary the bridge may rely on.

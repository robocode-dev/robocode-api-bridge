---
id: CAP-004
type: capability
status: draft
links: [G-001, C-005]
goal: G-001
title: Robot file I/O sandboxing
provenance: inferred
reversal-cost: high
---

# CAP-004 — Robot file I/O sandboxing

Classic Robocode confines everything a robot writes to that robot's own data directory. A robot that opens an absolute path gets a file inside its data directory instead, and never learns the difference. This capability is the promise that the bridge does the same.

It currently does not. This capability is a specification for work not yet done, which is why it is the one place in this corpus where the criteria describe behaviour that does not exist.

## Why it exists as its own capability

Robots depend on the redirection, and the dependency is invisible in their source. A robot that saves its learned targeting data to a root path is not misbehaving — it is a robot whose author relied on the engine to place the file, correctly, because under classic the engine always did. One such bot in the collection produces access-denied errors in the thousands over a single battle, and its behaviour under the bridge is not a degraded version of its classic behaviour but a different robot: one whose learning never persists.

The safety reading points the same way. Rumble jars are downloaded code run unmodified on a maintainer's machine. Classic sandboxes them; the bridge does not.

## What it covers

Path confinement for the file wrappers a robot uses, the resolution rule that makes `getDataFile` and `getDataDirectory` agree with each other, and the size cap classic enforces on a robot's data directory.

## What it does not cover

The rest of classic's sandbox. Classic also restricts threads, reflection, sockets, and other ambient authority, and its own test suite has robots for each. Those are real gaps in the bridge and they are deliberately not promised here: this capability is scoped to file I/O, which is the part with an active, observed defect. Widening the scope to the whole sandbox would turn a milestone into a project.

## Status

`draft`, and honestly so. Every criterion describes behaviour the bridge lacks. `M-004` is the plan door, and `C-005` is the constraint these criteria discharge.

The one thing that makes this cheaper than it looks: classic's own test suite already contains robots that assert confinement by attempting to escape it. `M-004` implements the sandbox and ports those robots in the same milestone, so the criteria gain machine evidence at the moment the behaviour appears.

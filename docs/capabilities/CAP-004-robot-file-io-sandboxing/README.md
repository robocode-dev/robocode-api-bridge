---
id: CAP-004
type: capability
status: active
links: [G-001, C-005, IDR-007]
goal: G-001
title: Robot file I/O sandboxing
provenance: inferred
reversal-cost: high
---

# CAP-004 — Robot file I/O sandboxing

Classic Robocode confines everything a robot writes, through its own data-file API, to that robot's own data directory. A robot that opens an absolute path gets a file inside its data directory instead, and never learns the difference. This capability is that redirection, implemented at the one point the bridge controls: `RobotData.getDataFile`/`getDataDirectory`.

## Why it exists as its own capability

Robots depend on the redirection, and the dependency is invisible in their source. A robot that saves its learned targeting data to a root path is not misbehaving — it is a robot whose author relied on the engine to place the file, correctly, because under classic the engine always did. One such bot in the collection produced access-denied errors in the thousands over a single battle before this capability existed, and its behaviour under the bridge was not a degraded version of its classic behaviour but a different robot: one whose learning never persisted.

## What it covers

Path confinement inside `getDataFile`/`getDataDirectory` — asterisks stripped and a `java.io.File` merge used so an absolute or root-relative name is re-rooted inside the directory rather than overriding it, `..` rejected exactly as classic rejects it — plus the 200000-byte quota classic enforces on a robot's data directory.

## What it does not cover

The rest of classic's sandbox, and, within file I/O itself, a raw `java.io` call that never goes through `getDataFile`. Classic blocks those unconditionally on path with a JVM `SecurityManager`, a mechanism JDK 24 removed and this bridge cannot reproduce; `IDR-007` records why `FIO-004` — the criterion that names this — stays `@draft` rather than closing on evidence a bridge probe cannot honestly produce. Threads, reflection, sockets, and other ambient authority are the same shape of gap and are likewise not promised here.

## Status

`active`. `FIO-001`–`FIO-003` are proven; `FIO-004` remains `@draft` per `IDR-007`. `M-004` is the plan door, and `C-005` is the constraint these criteria discharge — partially: the redirection and quota rules are machine-enforced, the raw-`java.io` case is not.

---
id: IDR-007
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-004, C-005]
title: File I/O confinement covers the getDataFile/getDataDirectory surface, not raw java.io calls
---

# IDR-007 — File I/O confinement covers the `getDataFile`/`getDataDirectory` surface, not raw `java.io` calls

## Decision

`FIO-004` is satisfied by confining paths reached through `getDataFile`/`getDataDirectory` — the surface `RobotData` resolves and `RobocodeFileOutputStream`/`RobocodeFileWriter` route through. A robot that bypasses that surface entirely, opening `java.io.FileInputStream`/`FileOutputStream` on an arbitrary path with no call to `getDataFile`, is not confined by this capability.

## Context

Classic's own conformance robots test both cases. `FileOutputStreamAttack` calls `getDataFile("test")` before opening a stream, so it is confined by construction once `RobotData`'s resolver re-roots the name. `FileAttack` opens `C:\MSDOS.SYS` and `C:\Robocode.attack` directly, with no `getDataFile` call anywhere in the path; classic blocks it with the JVM `SecurityManager`'s `checkRead`/`checkWrite`, a mechanism JDK 24 removed outright and this bridge does not reproduce.

## Why this way

`docs/architecture/README.md` already draws this line: "the rest of classic's sandbox... threads, reflection, and sockets are real gaps that are scoped out rather than forgotten." Raw-`java.io` interception is the same shape of gap — it needs a security-manager successor, a custom `FileSystemProvider`, or bytecode instrumentation of robot jars, none of which exist in this bridge today, and building one is a separately-scoped capability rather than a corner of `CAP-004`'s resolver work. Confining the surface the bridge actually controls, and naming the rest as a known boundary, keeps the claim `CAP-004` makes checkable instead of quietly wider than what was built.

## Consequences

`FileOutputStreamAttack` ports as `FIO-004` evidence; `FileAttack` is not ported under `FIO-004` (it may be recorded elsewhere as a documented out-of-scope gap alongside threads/reflection/sockets, but that is not this decision's obligation to do). A future capability proposing raw-`java.io` confinement would need its own criteria and cannot piggyback on `FIO-004`'s current wording without first revising it.

---
id: CH-009
type: change
status: open
links: [P-001, M-004, CAP-004, C-005, FIO-001, FIO-002, FIO-003, FIO-004, IDR-002, IDR-007]
title: Confine robot file I/O to the robot's data directory
---

# CH-009 — Confine robot file I/O to the robot's data directory

## What

Give `RobotData.getDataFile`/`getDataDirectory` the confinement classic Robocode has and the bridge does not: an absolute or root-relative path a robot names is re-rooted inside the robot's data directory rather than passed through, matching classic's `RobotFileSystemManager.getDataFile` exactly (asterisks stripped, `..` rejected, `java.io.File` merge semantics rather than `Path#resolve`), and the directory's total size is capped at the documented 200000-byte quota. Move `FIO-001`, `FIO-002`, `FIO-003` out of `@draft` with integration evidence, and give `C-005` machine enforcement for those rules.

`FIO-004` stays `@draft`. This was a blocking open question at proposal time; building and running the evidence probe on both engines during implementation showed the narrowed reading originally proposed does not hold. `IDR-007` has the finding: both of classic's own `FIO-004` test robots (`FileAttack`, `FileOutputStreamAttack`) are attacks classic *blocks*, one of them even when the path is already confined through `getDataFile`, because classic's confinement here is a JVM `SecurityManager` gate that is unconditional on path, not a redirection this bridge's resolver can reproduce. JDK 24 removed `SecurityManager` outright, and the architecture record already scopes this class of gap out (alongside threads/reflection/sockets). No criterion closes on evidence a bridge probe cannot honestly produce, so `FIO-004` remains open, its residual named in `C-005`.

## Why

`C-005` already states the defect: `RobotData.getDataFile` uses `java.nio.file.Path#resolve`, which returns an absolute argument unchanged rather than re-rooting it — the opposite of classic's silent redirection. A rumble bot that writes to a root-level path (`AN-012`'s note on Windows permission-failure floods) produces thousands of access-denied errors under the bridge where classic produced a normal write inside the robot's directory. `IDR-002` aligned `getDataDirectory` and `getDataFile` to the same lookup but explicitly left confinement undone; `FIO-002`'s `@draft` status is the record of that gap, and this change is what closes it.

The quota (`FIO-003`) is documented in three Javadoc comments (`RobocodeFileOutputStream`, `RobocodeFileWriter`, `IAdvancedRobotPeer#getDataFile`) and enforced nowhere; a robot that reads its own Javadoc and expects a `SYSTEM:`-prefixed quota message and an `IOException` at 200000 bytes currently gets neither.

## Route

Full. This closes acceptance criteria that were `@draft` (`FIO-001`, `FIO-002`, `FIO-003`), leaves `FIO-004` `@draft` with a corrected, evidence-backed reason recorded as `IDR-007`, and promotes `C-005` to partial enforcement — all are contract changes, not refactors of unchanged behaviour.

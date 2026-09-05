---
id: DES-004
type: design
status: active
links: [CAP-004, IDR-002, IDR-007, ARCH-002, C-005]
title: Robot file I/O sandboxing — design
provenance: inferred
reversal-cost: high
---

# CAP-004 — design

## What existed before this capability

`RobocodeFileOutputStream` and `RobocodeFileWriter` are reproduced in the frozen `robocode.*` surface with their classic signatures. Before `CH-009`, `RobotData.getDataFile` resolved a robot-supplied name with `java.nio.file.Path#resolve`, which returns an absolute argument unchanged rather than re-rooting it — the opposite of classic's redirection. The documented 200000-byte data directory size cap was documented only.

`getDataDirectory` was already changed, ahead of this capability, to resolve through the same robot data lookup `getDataFile` uses, so the two agree about where the robot's directory is (`IDR-002`). That removed a disagreement; it did not add confinement.

## The design that is implemented

One resolution point, used by everything that reaches the filesystem through it: `RobotData.getDataFile`, matching classic's `RobotFileSystemManager.getDataFile` rather than inventing a new scheme.

- Asterisks are stripped from the supplied name.
- `..` anywhere in the name is rejected with a `java.security.AccessControlException` ("no relative path allowed") — classic's own `AdvancedRobotProxy.getDataFile` throws the same exception for the same reason, so a robot that already handles it on classic behaves the same way here.
- The remaining name is merged against the data directory with `new File(directory, name)` — `java.io.File`'s merge, not `java.nio.file.Path#resolve` — because `File`'s system-dependent handling of a root-relative or drive-relative child is what re-roots it inside the parent instead of overriding it. A true drive-letter-absolute name (`C:\...` on Windows) is not re-rooted by this merge in classic either; matching classic's own mechanism exactly, warts included, is the fidelity-correct choice over inventing a stronger guarantee classic does not make.
- `RobocodeFileOutputStream`'s `String` constructor opens whatever path it is given verbatim (`new FileOutputStream(fileName, append)`), matching classic's `ThreadManager.createRobotFileStream`. It does **not** call `getDataFile` again: a `File` obtained from `getDataFile` is already resolved, and re-resolving an already-absolute path through the same `File`-merge logic re-roots it under itself, corrupting the path. This was the first shape this change's implementation took, and the conformance run against both engines is what surfaced it (see `FileRedirectionConformanceTest`'s history in `CH-009`).

The quota is enforced at the same layer regardless of how a stream's path was obtained: `RobotData` tracks bytes charged against the 200000-byte cap (seeded from the existing data directory's contents at startup, matching classic's `initializeQuota`), and `RobocodeFileOutputStream.write` charges each write before performing it, closing the stream and raising the same `IOException` message classic raises when the charge would exceed the cap.

**The robot is not told about redirection.** Classic redirects silently, and a robot that discovers it has been redirected — by an exception, or by reading back a path — behaves differently from the robot that ran on classic.

## What this design does not cover, and why

Classic also blocks a raw `java.io.FileOutputStream`/`FileInputStream` unconditionally on path — even one already confined to the robot's own data directory — through a JVM `SecurityManager` that JDK 24 removed. `IDR-007` records this in detail: both of classic's own `FIO-004` evidence robots (`FileAttack`, `FileOutputStreamAttack`) depend on that mechanism, not on path resolution, so neither can be ported as evidence that this design's resolver satisfies `FIO-004`. That criterion stays `@draft`.

## Evidence

`FileRedirectionConformanceTest` (`FIO-001`, `FIO-002`) and `FileQuotaConformanceTest` (`FIO-003`) run a probe on both engines under `ARCH-003`'s conformance tier, giving `C-005` machine enforcement for the redirection and quota rules. The raw-`java.io` case `FIO-004` names remains agent-judgment-held, as it was before this change.

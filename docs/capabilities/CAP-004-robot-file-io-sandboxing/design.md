---
id: DES-004
type: design
status: draft
links: [CAP-004, IDR-002, ARCH-002, C-005]
title: Robot file I/O sandboxing — design
provenance: inferred
reversal-cost: high
---

# CAP-004 — design

`status: draft` because this describes an intended design rather than an implemented one.

## What exists today

`RobocodeFileOutputStream` and `RobocodeFileWriter` are reproduced in the frozen `robocode.*` surface with their classic signatures, and each wraps a plain file stream directly. A path a robot passes in is the path that reaches the filesystem. There is no redirection layer, and the documented data directory size cap is documented only.

`getDataDirectory` was changed to resolve through the same robot data lookup that `getDataFile` uses, so the two agree about where the robot's directory is. `IDR-002` records that. It removed a disagreement; it did not add confinement.

## The intended design

One resolution point, used by everything.

Every path a robot supplies passes through a single function that maps it into the robot's data directory: absolute paths are re-rooted, relative paths are resolved against the directory rather than against the process working directory, and traversal that would climb out is resolved inside. The file wrappers call it, and no wrapper opens a path the resolver has not returned.

Two properties matter more than the mechanism:

**The robot is not told.** Classic redirects silently, and a robot that discovers it has been redirected — by an exception, or by reading back a path — behaves differently from the robot that ran on classic. Silent redirection is the fidelity-preserving choice even though it is the less honest-looking one.

**One resolver, not one per wrapper.** The failure mode of per-wrapper redirection is a wrapper someone forgets, which reintroduces the whole defect through a path nobody tests. A single resolver makes the confinement claim checkable by inspection.

The size cap belongs at the same point, since it is a property of the directory rather than of any one file.

## Why the deleted-path case is the interesting one

The bot that surfaced this writes to a root path on a Windows machine, and the errors it produces are permission failures rather than missing-file failures. That distinction matters for `M-004`'s verification: after the fix, the correct outcome is not fewer errors but zero, and a data directory containing the file the robot thought it wrote elsewhere.

## Evidence plan

Classic's test suite already contains robots built to escape the sandbox and assert that they could not. Porting them under `ARCH-003`'s conformance tier gives `FIO-001` and `FIO-004` machine evidence at the moment the behaviour lands, and the same run gives `C-005` its promotion out of agent enforcement.

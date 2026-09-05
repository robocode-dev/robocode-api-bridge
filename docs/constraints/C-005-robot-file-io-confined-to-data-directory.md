---
id: C-005
type: constraint
status: active
links: [CAP-004]
title: A robot reads and writes only inside its own data directory
source: Classic Robocode's robot sandbox, which every rumble robot was written against
enforcement: partial
provenance: inferred
reversal-cost: high
---

# C-005 — A robot reads and writes only inside its own data directory

Classic Robocode confines all robot file I/O reached through `getDataFile`/`getDataDirectory` to the robot's own data directory. A robot that opens an absolute path through that surface does not fail; the engine resolves that path inside the robot's data directory and the write lands there. Classic separately blocks a raw `java.io` call that bypasses that surface entirely, confined path or not, through a JVM `SecurityManager`.

## Why this binds rather than merely being desirable

Robots depend on the redirection. A robot that writes to a root path is not attempting to escape — it is a robot whose author relied on the engine to place the file, because under classic the engine always did. Running it without the redirection turns working code into a robot that throws on every save, and one such bot in the collection produced access-denied errors in the thousands during a single battle before `CH-009`.

It is also a safety rule, and the safety and fidelity readings point the same way. Classic sandboxes robot I/O because robot jars are downloaded from the internet and run unmodified. The bridge runs the same untrusted jars, on the same machines.

Note which direction the parity argument runs here. `G-001` says classic is right by definition; the bridge is not free to be *more* permissive than classic because permissiveness looks harmless in a battle report. A robot that successfully writes outside its data directory produces no error at all, so nothing in the current instrument would ever report it.

**Checked by:** `FileRedirectionConformanceTest` (`FIO-001`, `FIO-002`) and `FileQuotaConformanceTest` (`FIO-003`), run under `ARCH-003`'s conformance tier on both engines, for the redirection and quota rules `RobotData.getDataFile`/`getDataDirectory` and `RobocodeFileOutputStream` enforce.

**Residual:** a raw `java.io.FileOutputStream`/`FileInputStream` call that never goes through `getDataFile` — classic blocks this unconditionally on path with a `SecurityManager`, a mechanism JDK 24 removed and this bridge cannot reproduce. `IDR-007` records why, and why the two classic test robots that would otherwise prove `FIO-004` cannot be ported as evidence here. Judgment holds this residual rule in the meanwhile, which in practice means it is not held: reviewers cannot see an unconfined raw write in a diff of robot bytecode they did not write.

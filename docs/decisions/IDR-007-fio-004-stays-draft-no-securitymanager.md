---
id: IDR-007
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-004, C-005]
title: FIO-004 stays @draft; classic's own evidence for it depends on a SecurityManager this bridge cannot have
---

# IDR-007 — `FIO-004` stays `@draft`; classic's own evidence for it depends on a `SecurityManager` this bridge cannot have

## Decision

`CH-009` closes `FIO-001`, `FIO-002`, and `FIO-003` but leaves `FIO-004` `@draft`. Confinement is implemented at the one point this bridge controls — `RobotData.getDataFile`/`getDataDirectory`, matching classic's `RobotFileSystemManager.getDataFile` in its re-rooting (deliberately deviating from classic's own asterisk/`..` check order, per `CAP-004/design.md`, because classic's order lets a name like `".*."` collapse into a traversal after its own check passes) — but that is not what classic's own `FIO-004` evidence tests. Neither of classic's conformance robots named in `CAP-004`'s original evidence plan can be ported as positive evidence under this bridge.

## Context

Both `tested.robots.FileAttack` and `tested.robots.FileOutputStreamAttack` are attack robots that classic's own test suite (`TestFileAttack`, `TestFileOutputStreamAttack`) expects to be *blocked*, not to succeed:

- `FileAttack` opens `java.io.FileOutputStream`/`FileInputStream` on arbitrary paths with no call to `getDataFile` at all.
- `FileOutputStreamAttack` calls `getDataFile("test")` first — obtaining a path already inside the robot's own data directory — and then opens a raw `java.io.FileOutputStream` on that resolved `File`. `TestFileOutputStreamAttack` asserts `expected = AccessControlException.class`: classic blocks this too, even though the path is already confined.

Reading `RobocodeSecurityPolicy.impliesRobotFileWrite`: the block is unconditional on path. `checkRobotFileStream()` only returns `true` for a write that originates from `ThreadManager.createRobotFileStream` — i.e., a `RobocodeFileOutputStream` construction — regardless of where that write lands; a raw `java.io.FileOutputStream`, confined path or not, is denied because it never goes through that gate. Building this probe against the bridge and running it on both engines (`compat-test`, both directions) confirms this empirically: classic throws the expected `AccessControlException`; the bridge, lacking any `SecurityManager`, does not, so the probe cannot serve as `FIO-004` evidence either way — succeeding on the bridge would not demonstrate confinement, and failing to reproduce classic's block is the exact gap this decision documents.

This is the same removal `AGENTS.md` already records: JDK 24 removed `SecurityManager` outright, which is why classic itself needs an older JDK to run at all. `docs/architecture/README.md` independently scopes this class of gap out: "the rest of classic's sandbox... threads, reflection, and sockets are real gaps that are scoped out rather than forgotten." Blocking a raw `java.io` call irrespective of the path it targets is that same shape of gap, not a corner of the path-redirection work `CAP-004`'s design describes.

## Why this way

Closing `FIO-004` without the mechanism it actually depends on would either narrow the criterion's wording to match what got built — misrepresenting what classic guarantees, since classic's promise is broader than "resolved paths are re-rooted" — or claim evidence a bridge probe cannot honestly produce. Leaving it `@draft` states the gap instead of hiding it inside a passing test. A future capability that adds a security-manager successor, a custom `FileSystemProvider`, or bytecode instrumentation of robot jars could close it; that is separately-scoped work, matching how threads/reflection/sockets are already held out of `CAP-004`.

## Consequences

`C-005`'s promotion out of agent enforcement is partial: the redirection and quota rules it names (`FIO-001`–`FIO-003`) are now machine-enforced, but the broader "reads and writes only inside its own data directory" claim remains judgment-held for the raw-`java.io` case, same as before this change. `CAP-004`'s design and criteria record the narrower, achieved scope; the residual is named rather than implied closed.

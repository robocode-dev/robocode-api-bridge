---
id: C-005
type: constraint
status: active
links: [CAP-004]
title: A robot reads and writes only inside its own data directory
source: Classic Robocode's robot sandbox, which every rumble robot was written against
enforcement: agent
provenance: inferred
reversal-cost: high
---

# C-005 — A robot reads and writes only inside its own data directory

Classic Robocode confines all robot file I/O to the robot's own data directory. A robot that opens an absolute path does not fail; the engine resolves that path inside the robot's data directory and the write lands there.

The bridge must do the same, and currently does not.

## Why this binds rather than merely being desirable

Robots depend on the redirection. A robot that writes to a root path is not attempting to escape — it is a robot whose author relied on the engine to place the file, because under classic the engine always did. Running it without the redirection turns working code into a robot that throws on every save, and one such bot in the collection produces access-denied errors in the thousands during a single battle.

It is also a safety rule, and the safety and fidelity readings point the same way. Classic sandboxes robot I/O because robot jars are downloaded from the internet and run unmodified. The bridge runs the same untrusted jars, on the same machines, with the sandbox absent.

Note which direction the parity argument runs here. `G-001` says classic is right by definition; the bridge is not free to be *more* permissive than classic because permissiveness looks harmless in a battle report. A robot that successfully writes outside its data directory produces no error at all, so nothing in the current instrument would ever report it.

## Residual

Everything. No path redirection exists in the bridge's file wrappers, and no test asserts confinement.

Judgment holds the rule in the meanwhile, which in practice means it is not held: reviewers cannot see an unconfined write in a diff of robot bytecode they did not write.

**Promotion trigger:** `M-004`. The classic engine's own test robots include file-attack cases that assert confinement, and porting them gives this constraint machine enforcement in the same milestone that implements it. `CAP-004` holds the criteria.

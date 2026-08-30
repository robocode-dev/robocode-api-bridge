---
id: C-007
type: constraint
status: active
links: [CAP-005, CAP-007, ARCH-003]
title: Rumble jars are never modified; their bytecode and bundled sources are read only for debugging
source: The maintainer's direction when resolving OQ-001, recorded in PDR-001
enforcement: agent
provenance: inferred
reversal-cost: high
---

# C-007 — Rumble jars are never modified; their bytecode and bundled sources are read only for debugging

The rumble collection is the measurement standard, held behind a single named constant rather than repeated as a path literal. Its jars are inputs: read, staged, and run, never rewritten. Their bytecode and any sources they bundle may be disassembled and read to understand why a robot behaves as it does — that is debugging, and it is encouraged.

## Why a modified jar is worse than a missing one

Every comparison this project makes is a claim of the form "the same robot behaves the same way on both engines". The claim rests entirely on *same robot*. Modify the jar and both sides still run, both sides still produce scores, the report still fills in — and every row is now a statement about a robot that does not exist. A missing jar is a gap you can see; a modified jar is a result you cannot distinguish from a real one.

The rumble ranking is the second reason. A bot's ranking is a large body of existing evidence about its behaviour, and it attaches to the published artifact. Change the artifact and the ranking stops describing the thing being measured.

There is a subtler case the rule is written to forbid. The temptation is not to modify a robot to cheat, but to modify one to make it testable — remove a hang, stub out a dependency, silence a noisy print. That produces a robot that passes, and a bridge that has never been shown to run the robot anyone actually has.

Staging is not modification. Copying a jar into a work directory, generating a bot directory beside it, or patching a generated boot script all leave the jar itself untouched, and the harness already works this way.

## Residual

Judgment holds the rule. A machine could checksum the collection before and after a run, but nothing does, and the constraint is as much about intent as about bytes: the failure mode is someone deciding a small edit is harmless.

**Promotion trigger:** a checksum of the collection recorded and re-verified around a sweep. Cheap to add and not yet added.

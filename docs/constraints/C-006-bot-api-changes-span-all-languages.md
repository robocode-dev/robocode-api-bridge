---
id: C-006
type: constraint
status: active
links: [ARCH-001]
title: A Tank Royale Bot API change lands in all four language implementations
source: The Tank Royale project's own multi-language Bot API contract
enforcement: human
provenance: inferred
reversal-cost: high
---

# C-006 — A Tank Royale Bot API change lands in all four language implementations

Tank Royale ships its Bot API for Java, .NET, Python, and TypeScript. A change to what the Bot API *means* — a semantic fix, a protocol version check, a guard against a state the API previously accepted — belongs in all four, not only in the one that surfaced the need.

## Why this appears in this repository at all

The bridge is a Java consumer of one of those four. When work here uncovers a defect in the Bot API rather than in the bridge, the fix belongs upstream, and the upstream obligation is to all four languages.

This has already happened. The null guard in the event queue's interruptible-event handling was found through this bridge and landed across the Java, .NET, and Python implementations together, alongside a protocol version compatibility check added to all four.

A fix applied only to Java is worse than no fix, because the defect stops being visible in the implementation most people exercise while remaining live in the other three, and the next person to hit it has no trail leading back to the analysis that explained it.

## Residual

**Residual:** judgment holds the whole rule. Nothing mechanical can tell that a Java-only change carried semantics the other implementations also need — that is a reading of what the change means, not of what it touches.

**Promotion trigger:** none identified in this repository. The obligation is upstream, and any enforcement belongs to the Tank Royale repository rather than to this one. Recorded here because work in this repository triggers it, and a rule that binds a change and lives only in someone's memory is exactly what the register exists to prevent.

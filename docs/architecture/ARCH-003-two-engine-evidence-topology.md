---
id: ARCH-003
type: architecture
status: active
links: [ARCH-001, CAP-007, C-007, PDR-001]
title: The two-engine evidence topology — where each tier runs and what isolates it
provenance: inferred
reversal-cost: high
---

# ARCH-003 — The two-engine evidence topology

Every claim this repository makes is comparative: the robot behaves *the same as it does on classic*. That makes classic Robocode part of the test apparatus rather than a reference document, and it shapes how evidence is produced.

`PDR-001` records why the apparatus has three tiers. This document records where they run.

## The three tiers, by what they need

| Tier | Engines | Processes | Runs in CI |
|---|---|---|---|
| Unit | none | the test JVM | yes |
| Conformance | both | test JVM, plus a bot JVM per participant and an embedded server | no |
| Sweep | both | a worker JVM per side, plus the server, booter, and bot JVMs beneath the Tank Royale worker | no |

The CI column is the whole reason the tiers are separate. Only the unit tier can run on a machine that has neither engine installed, and it is therefore the only tier that can gate a pull request. The other two need a classic Robocode installation, the Tank Royale runner jar, and the rumble collection — three things that live on a maintainer's machine.

## Two installations, two purposes

Classic Robocode appears twice and they are not interchangeable.

- **The installation** is a working Robocode home with `libs/` on the classpath. The Control API needs a real installation to run a battle.
- **The source repository** provides the conformance tier's test robots, built from its `robocode.tests.robots` module.

The conformance tier needs both. A checkout with neither present skips rather than fails, so the repository still builds for someone who only wants to compile the adapter.

## Isolation, and why it is not optional

A rumble jar is untrusted code with a two-decade history of ignoring the rules. Robots hang, spawn threads that never die, and hold JVMs open past the end of the battle. The engines make this worse in opposite ways: classic leaves non-daemon threads behind after a battle completes, and the Tank Royale runner registers a shutdown hook that can itself hang when a bot process refuses to die.

The apparatus therefore assumes nothing shuts down cleanly:

- **A worker JVM per battle per side.** A robot that takes down its worker takes down nothing else, and the orchestrator records the failure.
- **Process trees, not processes.** A timeout kills the worker together with the server, booter, and bot JVMs beneath it, because killing the parent alone orphans the rest.
- **Forced exit.** Workers halt the JVM rather than returning from `main`, with a watchdog that halts anyway if a shutdown hook has not finished in time.
- **Per-instance output.** Bot directories are duplicated so two instances of the same robot cannot interleave into one log file, which is what makes the exception signatures in `C-004` attributable.

The conformance tier inherits all of this. It is a smaller, faster arrangement of the same topology, running purpose-built test robots instead of rumble jars — and its robots come from classic's own suite, which means they are trustworthy in a way rumble jars are not, but the isolation stays because the surrounding machinery is identical.

## What is expensive to change here

The choice to drive both engines through their public APIs — the Control API on the classic side, the Battle Runner API on the Tank Royale side — rather than instrumenting either engine. It costs process overhead and rules out sharing state between the sides, but it means the apparatus measures the engines as they ship. An instrumented engine would measure a build no robot will ever run against, which for a project whose entire claim is fidelity would be measuring the wrong thing.

The asymmetry that follows: classic can be made deterministic through a seed, and Tank Royale cannot. `AN-002` records what that costs and which comparisons it rules out.

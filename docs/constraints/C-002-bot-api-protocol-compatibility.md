---
id: C-002
type: constraint
status: active
links: [CAP-001, ARCH-001]
title: The Bot API must be protocol-compatible with the server embedded in the runner
source: compat-test/README.md prerequisites, and the incident recorded in AN-001
enforcement: human
provenance: inferred
reversal-cost: high
---

# C-002 — The Bot API must be protocol-compatible with the server embedded in the runner

The bridge links one version of the Tank Royale Bot API; battles run against a server embedded in the Tank Royale runner jar. The two speak a versioned protocol, and a mismatched pairing is not a build error — it is a battle in which the bots never act.

## Why this is a constraint rather than a build detail

The failure is silent and looks like a bridge defect. Under an incompatible pairing the robots sit idle and score zero, which reads exactly like a bridge that cannot drive its robots.

A stricter form of the same rule: never fall back to a Bot API version merely because it resolves. Version 0.33.1 resolves cleanly and drops deferred same-priority events from its queue, so a bot that calls a blocking method inside `onScannedRobot` loses every other scan event. Radar locks break, mutually blind bots stall the round, and the scores that result are wrong in a way no amount of averaging reveals. `ADR-001` records the upgrade away from it.

## Residual

Nothing checks the pairing. The Bot API version is asserted in prose across the module build scripts and the harness README, and the server version is embedded inside the runner jar where no build step reads it.

**Residual:** judgment holds the whole rule. Whoever changes either version confirms the pairing by running a battle and observing that the bots act. The cost of missing it is a sweep whose every row is wrong in the same direction, which is expensive precisely because it looks like a real result.

**Promotion trigger:** a check that reads the protocol version from the linked Bot API and from the runner jar's embedded server and compares them. That is mechanically possible today and simply does not exist.

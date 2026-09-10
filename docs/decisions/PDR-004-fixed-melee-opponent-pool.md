---
id: PDR-004
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-005, CAP-007, C-003, P-001]
title: Measure melee subjects against a pinned fixed opponent pool
---

# PDR-004 — Measure melee subjects against a pinned fixed opponent pool

## Decision

The official melee parity case uses the subject robot plus nine opponents selected from a tracked twelve-jar pool. The pool is pinned by jar name and SHA-256 in `compat-test/melee-opponents.json`; selection is sorted, excludes the subject when it is in the pool, and takes the first nine remaining jars. Each registry setup records both the pool and the nine opponents selected for that subject.

## Rationale

Melee parity should exercise interaction with other robots rather than turn every subject into a ten-copy self-play battle. A pinned pool keeps the comparison repeatable across checkpoints and bridge repairs without silently changing the opponents. Excluding the subject avoids accidental self-play while retaining the official ten-participant shape.

## Consequences

The collection remains read-only, and a missing or hash-mismatched pool jar blocks the run before evidence is recorded. The selected opponent list is part of each observation's setup, so a later change to the pool produces a visibly different parity case rather than replacing history.

---
id: PDR-004
type: decision
status: verified
author: agent
accepted-by: [Flemming N. Larsen]
links: [CAP-005, CAP-007, C-003, P-001]
title: Measure melee subjects against a pinned fixed opponent pool
---

# PDR-004 — Measure melee subjects against a pinned fixed opponent pool

## Decision

The official melee parity case uses the subject robot plus nine opponents selected from a tracked twelve-jar pool. The pool is pinned by jar name and SHA-256 in `compat-test/melee-opponents.json`; it was selected from subjects with existing `PASS` observations in the roborumble registry that are also present in the melee collection. Selection is sorted, excludes the subject when it is in the pool, and takes the first nine remaining jars. Each registry setup records both the pool and the nine opponents selected for that subject.

## Approval

Flemming N. Larsen explicitly approved preserving this pinned pool for CH-016 in the 2026-09-10 collaboration.

## Rationale

Melee parity should exercise interaction with other robots rather than turn every subject into a ten-copy self-play battle. A pinned pool keeps the comparison repeatable across checkpoints and bridge repairs without silently changing the opponents. Excluding the subject avoids accidental self-play while retaining the official ten-participant shape.

## Consequences

The collection remains read-only, and a missing or hash-mismatched pool jar blocks the run before evidence is recorded. The selected opponent list is part of each observation's setup, so a later change to the pool produces a visibly different parity case rather than replacing history.

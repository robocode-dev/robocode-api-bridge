---
id: OQ-007
type: open-question
status: active
links: [CH-016]
title: CH-016 open questions
---

# Open questions

## OQ-007-1 — How should CH-016 handle opponent failures in the pinned melee pool?

The first 17 official melee observations show recurring classic-side failures from `amk.ChumbaMini` and `amk.ChumbaWumba`, both members of the PDR-004 pool. `AN-015` diagnoses the resulting subject observations as fixture-contaminated and separately names a likely bridge stream-limit gap.

Answer: preserve PDR-004's pinned pool. CH-016 will retain every original observation, classify opponent-originated failures explicitly, and fix bridge-owned causes with focused retests. The harness must not rewrite or replace the read-only opponent jars, and a later setup change would require a separate decision and visibly distinct registry observations.

Resolved by Flemming N. Larsen in the 2026-09-10 CH-016 collaboration.

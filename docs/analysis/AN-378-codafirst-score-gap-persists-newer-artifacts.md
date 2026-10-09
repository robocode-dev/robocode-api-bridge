---
id: AN-378
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-102]
title: CodaFirst's large score gap persists with newer matched artifacts
provenance: inferred
reversal-cost: low
---

# AN-378 — CodaFirst's large score gap persists with newer matched artifacts

## Risk investigated

Whether `AD.CodaFirst_1.1.jar`'s previously confirmed Tank Royale score deficit persists under newer matched artifacts, and whether the current measurement identifies its behavioral cause.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/AD.CodaFirst_1.1.jar`, whose previous status was `CONFIRMED (score)`. The read-only subject jar has SHA-256 `140825976d19ac8cbbf3a92040a9bc8568acb15933f6888cd0adce705497421f`; the run manifest records the selected opponent jar and hash. The five-pair confirmation `8ec3738de55d89e1` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `b79eed85b7c70bbd5b003af9e7f5c14011ca4132`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The run used the locally built bridge, wrapper, Bot API 1.4.0, and runner jars identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The eligible population was one named robot with its official two-participant setup. All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry's 25% threshold classifies score divergence; the measured score is a quality observation rather than a deterministic acceptance proof.

## What was tried

Classic averaged 12,830.6 points and Tank Royale averaged 7,133.0 points, for a −44.38% mean delta. The five pair deltas were −43.4%, −45.8%, −46.7%, −42.9%, and −43.1%. Both engines completed all pairs without runtime errors, and no skipped-turn events were recorded. The registry status remains `CONFIRMED (score)` with no bridge-only error signatures.

The preceding five-pair confirmation `1085f87da4e7117f` on 2026-10-06 had a −51.14% mean delta on an older bridge and Tank Royale commit pair. The current result shows that the large deficit persists under newer artifacts, at a smaller magnitude. `AN-102` records the earlier bytecode and score-formula review: score weights matched, while its fixed-command trace did not control starting positions and headings, leaving the behavioral cause unresolved.

## What was not pursued

The score gap was not assigned to the bridge, Tank Royale, or robot behavior from aggregate scores alone. The earlier uncontrolled trace was not treated as a causal explanation, and no code or rumble-jar change was made without a controlled behavioral trace.

## Finding

CodaFirst's large Tank Royale score deficit remains confirmed under the newer matched artifacts, with no runtime errors or skipped-turn events. The deficit's magnitude decreased from −51.14% to −44.38%, but the cause remains unresolved. A controlled trace of CodaFirst's perceived scan, heading, aim, and movement state is still needed to locate the divergence; this observation does not establish which component owns it.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/ArchAlpha.ArchimedesAlpha_1.0.jar`; the intervening AIR.iRobot and And.BasicSurfer rows are `PASS`.

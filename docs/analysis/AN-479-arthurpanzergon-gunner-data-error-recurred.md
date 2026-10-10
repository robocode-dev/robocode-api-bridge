---
id: AN-479
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: ArthurPanzergon's missing gunner data error recurs
provenance: inferred
reversal-cost: low
---

# AN-479 — ArthurPanzergon's missing gunner data error recurs

## Risk investigated

Whether `ghent.ArthurPanzergon_1.0.0.jar`'s historical Tank Royale data-file error recurs under current matched artifacts, and whether the run produces a usable paired score.

## Evidence boundary

The read-only subject jar has SHA-256 `90f35d34d2781d6549f1094c87f72fbf1413e91d73f1be75fd98f5f88b635556`. The official confirmation attempt `70d54fe2622e752d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `acc92002dd0ce91cde5c6770f081f258403042af`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

The registry records one attempt and zero valid paired samples; its status is `DISCREPANCY (errors)`. The raw current Classic result file reports a completed battle with participant scores of 4,317 and 3,860, but its console contains `EOFException`, `OptionalDataException`, and `StreamCorruptedException` from `ghent.modules.artillery.Gunner.loadStats`, along with skipped turns. Both current Tank Royale bot logs show `FileNotFoundException` for `ArthurPanzergon.data/gunner.dat` from the same `Gunner.loadStats` path; the worker produced no result. Skipped-turn telemetry is incomplete, and no score delta is available.

## What was tried

The earlier observation `dfa7800f0f04cab3` recorded Classic scores of 0 and 0 and no Tank Royale score. Tank Royale reported the same `java.io.FileNotFoundException` signature from `ghent.modules.artillery.Gunner.loadStats`, with a missing `gunner.dat` in each bot work directory. The current run again records that signature and has no valid paired sample, so it does not establish a score comparison.

## What was not pursued

The evidence does not establish why the robot expects `gunner.dat` or whether the Classic read errors and Tank Royale missing-file errors share a cause. No source or rumble-jar change was made, and no attribution to the bridge was attempted.

## Finding

ArthurPanzergon's Tank Royale missing-data-file error recurred under current matched artifacts. The current registry status is `DISCREPANCY (errors)` with one attempt and zero valid paired samples; the raw Classic result has scores but also repeated gunner-stat read exceptions and skipped turns, so it does not support a score-gap classification.

## M-006 handoff

Record `roborumble/ghent.ArthurPanzergon_1.0.0.jar` as `DISCREPANCY (errors)`. Skip `roborumble/gimp.GimpBot_0.1.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/gio.RealGioBot_1.0.jar` (`DISCREPANCY (no score)`).

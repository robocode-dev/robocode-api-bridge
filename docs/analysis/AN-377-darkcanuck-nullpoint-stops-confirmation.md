---
id: AN-377
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: DarkCanuck's Tank Royale null point blocks current confirmation
provenance: inferred
reversal-cost: low
---

# AN-377 — DarkCanuck's Tank Royale null point blocks current confirmation

## Risk investigated

Whether `darkcanuck.B26354_1.06.jar`'s earlier outcome discrepancy persists under the latest artifacts, and whether its current failed attempt identifies a bridge defect.

## Evidence boundary

The population is this single eligible M-006 registry row, `meleerumble/darkcanuck.B26354_1.06.jar`, selected because its latest prior status was `DISCREPANCY (outcome)`. The subject jar is read-only and has SHA-256 `9dec0de94deb2d91527d98bfdfc56b76eb65734778e6096b23724d7480f8e58d`; the run manifest records the selected opponent jars and hashes. The attempt `9f3a2c9cbfc35a90` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `b79eed85b7c70bbd5b003af9e7f5c14011ca4132`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The local bridge API and wrapper jars and Tank Royale API 1.4.0 and runner jar are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with 10 participants, 35 rounds, and a 1000×1000 arena; this result does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The confirmation requested five attempts; the first attempt stopped before producing a score sample, so there were zero completed pairs and telemetry was incomplete. No sample was discarded. This result is one failed attempt on one subject and opponent setup, not a population-wide estimate. No confidence interval or significance test applies.

## What was tried

The current attempt recorded `NullPointerException` with origin `darkcanuck.m.a` and produced no Classic or Tank Royale score. The registry contains a prior robot-owned diagnosis `robot-null-point-in-darkcanuck-m-a`; the latest observation does not independently inspect or prove the underlying source-level cause.

The preceding observation `326a1db9fec4d3c7` on 2026-10-07 also had `DISCREPANCY (outcome)`: Classic scored 113,596 points while Tank Royale produced no score and recorded four errors. An earlier observation `e2187be09c14425c` on 2026-10-06 recorded a −2.3% score delta. These observations show that the outcome varies across runs; they do not establish that the robot-side failure is a bridge defect.

## What was not pursued

The no-score result was not converted into a score comparison, and the registry's robot-owned diagnosis was not treated as proof of a bridge issue. No bridge or rumble-jar change was made from this failed attempt.

## Finding

DarkCanuck's current confirmation did not produce the requested five score pairs because the first attempt stopped at a robot-origin `NullPointerException`. This does not confirm or clear the earlier score discrepancy and does not identify a bridge defect. Further analysis of the robot-owned failure would be needed before another score confirmation could complete.

## M-006 handoff

Continue in registry order with `roborumble/AD.CodaFirst_1.1.jar` (`CONFIRMED (score)`).

---
id: AN-385
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-108]
title: Garm's worker initialization failure recurs on newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-385 — Garm's worker initialization failure recurs on newer artifacts

## Risk investigated

Whether `Krabb.sliNk.Garm_0.9u.jar`'s earlier Tank Royale worker failure persists under newer matched artifacts and whether the current failure identifies its owner.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/Krabb.sliNk.Garm_0.9u.jar`, previously recorded as `DISCREPANCY (outcome)`. The read-only subject jar has SHA-256 `c895d29c6c2b83c9441a643deca71f43405cb9907a2d4393832a84305967fe2d`; the run manifest records the selected opponent jar and hash. The current attempt `67f68575c5e513be` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `4f3f1774553d68045dc085d5dd0917fad5b239f4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable used by the Tank Royale worker.

The confirmation requested five attempts; its first attempt stopped before producing a score sample. There were zero comparable pairs and telemetry was incomplete. No sample was discarded. This is one failed attempt on one subject and setup, not a score estimate; no confidence interval or significance test applies.

## What was tried

The current attempt recorded `NoClassDefFoundError` with unknown origin and produced no score in either engine. The available error text is not present in this attempt's observation, so it cannot confirm the exact initialization failure location.

The preceding observation `2e935b12cefc0443` on 2026-10-06 completed Classic at 4,820 points but produced no Tank Royale result. Its worker log reported `NoClassDefFoundError: Could not initialize class java.lang.StackTraceElement$HashedModules` and a subsequent uncaught-exception-handler failure. `AN-108` records that the earlier run used Temurin JDK 25.0.1, but does not contain the underlying initializer exception. The current artifact pair still does not preserve the original error needed to assign ownership.

## What was not pursued

The `bridge_only_signatures` field was not treated as proof of bridge ownership because the origin is unknown and the worker log omits the underlying initializer exception. No bridge or rumble-jar change was made from this incomplete attempt.

## Finding

Garm's Tank Royale worker failure persists under newer matched artifacts, but the current attempt provides no score and does not reveal the underlying initialization error. The failure remains unassigned to the bridge, robot, or runtime. The evidence does not justify a code change.

## M-006 handoff

Continue in registry order with `roborumble/Legend.Qetro_1.6.jar` (`DISCREPANCY (score)`).

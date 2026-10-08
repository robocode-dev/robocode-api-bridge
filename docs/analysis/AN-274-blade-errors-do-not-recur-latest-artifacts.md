---
id: AN-274
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Blade's pattern-gun error and no-score outcome do not recur with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-274 — Blade's pattern-gun error and no-score outcome do not recur with the latest artifacts

## Risk investigated

Whether `cw.megas.Blade_0.8.jar`'s historical Tank Royale no-score outcome and pattern-gun index error recur under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `4a50333c16fd0bdc02aca8c152e7017ede5c8e763ceaba42319867a5b256f830`. The official observation `c61c09256a05f53b` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ad1b8b818512d466fdf419caf51c0887e86405d3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 5,091 and Tank Royale scored 5,545, a +8.9% delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

AN-144's previous official pair had no Tank Royale score and 10 errors, including nine `StringIndexOutOfBoundsException` messages in the robot's pattern-gun path. The latest pair did not reproduce those errors or the no-score outcome. AN-144's source inspection identified the old unchecked string-index path in the robot; the current clean run does not explain why the failure stopped occurring.

## Finding

Blade passes the current score and error checks under the latest matched artifacts. Its prior robot-owned pattern-gun failure does not recur in this pair; the reason it no longer occurs remains unknown. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cw.megas.GhostShell_GT.jar` (`DISCREPANCY (outcome)`).

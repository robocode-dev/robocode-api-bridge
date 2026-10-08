---
id: AN-273
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Nene's Tank Royale zero-score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-273 — Nene's Tank Royale zero-score gap persists with the latest artifacts

## Risk investigated

Whether `cs.Nene_1.0.5.jar`'s confirmed Tank Royale zero-score result persists under the latest matched artifacts and whether the available evidence identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `9615be8f8f2bdf4f42a38f74b4b81d0944e86f06311582915a78af06d529e856`. The official five-pair confirmation `03d40c32c811f41e` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ad1b8b818512d466fdf419caf51c0887e86405d3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic mean score was 4,721.2, while Tank Royale scored 0 in all five pairs. Every delta was −100%. Neither engine reported errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status remains `CONFIRMED (score)`.

AN-142's earlier confirmation also had a −100% mean delta, with a Classic mean score of 4,602 and zero Tank Royale score in all five pairs. The latest matched artifacts reproduce the same result.

## Finding

Nene's all-five Tank Royale zero-score discrepancy persists under the latest matched artifacts. The current run has no errors or captured skipped turns. AN-142's bundled-source leads remain unconfirmed explanations of the score difference; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/csp.Eagle_3.30.jar`, whose current status is `MATCHED (score noise)`. Continue with `roborumble/cw.megas.Blade_0.8.jar` (`DISCREPANCY (outcome)`).

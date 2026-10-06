---
id: AN-119
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Cabbage's large positive score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-119 — Cabbage's large positive score gap is confirmed across five runs

## Risk investigated

Whether blir.nano.Cabbage's historical score difference persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `blir.nano.Cabbage_R1.0.1.jar` has SHA-256 `6048d93ca6c18440d2442930806a4edb783d6bc3e7b019f324fad7aebaccb222`. The current official confirmation `5d86fd97b4543fb8` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `166ab9b9b68c526e49f88e0c70af73e255b15812`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 1,383.4 in Classic and 2,471.2 in Tank Royale, an +80.78% mean delta. The five pair deltas were +101.6%, +100.8%, +62.1%, +63.5%, and +75.9%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `14e36192003c6140` and `242021032b8974e3` reported score deltas of +111.9% and +84.3%, without errors. The current confirmation reproduces the direction and large magnitude of the score difference.

## Finding

Cabbage has a large confirmed current score gap with no runtime errors or captured skipped turns. The reason for the score difference remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/boe.Minerva_0.80.jar` (`DISCREPANCY (outcome)`).

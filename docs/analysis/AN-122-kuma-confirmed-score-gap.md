---
id: AN-122
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Kuma's large positive score gap is confirmed across five runs
provenance: inferred
reversal-cost: low
---

# AN-122 — Kuma's large positive score gap is confirmed across five runs

## Risk investigated

Whether bp.Kuma's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bp.Kuma_1.0.jar` has SHA-256 `2646c78e817ac3701fbd23306259844fdea377b82ac7748f8d3cf6ea36b4825e`. The current official confirmation `0c6594fecad7bc88` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `202718bf0471e1d31dbb2d55cfaba566519c4001`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 3,404.4 in Classic and 5,182.2 in Tank Royale, a +53.12% mean delta. The five pair deltas were +69.6%, +61.3%, +51.5%, +57.5%, and +25.7%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `a5efd143dba4df0f` and `85f7f94a56f0d1c2` reported score deltas of +91.6% and +84.3%, also without errors. The current confirmation reproduces the direction of the large score difference.

## Finding

Kuma has a large confirmed current score gap with no runtime errors or captured skipped turns. The reason for the difference remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/bts.wiki.RipCurl_0.9b.jar` (`DISCREPANCY (outcome)`).

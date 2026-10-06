---
id: AN-131
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Insomnia's historical score gap does not clear the five-run confirmation band
provenance: inferred
reversal-cost: low
---

# AN-131 — Insomnia's historical score gap does not clear the five-run confirmation band

## Risk investigated

Whether cb.nano.Insomnia's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `cb.nano.Insomnia_1.0.jar` has SHA-256 `dfaa0b7522185985faaaad2c8c8761b56ed6b2e31f9d256de0abbcf466863d30`. The current official confirmation `9212979d0d391d93` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `36389fb31c31bd976186020708fa580cdf791f30`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 2,297.2 in Classic and 2,463.2 in Tank Royale, an +8.68% mean delta. The five pair deltas were −5.4%, +15.6%, −9.2%, +33.5%, and +8.9%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `MATCHED (score noise)`.

Earlier observations `997241f811003c48` and `2b65230e01814b28` reported single-pair deltas of +14.0% and +41.0%, without errors. The current five-run mean is below the harness's 15-point confirmation band, so the historical large gap is not confirmed.

## Finding

Insomnia's current mean score difference is within the score-noise band, despite one +33.5% pair. No runtime errors or captured skipped turns occurred. The historical cause remains open because the measurements do not establish what produced the older +41.0% result. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/cbot.agile.Nibbler_0.2.jar` (`DISCREPANCY (errors)`).

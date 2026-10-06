---
id: AN-126
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Tirunculus's current score gap is confirmed at +24.28 percent
provenance: inferred
reversal-cost: low
---

# AN-126 — Tirunculus's current score gap is confirmed at +24.28 percent

## Risk investigated

Whether bwbaugh.nano.Tirunculus's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `bwbaugh.nano.Tirunculus_0.0.0a.jar` has SHA-256 `d1fcb6072a0110947df39ad0db284f0fa4f229e7ef0f0d2502553ca0b2acb7d4`. The current official confirmation `c65434b2428d5eb5` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `05ed9f57016b27d48a9b693658d61b249219c47e`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,852 in Classic and 13,485.4 in Tank Royale, a +24.28% mean delta. The five pair deltas were +25.0%, +25.6%, +20.9%, +24.8%, and +25.1%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)` because the mean exceeds the five-run 15-point confirmation band.

Earlier observations `b935555308fc01c3` and `065a07920f011446` reported larger deltas of +53.7% and +48.8%, without errors. The current gap is smaller but remains consistently positive across the five runs.

## Finding

Tirunculus retains a confirmed current score difference. The five-run mean is below the single-pair 25% screening threshold but above the confirmation band, and individual deltas cluster between +20.9% and +25.6%. No runtime errors or captured skipped turns occurred. The score cause remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/caimano.Furia_Ceca_0.22.jar` (`score-review`).

---
id: AN-133
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: UrChicken2's historical score gap is not confirmed in current repeats
provenance: inferred
reversal-cost: low
---

# AN-133 — UrChicken2's historical score gap is not confirmed in current repeats

## Risk investigated

Whether chickenfuego.UrChicken2's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `chickenfuego.UrChicken2_1.0.jar` has SHA-256 `a56a6d6bbeae72501cb86c35621436e91b31686db0785cd3151512452bdf818f`. The current official confirmation `ebca585aa7c5ee48` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `68a0c9a0aa5d83ca07ff6de46ebc5c7d1a2fa21b`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 6,062.8 in Classic and 5,949.4 in Tank Royale, a −1.38% mean delta. The pair deltas were +16.9%, −13.9%, −13.0%, +0.1%, and +3.0%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `MATCHED (score noise)`.

Earlier observations `ac7ec21a2a822b4f` and `728bbeb46f532bf2` reported deltas of −8.7% and −28.7%, without errors. The current five-run mean is within the harness's score-noise band and does not reproduce the earlier large negative difference.

## Finding

UrChicken2's historical score gap is not confirmed under current matched artifacts. Its five-run mean is near zero and the pairs vary in direction. No runtime errors or captured skipped turns occurred. The cause of the earlier −28.7% result remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/cli.WasteOfAmmo_1.0.jar` (`DISCREPANCY (no score)`).

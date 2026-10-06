---
id: AN-143
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Eagle's historical score gap is not reproduced in current repeats
provenance: inferred
reversal-cost: low
---

# AN-143 — Eagle's historical score gap is not reproduced in current repeats

## Risk investigated

Whether `csp.Eagle 3.30`'s historical Tank Royale score deficit persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `csp.Eagle_3.30.jar` has SHA-256 `8a09a1ad3e1e4eb01f84d98868d26db8c0e58b4900ca41ddc93ddf13a56fbcce`. The current official confirmation `29ac380d947bfd32` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `c9ca5caaf61770167bde6d2e1675626bd885e535`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, the score deltas were −1.7%, +6.9%, −2.3%, +7.9%, and +12.1%. Classic averaged 7,240 points and Tank Royale averaged 7,562.8, for a +4.58% mean delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `MATCHED (score noise)`.

Earlier observations `e0122d77dc924a71` and `f1344f1c2958947d` had score deltas of −37.0% and −25.4%, respectively, without runtime errors. Those runs used older bridge and Tank Royale artifacts. The historical score deficit does not reproduce with the current matched pair.

## Finding

The five-pair current result falls within the registry's score-noise band, with no runtime errors or skipped-turn telemetry. The older score discrepancy is not reproduced; this observation does not identify a cause for the earlier scores. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cw.megas.Blade_0.8.jar` (`DISCREPANCY (errors)`).

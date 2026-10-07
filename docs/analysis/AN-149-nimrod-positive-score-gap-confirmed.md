---
id: AN-149
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Nimrod's large positive score gap is confirmed across five pairs
provenance: inferred
reversal-cost: low
---

# AN-149 — Nimrod's large positive score gap is confirmed across five pairs

## Risk investigated

Whether Nimrod's historical Tank Royale score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `cx.mini.Nimrod_0.55.jar` has SHA-256 `9ccc75f18f1ea296fefe441150fd552e546989e1e42af4abdc578f297564a321`. The current official confirmation `5410b023fd3ba1c8` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `dc35e0ff7012b1ac9d36671dd7a32d0a601fbc2d`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Across five pairs, the score deltas were +130.4%, +134.8%, +148.9%, +129.5%, and +137.0%. Classic averaged 4,910.8 points and Tank Royale averaged 11,595.6, for a +136.12% mean delta. Both engines were error-free, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `CONFIRMED (score)`.

Earlier observations `13e1779cf8e10822` and `79c0ff02f62c13d6` also showed a large Tank Royale advantage: 11,172 versus 4,854 and 12,250 versus 4,878, respectively. Those runs used older bridge and Tank Royale artifacts.

## Finding

Nimrod's large positive score discrepancy is confirmed with current matched artifacts and is consistent with both earlier observations. No runtime errors or skipped turns were observed. This measurement does not establish the behavioral cause of the advantage. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cx.nano.Smog_2.6.jar` (`score-review`).

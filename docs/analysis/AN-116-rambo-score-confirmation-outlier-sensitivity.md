---
id: AN-116
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: RamboT's confirmed score gap depends on one unusually high-delta run
provenance: inferred
reversal-cost: low
---

# AN-116 — RamboT's confirmed score gap depends on one unusually high-delta run

## Risk investigated

Whether bbo.RamboT's score discrepancy persists across the official five-pair confirmation and whether the result is consistent across all pairs.

## Evidence boundary

The read-only subject jar `bbo.RamboT_0.3.jar` has SHA-256 `3c933f89750dfd882f03844f5fc83ae4bf36d5a6baca855251417b4a1457e6ed`. The current official confirmation `c468ff79790bb4bc` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `fa8756066e25c8f76f182f55f01b2ac72afd882d`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 9,328.4 in Classic and 11,221.2 in Tank Royale, a +20.74% mean delta. The five pair deltas were +11.1%, +9.7%, +10.7%, +22.8%, and +49.4%. The first four averaged +13.58%; the fifth pair raises the five-run mean above the harness's 15-point confirmation band. All five pairs completed without errors or bridge-only failures, and all five captured empty skipped-turn event lists. The registry status is `CONFIRMED (score)`.

Earlier observations `df99f8a1d6c00d7e` and `aca48d105b06bcde` recorded single-pair deltas of +22.4% and +42.8%, also without errors.

## Finding

RamboT meets the harness's five-run score-confirmation rule, but the classification is sensitive to one unusually high-delta pair: without that pair, the other four average below the 15-point band. The repeated positive direction and earlier score gap support continued review, while the size of the current gap remains variable. The cause remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bing2.Melody_1.3.1.jar` (`score-review`).

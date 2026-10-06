---
id: AN-138
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Bulldozer's positive score gap is confirmed at a smaller magnitude
provenance: inferred
reversal-cost: low
---

# AN-138 — Bulldozer's positive score gap is confirmed at a smaller magnitude

## Risk investigated

Whether conscience.Bulldozer's historical score discrepancy persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `conscience.Bulldozer_1.0a.jar` has SHA-256 `f822506ece11632ca21436cfede847408f696140c474ae3b35e25187fbe27875`. The current official confirmation `018a86452c5c224d` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `e85df8e90042b29e3fed876fa23444cbe2f5c716`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,946.4 in Classic and 14,074 in Tank Royale, a +28.62% mean delta. The five pair deltas were +31.0%, +32.5%, +30.2%, +25.6%, and +23.8%. All five pairs completed without errors or bridge-only failures, and each Tank Royale run captured an empty skipped-turn event list. The registry status is `CONFIRMED (score)`.

Earlier observations `34ee10313f50e735` and `d7ba964e4e43901c` reported larger deltas of +51.2% and +51.4%, without errors. The current score gap is smaller but remains positive across all five pairs and above the single-pair screening threshold.

## Finding

Bulldozer retains a confirmed positive score gap, with the current five-run mean below its earlier single-pair differences. No runtime errors or captured skipped turns occurred. The reason for the score difference remains open; no bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/conscience.Idem_1.0a.jar` (`score-review`).

---
id: AN-180
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Dragonbyte Neutrino's positive score gap clears the confirmation band
provenance: inferred
reversal-cost: low
---

# AN-180 — Dragonbyte Neutrino's positive score gap clears the confirmation band

## Risk investigated

Whether `dragonbyte.Neutrino_4.jar`'s historical Tank Royale score advantage persists across five official pairs with current matched artifacts.

## Evidence boundary

The read-only subject jar `dragonbyte.Neutrino_4.jar` has SHA-256 `c7c7c279979129da96e848c140b138ace728ccfdb96a5de7d90ed0f1d6e0a65e`. The official confirmation `6651d934a7211124` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `3839a2bcf7410742e00e1609d83acf376eae7ece`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were +26.1%, +3.7%, +24.1%, +7.5%, and +17.5%. Classic averaged 1,827.2 points and Tank Royale averaged 2,103.2, for a +15.78% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `CONFIRMED (score)`.

The 25% threshold is the single-pair review trigger. The five-pair `--confirm-score` status uses the 15-point mean-gap band, so the +15.78% mean is confirmed even though it is below 25%. The manifest records the single-pair threshold.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `921a316d393ed969` recorded a single-pair Classic score of 1,515 and Tank Royale score of 2,219, a +46.5% delta. The current five-pair mean is a smaller Tank Royale advantage that remains above the confirmation band.

## Finding

Dragonbyte Neutrino's Tank Royale score advantage is confirmed with current matched artifacts. All five pairs completed without errors and all Tank Royale skipped-turn captures were empty. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/drm.CobraBora_1.12.jar` (`score-review`).

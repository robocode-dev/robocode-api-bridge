---
id: AN-181
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: CobraBora's positive score gap is confirmed
provenance: inferred
reversal-cost: low
---

# AN-181 — CobraBora's positive score gap is confirmed

## Risk investigated

Whether `drm.CobraBora_1.12.jar`'s historical Tank Royale score advantage persists across five official pairs with current matched artifacts.

## Evidence boundary

The read-only subject jar `drm.CobraBora_1.12.jar` has SHA-256 `dcb1af76fd7509e2eeef13fb80a8b45a6184cd47c025315df67e000d37668b1e`. The official confirmation `d182f0669de56c32` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `d10c4e92c2b9b5d44542a7a9b7d4b55dcc9c847e`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were +38.8%, +49.3%, +33.8%, +43.4%, and +34.6%. Classic averaged 6,864.8 points and Tank Royale averaged 9,608.4, for a +39.98% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `CONFIRMED (score)`.

The 25% threshold is the single-pair review trigger. The five-pair `--confirm-score` status uses the 15-point mean-gap band, so the +39.98% mean is confirmed under both the single-pair screen and the five-pair confirmation band. The manifest records the single-pair threshold.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `54f1d01d2403ba18` recorded a single-pair Classic score of 6,155 and Tank Royale score of 8,467, a +37.6% delta. The current five-pair result is consistent with a Tank Royale score advantage of similar magnitude.

## Finding

CobraBora's Tank Royale score advantage is confirmed with current matched artifacts. All five pairs completed without errors and all skipped-turn captures were empty. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/aaa.r.ScalarR_0.005g.047.jar` (`DISCREPANCY (errors)`).

---
id: AN-176
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Eve's Classic score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-176 — Eve's Classic score advantage is confirmed

## Risk investigated

Whether Eve's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dmp.nano.Eve_3.41.jar` has SHA-256 `edd8e3e4a88e6abb36bba0d2241c3b7d03dda39619f4f9264e4b4757f1eb2da9`. The official confirmation `02cf19f8c7dcc852` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `a8daf66c91ed8cad73406e1c2b57ec71f91fd92b`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −57.0%, −50.2%, −41.7%, −65.1%, and −67.0%. Classic averaged 2,968.6 points and Tank Royale averaged 1,264.0, for a −56.2% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `CONFIRMED (score)`; the mean exceeds the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `3931602ad25b6a06` recorded a single-pair Classic score of 3,596 and Tank Royale score of 1,857, a −48.4% delta. The current five-pair mean confirms the same direction and a larger gap.

## Finding

Eve's Classic score advantage is confirmed with current matched artifacts and agrees in direction with the earlier observation. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/doka.ShinigamiKNN_1.0.jar` (`DISCREPANCY (outcome)`).

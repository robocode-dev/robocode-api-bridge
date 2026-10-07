---
id: AN-172
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: NanoSkunk10's historical score gap is not confirmed
provenance: inferred
reversal-cost: low
---

# AN-172 — NanoSkunk10's historical score gap is not confirmed

## Risk investigated

Whether NanoSkunk10's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `djdjdj.NanoSkunk10_1.0.jar` has SHA-256 `fc647073a6fdfbbdc04f2f964ccb7bd7210180d1d11b1b6ecba314d480e3f944`. The official confirmation `fea8fc4f9b3a1168` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `413aba88f6e8dd6445e26c2ffa329254d4750f70`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −24.0%, −3.6%, −13.0%, −2.1%, and −10.6%. Classic averaged 6,525.8 points and Tank Royale averaged 5,821.2, for a −10.66% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `MATCHED (score noise)`; the mean is below the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `584c9f0476f1fbd5` recorded a single-pair Classic score of 7,024 and Tank Royale score of 5,204, a −25.9% delta. That single-pair score discrepancy is not confirmed by the current five-pair mean.

## Finding

NanoSkunk10's historical Classic score advantage is not confirmed with current matched artifacts. The five-pair mean remains a modest Classic advantage within the score-noise band, with no runtime errors or skipped turns observed in Tank Royale. The measurements do not establish the cause of the earlier larger gap. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dmh.robocode.robot.BlackDeath_9.2.jar` (`DISCREPANCY (outcome)`).

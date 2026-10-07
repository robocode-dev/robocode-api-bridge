---
id: AN-170
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: BlindSquirl's historical score deficit is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-170 — BlindSquirl's historical score deficit is not reproduced

## Risk investigated

Whether BlindSquirl's historical Tank Royale score deficit persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `dittman.BlindSquirl_Retired.jar` has SHA-256 `137363a80d70457dbb26f256a45f1e52814c2818e21bf7834800607eb193ab92`. The official confirmation `1b35acfd8039eda4` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `cefa0342790511bb7681e572e39c087eb67774ca`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were +3.8%, −1.0%, +5.1%, +10.7%, and +8.9%. Classic averaged 5,122.6 points and Tank Royale averaged 5,399.6, for a +5.5% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `MATCHED (score noise)`; the mean is below the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `bd4fb0e12b986401` recorded a Classic score of 5,456 and Tank Royale score of 1,908, a −65.0% delta. That single-pair deficit is not reproduced by the current five-pair mean.

## Finding

BlindSquirl's historical Tank Royale score deficit is not reproduced with current matched artifacts; the current mean is a small Tank Royale advantage within the score-noise band. No runtime errors or skipped turns were observed. The measurements do not identify the cause of the earlier result. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/divineomega.DivineBot_1.9.5.jar` (`DISCREPANCY (errors)`).

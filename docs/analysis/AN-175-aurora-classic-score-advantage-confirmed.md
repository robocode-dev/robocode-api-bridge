---
id: AN-175
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Aurora's Classic score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-175 — Aurora's Classic score advantage is confirmed

## Risk investigated

Whether Aurora's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dmp.micro.Aurora_1.41.jar` has SHA-256 `0550ff35cc6fd581a7469d3378ccf17ae5f3fb00888c56858ed3b54f515366e9`. The official confirmation `9470ea89146b4bb5` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `fadc295354e275b41f0bd071a5a38a0279870017`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −35.4%, −34.4%, −39.8%, −36.8%, and −38.7%. Classic averaged 4,457.2 points and Tank Royale averaged 2,806.6, for a −37.02% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `CONFIRMED (score)`; the mean exceeds the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `05310022c6d6c60c` recorded a single-pair Classic score of 4,331 and Tank Royale score of 2,723, a −37.1% delta. The current five-pair mean is consistent with it.

## Finding

Aurora's Classic score advantage is confirmed with current matched artifacts and agrees with the earlier observation. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dmp.nano.Eve_3.41.jar` (`score-review`).

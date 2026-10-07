---
id: AN-174
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: BlueBerry's Classic score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-174 — BlueBerry's Classic score advantage is confirmed

## Risk investigated

Whether BlueBerry's historical Classic score advantage persists with current matched artifacts.

## Evidence boundary

The read-only subject jar `dmh.robocode.robot.BlueBerry_0.5.jar` has SHA-256 `3739fb06f3f805c42e7afccd8b0cc17e02916021789c6e504c22688a1ae160fb`. The official confirmation `4f7f98156d9a57ca` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `a22f6ef6187d6fa489c74f4fac982ce10880c268`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −41.1%, −39.8%, −42.3%, −38.9%, and −30.6%. Classic averaged 5,717.8 points and Tank Royale averaged 3,506.0, for a −38.54% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `CONFIRMED (score)`; the mean exceeds the 15-point five-pair confirmation band. The 25% threshold is the single-pair review trigger.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `41ff60750d06c66c` recorded a single-pair Classic score of 5,598 and Tank Royale score of 3,366, a −39.9% delta. The current five-pair mean is consistent with it.

## Finding

BlueBerry's Classic score advantage is confirmed with current matched artifacts and agrees with the earlier observation. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dmp.micro.Aurora_1.41.jar` (`score-review`).

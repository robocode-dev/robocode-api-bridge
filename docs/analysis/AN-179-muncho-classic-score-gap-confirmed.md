---
id: AN-179
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Muncho's Classic score advantage is confirmed
provenance: inferred
reversal-cost: low
---

# AN-179 — Muncho's Classic score advantage is confirmed

## Risk investigated

Whether Muncho's historical Classic score advantage persists across five official pairs with current matched artifacts.

## Evidence boundary

The read-only subject jar `donjezza.Muncho_1.0.jar` has SHA-256 `ca1863e988017d98a771812f983352f9b22daf33a195301f05d410b2fa26673d`. The official confirmation `1bc442cdb44483c2` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `3839a2bcf7410742e00e1609d83acf376eae7ece`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −19.5%, −19.9%, −20.9%, −24.9%, and −23.9%. Classic averaged 6,753.4 points and Tank Royale averaged 5,278.4, for a −21.82% mean delta. Both engines completed all five pairs without errors. Tank Royale captured empty skipped-turn event lists for attempts 1, 2, 3, and 5; attempt 4 telemetry was incomplete. The registry status is `CONFIRMED (score)`.

The 25% threshold is the single-pair review trigger. The five-pair `--confirm-score` status uses the 15-point mean-gap band, so the −21.82% mean is confirmed even though it is below 25%. The manifest records the single-pair threshold.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `5621bc7c9b63b9bc` recorded a single-pair Classic score of 6,734 and Tank Royale score of 4,848, a −28.0% delta. The current five-pair mean confirms a smaller Classic advantage.

## Finding

Muncho's Classic score advantage is confirmed with current matched artifacts. Its −21.82% five-pair mean is below the 25% single-pair review threshold and above the 15-point five-pair confirmation band. No runtime errors occurred. Four Tank Royale skipped-turn captures were empty, and one was incomplete. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dragonbyte.Neutrino_4.jar` (`score-review`).

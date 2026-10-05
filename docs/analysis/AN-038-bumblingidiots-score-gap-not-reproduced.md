---
id: AN-038
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CRIT-005, AN-002, AN-020]
title: BumblingIdiots' earlier higher score is not confirmed by five current pairs
provenance: inferred
reversal-cost: low
---

# AN-038 — BumblingIdiots' earlier higher score is not confirmed by five current pairs

## Question

Does the earlier `teamrumble/mskwik.BumblingIdiots_1.0.jar` score gap persist with the current matched bridge and Tank Royale artifacts?

## Evidence boundary

The collection jar has SHA-256 `0a51bc9d9ca2c6c9db9d7f748d601a80a753cd1092ad04c3c801a4839a1c3b45`; its selected robot is `mskwik.Idiot [1.0]`. Registry observation `de2d4c9c3132be8f` records five official pairs on a 1200×1200 field, 10 rounds, and two teams, with Classic Robocode 1.11.1. The bridge commit was `24012fdf7f3d576bba14e09716d208ce56cdc54a`. Runner 1.4.0 SHA-256 was `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`; Bot API 1.4.0 SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`. Bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`. The Tank Royale checkout was at `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`; the tested artifacts were built from unchanged production source at `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`.

The previous observation `982cb1f3bee1097c` scored 19,390 in Classic and 24,884 in Tank Royale, a +28.3% single-pair difference; it used Tank Royale 1.2.0 artifacts. The five current paired deltas are −16.5%, −16.0%, −14.5%, −7.9%, and −14.8%. Mean Classic score is 19,713.6 and mean Tank Royale score is 16,964.4, a −13.94% mean. The repeated-score rule classifies this as `MATCHED (score noise)`; all five samples produced scores and the confirmation recorded no bridge-only exception signatures.

## Skipped-turn telemetry

The five Tank Royale samples recorded 0, 7, 6, 10, and 0 skipped-turn events respectively. Every captured event was on round 1, turn 1, and telemetry capture completed for every sample. Both no-skip samples had score deltas near −15%, while the ten-skip sample had the smallest gap at −7.9%; these records do not explain the score direction.

## Finding

The historical Tank Royale score advantage does not reproduce on the current artifact pair. The current mean is close to, but does not cross, the repeated-score threshold; the old and new measurements use different Tank Royale artifacts and point in opposite directions, so they do not isolate why the observed scores moved. No bridge or Tank Royale score cause is assigned, and no diagnosis event is added.

## M-006 handoff

Keep this subject at `MATCHED (score noise)` on the current five-pair confirmation. Retain the prior score observation and its provenance; do not treat its single +28.3% result as a current confirmed gap. No source change is indicated by this retest.

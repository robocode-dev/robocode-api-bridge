---
id: AN-037
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CRIT-005, AN-020, AN-021]
title: AresHaikuTeam's earlier score gap does not reproduce in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-037 — AresHaikuTeam's earlier score gap does not reproduce in five current pairs

## Question

Does the earlier `teamrumble/ms.AresHaikuTeam_0.3.jar` score gap persist with the current matched bridge and Tank Royale artifacts?

## Evidence boundary

The collection jar has SHA-256 `8d42c60a8d2a9872b825d481b62d751d8e1d1243eb74d895734f85152677b987`; its selected robot is `ms.AresHaiku 0.3`. Registry observation `557db1e6c0832ee2` records five official pairs on a 1200×1200 field, 10 rounds, and two teams, with Classic Robocode 1.11.1. The bridge commit was `7486bba3b5d27cac986cea727bbea88296618875`. Runner 1.4.0 SHA-256 was `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`; Bot API 1.4.0 SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`. Bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`. The Tank Royale checkout was at `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`; the tested artifacts were built from unchanged production source at `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`.

The previous current-wrapper observation `208d6bf410614cc3` scored 20,567 in Classic and 28,763 in Tank Royale, a +39.9% single-pair difference. The five current paired deltas are +5.9%, +2.5%, +4.1%, +1.7%, and +7.5%. Mean Classic score is 20,201.2 and mean Tank Royale score is 21,079.2, a +4.34% mean. The repeated-score rule classifies the current result as `MATCHED (score noise)`; all five samples produced scores and the confirmation recorded no bridge-only exception signatures.

## Skipped-turn telemetry

The five Tank Royale samples recorded 2, 0, 6, 9, and 6 skipped-turn events respectively. Every captured event was on round 1, turn 1, and telemetry capture completed for every sample. The largest skip count coincided with the smallest score delta, while the no-skip sample still scored +2.5%; these records do not track the score differences and do not establish a cause.

## Finding

The earlier +39.9% result is not reproduced on the current matched artifacts. The five-pair mean is within the score-noise band, so the prior single-pair difference is not a confirmed score gap. The existing `nested-team-jar-discovery` diagnosis remains in the registry for the historical wrapper startup failure; the current retest adds no cause event and shows that failure surface no longer prevents score collection.

## M-006 handoff

Keep this subject at `MATCHED (score noise)` and retain its wrapper diagnosis history. No bridge or Tank Royale score cause is assigned, and no source change is indicated by this retest.

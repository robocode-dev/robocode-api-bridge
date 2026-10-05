---
id: AN-023
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-020]
title: BlindFighters' earlier score gap does not reproduce in five current official pairs
provenance: inferred
reversal-cost: low
---

# AN-023 — BlindFighters' earlier score gap does not reproduce in five current official pairs

## Question

Does the earlier `teamrumble/dummy_team.BlindFighters_1.01.jar` score discrepancy persist on the current bridge and matched Tank Royale artifacts?

## Evidence boundary

Five official teamrumble pairs used the read-only collection jar (SHA-256 `d3276ae8f7ed703a76a6c344cf7e744ee72305116d5f78b988d470de158d29ec`), classic Robocode 1.11.1, bridge commit `8bfc95f7824addf2c4e79c562cd6a3a710825083`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, and local 1.4.0 runner and Bot API artifacts. The field was 1200×1200 with 10 rounds; the harness captured skipped-turn telemetry on every repeat.

## Results

The paired score deltas were +10.2%, +14.9%, +12.6%, +7.7%, and +16.1%, for a +12.3% mean. Mean team scores were 19,545.2 on classic and 21,941.8 on Tank Royale. All five pairs completed without runtime errors. The registry records `MATCHED (score noise)` in observation `de36a467e13df0c0`, below the 25% sweep threshold.

Skipped-turn telemetry was captured for all repeats. Events occurred only on round 1, turn 1, in repeats 1, 2, and 4; repeats 3 and 5 recorded none. The variation does not identify a cause for the score differences.

The previous single-pair observation `95a261e1dcd6e54a` reported +61.0% using bridge `d64c2fbad098a8f001662f9aad542e4352e3cb63` and Tank Royale `0b2d2beb27387235c24a22f8d8fbd10bf88ef835`. That result is not pooled with the current five because its artifacts differ. The large single-pair difference is not reproduced on the current build.

## Finding

BlindFighters' current score review is matched within the project's noise band, with no asymmetric runtime error. This closes the current score review for this subject but does not explain the earlier cross-build score difference or establish deterministic parity.

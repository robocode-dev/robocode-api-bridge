---
id: AN-040
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CRIT-005, AN-002, AN-020]
title: ImWithDroidTeam's earlier score gap is not confirmed by five current pairs
provenance: inferred
reversal-cost: low
---

# AN-040 — ImWithDroidTeam's earlier score gap is not confirmed by five current pairs

## Question

Does the earlier `teamrumble/pedersen.ImWithDroidTeam_1.3.jar` score advantage persist on the current bridge and matched Tank Royale artifacts?

## Evidence boundary

The read-only team jar has SHA-256 `08590df195fdc1d850fcf718064c1338f9d4dc85fab3affe9908b19aa24aa33b`; its selected classic robot is `pedersen.ImWithStupid 1.3`. Registry observation `9c7e73ca883e1468` recorded a single +37.4% Tank Royale score advantage using Bot API 1.3.1. Earlier outcome observations used Bot API 1.2.0.

The current official confirmation is observation `0d4afa51ee3bbcbf`, measured with Classic Robocode 1.11.1, a 1200×1200 field, 10 rounds, and two teams. The bridge commit was `8ea5753c7b6f13e54e5c9687c5e8def259d42a66`; Runner and Bot API 1.4.0 SHA-256 values were `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` and `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`. The bridge API SHA-256 was `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, and wrapper SHA-256 was `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

## Result

The five paired score deltas were +15.0%, +6.0%, +2.2%, +4.5%, and +15.4%; mean Classic score was 19,913.8, mean Tank Royale score was 21,590.2, and mean pairwise delta was +8.62%. Both engines completed every sample without errors, and the confirmation recorded no bridge-only signatures. The registry classifies the subject as `MATCHED (score noise)`.

Tank Royale skipped-turn telemetry recorded 48, 30, 44, 32, and 40 events. They all occurred in round 1 across turns 1 through 109; their presence does not explain the difference between this five-pair mean and the earlier single-pair result.

The subject retains the historical `nested-team-jar-discovery` diagnosis owned by the wrapper from earlier outcome discrepancies. The current five-pair score confirmation found no new wrapper or bridge cause.

## Finding

The earlier +37.4% score advantage does not reproduce in five current official pairs. The current mean pairwise delta is +8.62%, below the 15-point five-pair confirmation band, and the earlier observation used a different Bot API version. The 25% threshold applies to single-pair review. No current score defect is located by this retest, and no code change is indicated.

## M-006 handoff

Keep `teamrumble/pedersen.ImWithDroidTeam_1.3.jar` at `MATCHED (score noise)` while retaining its historical observations and diagnosis. Continue in registry order with `teamrumble/pedersen.ImWithStupidTeam_1.3.jar`.

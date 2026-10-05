---
id: AN-041
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CRIT-005, AN-002, AN-020]
title: ImWithStupidTeam's earlier score gap is not confirmed by five current pairs
provenance: inferred
reversal-cost: low
---

# AN-041 — ImWithStupidTeam's earlier score gap is not confirmed by five current pairs

## Question

Does the earlier `teamrumble/pedersen.ImWithStupidTeam_1.3.jar` score advantage persist on the current bridge and matched Tank Royale artifacts?

## Evidence boundary

The read-only team jar has SHA-256 `214b6b55252fb3dd161f641833c48db0401f6b87277427fb4708b07880b7e317`; its selected classic robot is `pedersen.ImWithStupid 1.3`. Registry observation `6881105b0a1dac37` recorded a single +34.2% Tank Royale score advantage using Bot API 1.3.1. Earlier outcome observations used Bot API 1.2.0 and retain the historical `nested-team-jar-discovery` diagnosis owned by the wrapper.

The current official confirmation is observation `88173d72acfab9ca`, measured with Classic Robocode 1.11.1, a 1200×1200 field, 10 rounds, and two teams. The bridge commit was `efbdec50c5be3f6a64e664699e56cc48a0fc4d1b`; Runner and Bot API 1.4.0 SHA-256 values were `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` and `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`. The bridge API SHA-256 was `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, and wrapper SHA-256 was `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

## Result

The five paired score deltas were −3.9%, −7.4%, −1.1%, −6.5%, and −5.8%; mean Classic score was 23,016.2, mean Tank Royale score was 21,876.4, and mean pairwise delta was −4.94%. Both engines completed every sample without errors, and the confirmation recorded no bridge-only signatures. The registry classifies the subject as `MATCHED (score noise)`.

Tank Royale skipped-turn telemetry recorded 34, 26, 38, 37, and 41 events. All captured events occurred in round 1, across turns 1 through 74; they do not explain the earlier positive single-pair result.

## Finding

The earlier +34.2% score advantage does not reproduce in five current official pairs. The current mean pairwise delta is −4.94%, and the earlier observation used a different Bot API version. The historical wrapper diagnosis belongs to prior outcome discrepancies; this score retest finds no current bridge or wrapper cause, and no code change is indicated.

## M-006 handoff

Keep `teamrumble/pedersen.ImWithStupidTeam_1.3.jar` at `MATCHED (score noise)` while retaining its historical observations and diagnosis. Continue in registry order with `teamrumble/rz.AlephTeam_0.34.jar`.

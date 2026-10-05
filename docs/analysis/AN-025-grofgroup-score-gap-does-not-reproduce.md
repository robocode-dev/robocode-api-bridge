---
id: AN-025
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-002, AN-016]
title: GrofGroup's earlier higher score gap does not reproduce on current local artifacts
provenance: inferred
reversal-cost: low
---

# AN-025 — GrofGroup's earlier higher score gap does not reproduce on current local artifacts

## Question

Does the earlier `teamrumble/gh.nano.GrofGroup_1.1.jar` score discrepancy persist on the current bridge and matched local Tank Royale 1.4.0 runner and Bot API?

## Evidence boundary

The read-only collection jar has SHA-256 `84d7a929b3813c34df0176d25afac94d4e548dffc9a6edc5aaf8f1e8793659c5`. The five official pairs used classic Robocode 1.11.1, bridge commit `50328dbb942f069b449b03cf904a00f94d97b85d`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

Each pair used official teamrumble settings: a 1200×1200 field, 10 rounds, and two teams. The collection jar remained read-only, and skipped-turn telemetry was captured.

## Results

The paired score deltas were −10.3%, −6.7%, −6.8%, −7.5%, and −6.8%, for a −7.62% mean. Classic averaged 21,214.2 and Tank Royale 19,591.8. All five pairs completed without runtime errors. The current registry observation is `ae0e37bf27a31ebc`, classified `MATCHED (score noise)`.

The earlier single-pair observation `3784af636099c8b7` reported +29.8% using bridge `d64c2fbad098a8f001662f9aad542e4352e3cb63`, Tank Royale `0b2d2beb27387235c24a22f8d8fbd10bf88ef835`, and Bot API 1.2.0. That result is retained and not pooled with the current five because the bridge, runner, and Bot API artifacts differ.

Skipped-turn telemetry recorded 3, 10, 10, 4, and 2 events across the five repeats. Every recorded event was at round 1, turn 1. The varying counts do not identify a cause for the score differences.

## Finding

GrofGroup's earlier higher score result does not reproduce on the current matched local artifacts. The current five-pair score difference is within the project's ±25% review threshold, with no asymmetric runtime errors. The current case is `MATCHED (score noise)`; these measurements do not identify the cause of the earlier cross-build result or establish deterministic parity.

## M-006 handoff

Keep the five current observations as the score baseline for this artifact pair. Continue the team registry in order and use the same local Tank Royale runner and Bot API hashes for the next score reviews.

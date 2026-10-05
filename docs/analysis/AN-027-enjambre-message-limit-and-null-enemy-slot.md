---
id: AN-027
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-002, AN-016, AN-019]
title: Enjambre no longer hits the Tank Royale message limit but still exposes a classic robot null lookup
provenance: inferred
reversal-cost: low
---

# AN-027 — Enjambre no longer hits the Tank Royale message limit but still exposes a classic robot null lookup

## Question

Does the old `teamrumble/jab.Enjambre_3.jar` Tank Royale team-message failure persist after the message-batching repair, and what causes the remaining classic error?

## Evidence boundary

The read-only team jar has SHA-256 `ae0ce8fe2088fa2768b3c37dcc85fefd694e403cdde2b9e9bfeda87cdacf3780`. The current focused retest used classic Robocode 1.11.1, bridge commit `8dbc76a7dfd0b705ccbe2bbcc5c91eb745bafd19`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local 1.4.0 runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The team descriptor contains one `jab.Queen 3` and four `jab.Bee 3` members. Each pair used official teamrumble settings: a 1200×1200 field, 10 rounds, and two teams. The collection jar remained read-only.

## Results

The old observation `e3e38c2f6cbb6913` used bridge `d64c2fbad098a8f001662f9aad542e4352e3cb63`, Tank Royale `0b2d2beb27387235c24a22f8d8fbd10bf88ef835`, and Bot API 1.2.0. Classic reported a NullPointerException in `jab.module.Module.getEnemyAssignedNum`; Tank Royale stopped with `BotException: The maximum number team messages has already been reached: 10`, so it produced no score.

Two current official pairs completed the Tank Royale side without errors. Observation `8c312e24b019db24` scored 24,790 on classic and 21,246 on Tank Royale (−14.3%); classic reported one error. The repair-linked observation `93aa5d1940c52002` scored 23,548 and 20,672 (−12.2%); classic reported two errors. Both Tank Royale runs reported no runtime errors. The current registry status remains `DISCREPANCY (errors)` because the error outcomes differ, so these score deltas are not standalone score findings.

The linked retest records diagnosis `tank-royale-team-message-limit`, owner `tank-royale`, and bridge repair `68ed6be3ac3be160c302827217d51b4885383fde`. The bridge batches legacy team messages at turn submission, and the local 1.4.0 Bot API supports up to 64 message packets and 128 logical messages per turn. The previous ten-packet failure did not recur in either current pair.

The team source's `Module.getEnemyAssignedNum` loops over `enemyNumAssignation` and calls `.equals(enemyName)` without checking whether the slot is null. The array is sized to the expected enemy count; `assignNumToEnemy` fills slots only when it receives a non-teammate assignment, so an unfilled slot can trigger the observed exception. The classic stack reaches this method from `GuessFactorMelee.getTheMostVisitedTargetingBin` during round 1.

Tank Royale skipped-turn telemetry recorded 9 of 10 bot IDs at round 1, turn 1 in each current pair. This start-of-round signal does not establish why the independently seeded classic battle reached a null assignment slot while the Tank Royale battle did not.

## Finding

The old Tank Royale message-count error is not reproduced after the message-batching repair on the current local artifact pair. The remaining classic NullPointerException is robot-owned: Enjambre reads a null enemy-assignment slot without a guard. The paired outcome still differs, but the independent battle seeds and missing assignment/event timeline do not establish a bridge defect. The read-only team jar was not changed.

The registry retains both diagnoses: `tank-royale-team-message-limit` for the old API-side packet failure and `robot-unassigned-enemy-slot-null-lookup` for the current classic crash. No bridge correction follows from this batch.

## M-006 handoff

Keep the case as `DISCREPANCY (errors)` with both diagnosis events and the linked message-batching retest. Continue the team registry with the same local runner and Bot API artifacts; do not use the score deltas from error-mismatched pairs as a score-parity verdict.

---
id: AN-039
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-027]
title: Slippery exposes an oversized team-message batch; bounded flushing restores the official run
provenance: inferred
reversal-cost: low
---

# AN-039 — Slippery exposes an oversized team-message batch; bounded flushing restores the official run

## Question

Why did the current `teamrumble/ntc.slippery.Slippery_1.0.jar` produce a Tank Royale-only team-message exception, and does its earlier score gap persist after the cause is repaired on current artifacts?

## Evidence boundary

The read-only team jar has SHA-256 `8c8bb31fc63ee293ab00642593a7ffeec14f844891ad127b57c3b9421f2eef2a`; its descriptor names `Leader1`, `Leader2`, and three droids. Both leaders broadcast one predicted enemy position for each non-teammate scan; the bundled sources show no deliberate message-flood loop.

The first current-artifact observation `61c437063823c1a8` ran the official 1200×1200, 10-round, two-team setup on Classic Robocode 1.11.1. Classic completed without errors, but Tank Royale aborted before scoring with `BotException: The maximum number of logical team messages has already been reached: 128` from both `Leader1.onHitWall` and `Leader2.onHitWall`. The bridge commit was `ce7e5222d366bfd40fcd09501e9b97022529c692`; Runner 1.4.0 SHA-256 was `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, Bot API 1.4.0 SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, bridge API SHA-256 was `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5`, and wrapper SHA-256 was `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The cause inspection found that `BotPeer.flushTeamMessages()` removed the full queue and submitted it as one batch. Tank Royale 1.4.0 counts every batch entry toward its documented 128-logical-message per-turn limit, so a queue above that size threw while the bridge was flushing it. The bridge had not bounded each flush or retained a remainder for a later turn.

## Repair and retest

Commit `7e3a40ad774762e54fd1ecf2e0eba3cb4c812cd0` limits each flush to 128 queued entries and leaves later entries in order for following turns. The tracked diagnosis is `bridge-team-message-batch-exceeds-128-logical-payload-limit`, owner `bridge`.

The official five-pair retest is observation `aec07898ca594790`, completed on 2026-10-05. It used Classic Robocode 1.11.1 and the same 1200×1200, 10-round, two-team settings; the bridge API SHA-256 was `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, with the same Runner 1.4.0, Bot API 1.4.0, and wrapper artifacts listed above. The bridge source was committed before the run, so the registry manifest identifies the repair revision.

All five pairs completed without errors. Their score deltas were −4.8%, −8.5%, −11.5%, −7.3%, and −8.2%; mean Classic score was 19,922.6, mean Tank Royale score was 18,310.6, and the mean delta was −8.06%. The registry classifies the row as `MATCHED (score noise)` with no bridge-only signatures. Skipped-turn telemetry recorded 5, 4, 5, 4, and 0 events respectively, all at round 1, turn 1.

The previous score observation `d53817b4982c3ef4` was a single +28.8% Tank Royale advantage measured with Bot API 1.2.0. The five current pairs point in the other direction and remain inside the confirmation band, so that earlier score gap is not confirmed on the current artifacts.

## Finding

The asymmetric failure was bridge-owned: an unbounded bridge batch exceeded Tank Royale's per-turn logical-message limit and aborted the robot turn. Sending the first 128 entries and retaining the rest for later turns prevents that failure while preserving message order. The official retest completes on the repair and does not reproduce the earlier score gap; there is no unresolved Slippery bridge or Tank Royale discrepancy.

## M-006 handoff

Keep `teamrumble/ntc.slippery.Slippery_1.0.jar` at `MATCHED (score noise)` with the diagnosis and repair observation retained. Continue in registry order with `teamrumble/pedersen.ImWithDroidTeam_1.3.jar`.

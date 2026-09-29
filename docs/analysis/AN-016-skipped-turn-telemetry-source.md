---
id: AN-016
type: analysis
status: active
links: [P-001, CAP-007]
title: A bounded source for per-turn skipped-turn telemetry
provenance: inferred
reversal-cost: low
carried-by: [CAP-007, DES-007, CRIT-007]
---

# AN-016 — A bounded source for per-turn skipped-turn telemetry

## Risk investigated

Whether compatibility measurements can record the exact bot, round, and turn for skipped-turn events without mistaking unavailable telemetry for zero skips.

## Evidence boundary

This spike ran on Windows with PowerShell, JDK 25, classic Robocode 1.11.1, bridge worktree `ch-018-record-skipped-turn-telemetry` at `7e06e2a`, and Tank Royale worktree revision `21324d6b7c4433e3fc1ba2a0e29c691fc3565e47`. The Tank Royale worktree had unrelated GUI edits before the spike; no source files there were changed. The runner 1.4.0 fat JAR and Bot API 1.4.0 JAR were built from that local Tank Royale revision. This is prepared-environment evidence, not a clean-checkout or release-pair claim.

## What was tried

The classic Control API snapshot interface was inspected with `javap`. `IRobotSnapshot` exposes only `ACTIVE`, `HIT_WALL`, `HIT_ROBOT`, and `DEAD`; it does not expose skipped-turn events or a skipped-turn counter. Classic robots can report callbacks themselves, as the bridge-owned `SkippedTurnProbe` does, but that cannot establish zero skips for arbitrary silent robots.

Tank Royale's local runner source exposes opt-in `enableTurnTimingDiagnostics(30)`. The runner passes a diagnostics system property to the embedded server and redirects server output through JUL. The server records `skipped-turn-detected` with bot ID, round, and turn, then emits nearby timing records when the server stops.

A source-mode Java spike enabled that runner diagnostic and ran a temporary bot that slept 200 ms before each intent with a 1 ms turn timeout. The battle completed one round, and the JUL handler captured server diagnostics including exact bot/round/turn tuples. The emitted skip lines repeated tuples from overlapping diagnostic windows, and the server stores timing records in a bounded 24,000-record ring. This proves the local capture route works for a bounded forced-skip run; it does not establish that it retains every skip in a long battle.

The spike made no TPS or performance claim. An earlier filtered Gradle integration-test attempt pulled in unrelated .NET test dependencies and was stopped before the requested runner test began; that attempt is not counted as a test result.

## Findings

Bridge-side telemetry at `BotPeer`'s skipped-turn dispatch records the event at the boundary the compatibility layer receives, before the legacy robot's callback runs. It can be enabled only for measurement runs and preserves the exact bot ID, round, and skipped turn number. It buffers records in memory and flushes them at `GameEnded`, avoiding a file write on each skipped-turn callback. A final marker per bot reports the number of unique tuples so the harness can detect an interrupted flush. It avoids the runner's repeated timing windows and bounded timing buffer.

After implementation, three one-round local Tank Royale runs of the existing `SkippedTurnProbe` with two bridge bots reported 84, 82, and 76 unique event tuples. In each run, per-bot comparison matched every tuple's turn number to exactly one `SkippedTurnReported` callback line, and each completion marker's count matched its bot's tuples. A run without `--capture-skipped-turns` reported `disabled` with `events: null` and emitted no bridge markers, even when the parent environment supplied the telemetry property. These runs used the locally built bridge/wrapper with the Bot API and runner from the same Tank Royale 1.4.0 worktree revision; this is local conformance evidence, not a release or cross-machine performance claim.

The first end-to-end attempt also showed that `bot.getMyId()` is unavailable during `BotPeer` construction. The readiness marker now comes from `GameStartedEvent`, after the Bot API has assigned the ID; the forced-skip run passed after that correction.

This source measures skipped-turn events delivered to the bridge. It does not claim to observe a server-detected event that never reaches the bridge. Disabled capture, an incomplete battle, and a completed capture with no events must remain distinguishable. Historical registry observations stay unmeasured.

## Rejected interpretation

An empty bot log or missing server log line is not evidence of zero skips. The optional runner trace is useful for broader timing diagnosis, but its repeated windows and bounded ring are not the compatibility registry's per-event source.

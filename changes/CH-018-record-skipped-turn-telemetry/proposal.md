---
id: CH-018
type: change
status: open
links: [P-001, CAP-007, AN-016]
title: Record delivered skipped-turn events in compatibility measurements
---

# CH-018 — Record delivered skipped-turn events in compatibility measurements

## Problem

M-006 observations currently cannot show which Tank Royale turns a bridge bot reported as skipped. Missing data has been stored as `null`, so it cannot answer whether skips occurred during warm-up or during the measured workload.

## Change

Add opt-in telemetry at the bridge's `SkippedTurnEvent` dispatch boundary. When enabled for a compatibility run, each delivered event records the bot ID, round, and skipped turn number. Preserve every recorded turn, including warm-up turns. A completed enabled run with no events has an empty event list; disabled, unsupported, or incomplete capture has a separate status and no empty-list claim. Existing registry observations are not rewritten.

The measurement is about skipped-turn events received by the bridge. It does not claim to detect a server event that never reaches the bridge. This uses the current frozen `robocode.*` surface without changing any robot-visible method or event semantics.

## Plan and acceptance

This change serves `P-001/M-006` and adds the draft capability criterion `HARN-008` under `CAP-007`. `M-006` remains open; this change adds a separately tracked evidence door and does not claim parity-campaign completion.

## Challenge to the commitment

The assumption most likely to undermine this work is that the opt-in property reaches every wrapped bot process and that the marker is captured even when the legacy robot overrides `onSkippedTurn`. A credible alternative is to use the Tank Royale runner's server timing logs, which observe server detection independently of bot code, but the local spike found duplicated records from overlapping windows and a bounded diagnostic buffer. The cheapest useful test is a deliberately slow bridge bot run with capture enabled and disabled; compare each server-issued skipped event with exactly one recorded `(bot ID, round, turn)` tuple. Revise or stop if the enabled run misses a delivered event, the disabled run emits markers, or a completed no-skip run cannot be distinguished from unavailable capture.

An implementation could satisfy a parser test and still fail the person using the measurement by enabling diagnostics implicitly in ordinary parity battles, perturbing their timing, or reporting an empty list when bot logs were not collected. The opt-in flag, explicit status, and end-to-end probe guard against those failures.

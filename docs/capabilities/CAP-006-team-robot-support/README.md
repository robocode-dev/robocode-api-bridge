---
id: CAP-006
type: capability
status: draft
links: [G-001, ARCH-001, AN-013, ADR-002, IDR-008]
goal: G-001
title: Team robot support
provenance: inferred
reversal-cost: high
---

# CAP-006 — Team robot support

Robocode's team division runs teams rather than individual robots: a `.team` descriptor naming several robots that start together, message each other, and win or lose as a unit. Droids are part of the same feature — robots with no radar that depend entirely on teammates for targeting.

The wrapper produces a runnable Tank Royale bot directory per team member and a team boot entry the Tank Royale booter reads to group them, and `BotPeer` gives a droid team member the `Bot` subclass Tank Royale's droid detection actually keys on. Generated bot metadata carries each member's full class name, which Tank Royale uses with its battle-wide name map to restore classic team identities. The compatibility harness stages the grouped entry on both engines, and the team division is no longer recorded as skipped.

## Why it exists as its own capability

It is the only part of this corpus that is greenfield rather than repair, and the only one where the original failure was a clean absence rather than a subtle difference. The wrapper and harness now make the missing behavior observable in focused integration evidence instead of silently skipping it.

That makes it lower-risk than the score gaps and higher-effort than any of them, which is why `P-001` places it late.

## What it covers

The wrapper producing runnable Tank Royale bot directories from a team jar, grouped team staging on both engines, teammate messaging and directed-recipient isolation, classic name identity through the frozen team API, and droid semantics — a robot that receives no scan events of its own and acts on what its teammates tell it. `ADR-002` keeps Tank Royale's native team model underneath and uses its authoritative name map for the classic API.

## What it does not cover

The TwinDuel division. It is a team division with its own official parameters and no collection directory alongside the other three, so it is out of scope until there is something to run.

## The architectural pressure this creates

`ARCH-001` describes a wrapper that turns one jar into one bot directory. A team jar is several robots plus a descriptor, so that assumption is what has to give — this capability is the first real test of the wrapper's shape rather than an addition to it.

The interesting question was where a team becomes several Tank Royale bots. `AN-013` found Tank Royale's own team model corresponds closely to classic's at every point checked, and `ADR-002` records the resulting choice: map onto it directly (one Tank Royale bot process per member, grouped by the booter's own team mechanism) rather than reconstructing team behaviour above individual bots.

## Status

`draft`. `TEAM-001`, `TEAM-002`, and `TEAM-003` have passing two-engine integration evidence, including classic-name mapping, message delivery, recipient isolation, and droid behavior. The bridge now defaults to the published Tank Royale 1.4.0 Bot API, and `:conformance-test:test --tests '*TeamSupportConformanceTest'` passed against its published Bot API and runner on 2026-10-01 without skips. Capability activation still needs the repo-owned executable evidence export and verification of the high-cost inferred `ARCH-001`, `CAP-006`, and `CRIT-006` artifacts required by `clue validate`. `M-005` is complete; the full team collection remains available for the later `M-006` sweep.

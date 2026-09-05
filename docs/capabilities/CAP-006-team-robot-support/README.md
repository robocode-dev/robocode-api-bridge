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

The wrapper produces a runnable Tank Royale bot directory per team member and a team boot entry the Tank Royale booter reads to group them, and `BotPeer` gives a droid team member the `Bot` subclass Tank Royale's droid detection actually keys on. The compatibility harness now stages the grouped entry on both engines, and the team division is no longer recorded as skipped.

## Why it exists as its own capability

It is the only part of this corpus that is greenfield rather than repair, and the only one where the original failure was a clean absence rather than a subtle difference. The wrapper and harness now make the missing behavior observable in focused integration evidence instead of silently skipping it.

That makes it lower-risk than the score gaps and higher-effort than any of them, which is why `P-001` places it late.

## What it covers

The wrapper producing runnable Tank Royale bot directories from a team jar, grouped team staging on both engines, teammate messaging and directed-recipient isolation, and droid semantics — a robot that receives no scan events of its own and acts on what its teammates tell it. The bridge keeps Tank Royale's numeric sender and teammate-id representation because the protocol exposes no classic robot-name mapping; `ADR-002` records that boundary.

## What it does not cover

The TwinDuel division. It is a team division with its own official parameters and no collection directory alongside the other three, so it is out of scope until there is something to run.

## The architectural pressure this creates

`ARCH-001` describes a wrapper that turns one jar into one bot directory. A team jar is several robots plus a descriptor, so that assumption is what has to give — this capability is the first real test of the wrapper's shape rather than an addition to it.

The interesting question was where a team becomes several Tank Royale bots. `AN-013` found Tank Royale's own team model corresponds closely to classic's at every point checked, and `ADR-002` records the resulting choice: map onto it directly (one Tank Royale bot process per member, grouped by the booter's own team mechanism) rather than reconstructing team behaviour above individual bots.

## Status

`draft`. `CH-011` adds team-aware staging and purpose-written two-engine evidence. `TEAM-001` and `TEAM-003` are active with passing integration evidence; `TEAM-002` remains `@draft` because the bridge can prove message delivery and directed-recipient isolation, but not literal classic sender names across the two engines under the protocol boundary recorded in `ADR-002`. `M-005` is complete; the full team collection remains available for the later `M-006` sweep.

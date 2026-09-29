---
id: ADR-002
type: decision
status: verified
author: agent
accepted-by: [Flemming N. Larsen]
links: [CAP-006, ARCH-001, AN-013]
title: Preserve Tank Royale team grouping and expose classic team names
---

# ADR-002 — Preserve Tank Royale team grouping and expose classic team names

## Decision

The bridge continues to map classic teams onto Tank Royale's native team model: one bot process per member, grouped by the booter, with membership, droid behavior, messaging transport, and team scoring owned by the server.

The frozen `robocode.*` team API exposes classic names, not Tank Royale numeric IDs. `getName()`, `getTeammates()`, `isTeammate()`, `sendMessage()`, and `MessageEvent.getSender()` therefore use the classic full robot name, optional version, and the classic ` (n)` suffix when names repeat in the battle. Numeric IDs remain internal transport identifiers.

## Context

`AN-013` checked whether Tank Royale's team model corresponds closely enough to classic's to translate onto directly, or whether the bridge needed a second implementation of team semantics above individual bots. Tank Royale's Bot API carries the same shape at every point classic does: per-bot team id and name sent at handshake, server-mediated team membership and messaging, a `Droid` marker with the identical "+20 energy, no scanner" contract classic's own `Droid.java` states, and server-side results that already distinguish team-level from bot-level scoring (`ResultsForObserver.isTeam`).

Flemming N. Larsen explicitly chose classic-name restoration on 2026-09-27 while resolving CH-017 OQ-008-1. Merged [Tank Royale PR 277](https://github.com/robocode-dev/tank-royale/pull/277) adds the full team-member name to bot metadata and sends each bot its own name and teammate names at game start. The server assigns duplicate suffixes across the battle, so the bridge can use authoritative names instead of deriving suffixes from a team-local view.

## Why this way

The bridge should translate the server-owned team model rather than create a parallel implementation. Name restoration changes only the frozen API's identity view; it does not replace server-owned membership or message routing.

## Consequences

`robots-wrapper` gained a second production path: a team jar's `.team` descriptor drives production of one bot directory per member plus a team boot-entry directory naming them, rather than one bot directory per jar. Each generated bot metadata file also carries the full Java class name as `teamMemberName`, which lets the Tank Royale server build the classic identity map.

The bridge maps Tank Royale IDs to those authoritative names for `getName()`, `getTeammates()`, `isTeammate()`, directed `sendMessage()`, and `MessageEvent.getSender()`. The name-map API is optional at runtime so the bridge can still compile and run with earlier Bot API versions; those versions retain the prior numeric-ID fallback. The full-name behavior is verified with the matched local Tank Royale 1.4.0 Bot API and runner, including battle-wide duplicate suffixes and directed-recipient isolation (`TEAM-002`).

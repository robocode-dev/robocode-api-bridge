---
id: ADR-002
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-006, ARCH-001, AN-013]
title: Map team robots onto Tank Royale's native team model rather than reconstructing team semantics
---

# ADR-002 — Map team robots onto Tank Royale's native team model rather than reconstructing team semantics

## Decision

The bridge maps a classic team onto Tank Royale's own team concept: each team member becomes its own Tank Royale bot process, grouped by the Tank Royale booter's own team mechanism (a `teamMembers` field on a boot entry, resolved to sibling directories and assigned a shared team id at launch), and membership, droid gating (no scanner, +20 energy), team messaging, and team-level scoring are all left to the server. The bridge does not reimplement any of that.

Team messaging keeps Tank Royale's own numeric bot-id addressing rather than translating it to classic's name-based addressing: no channel in the Bot API's wire protocol ever reveals a real classic name to running robot code (not even a scanned bot's own event), and this bridge already reports every scanned robot's name as its stringified Tank Royale id everywhere else, so team messaging is consistent with that rather than a special-cased exception.

## Context

`CAP-006`'s design was deliberately left thin on this point: whether Tank Royale's team model corresponds closely enough to classic's to translate onto directly, or whether the bridge needed a second implementation of team semantics above individual bots, had not been checked.

`AN-013` checked it. Tank Royale's Bot API carries the same shape at every point classic does: per-bot team id and name sent at handshake, server-mediated `getTeammateIds`/`isTeammate`/`broadcastTeamMessage`/`sendTeamMessage`, a `Droid` marker with the identical "+20 energy, no scanner" contract classic's own `Droid.java` states, and server-side results that already distinguish team-level from bot-level scoring (`ResultsForObserver.isTeam`).

## Why this way

This is the same reasoning `IDR-001` already applied to event dispatch: when the engine already owns a piece of game semantics, the bridge translates rather than reimplements, because a second implementation is more code and depends on the two engines' models agreeing forever, not just now. `AN-013` found no gap between the two models large enough to justify paying that cost here.

Numeric-id team addressing is not a gap left unclosed by choice; it is the only option the wire protocol supports. Classic's name-based addressing works because the battle holds a static, name-keyed registry of every robot before round one. Tank Royale gives a connected bot no equivalent — not `getTeammateIds()` (ids only), not `ScannedBotEvent` (no name field at all) — so a droid, which by definition never scans, has no way to learn a teammate's classic name from the protocol at all. Building a name-to-id table would only work for the subset of teammates a bot has scanned or already heard from, which is a narrower and more surprising contract than simply keeping the numeric-id addressing this bridge already uses for every other identity surface.

## Consequences

`robots-wrapper` gained a second production path: a team jar's `.team` descriptor drives production of one bot directory per member plus a team boot-entry directory naming them, rather than one bot directory per jar. `ARCH-001` already named this as the thing that had to give; this decision fixes how.

A real, ported team robot that hardcodes a teammate's classic name in `sendMessage("sample.MyFirstDroid", ...)` will not resolve that name under the bridge — it receives numeric ids from `getTeammates()` instead, and must address by those. This is a known, named fidelity gap rather than a silent one, carried by `TEAM-002`'s evidence rather than worked around here.

If a future Tank Royale release adds a way to learn another bot's declared name (not just its id), that is new evidence against the addressing half of this decision — it gets its own analysis and, if it changes this decision, a revision to this record.

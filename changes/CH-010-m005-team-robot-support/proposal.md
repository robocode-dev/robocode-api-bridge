---
id: CH-010
type: change
status: proposed
links: [P-001, M-005, CAP-006, TEAM-001, TEAM-002, TEAM-003, AN-013, ROUTE-009]
title: Produce runnable Tank Royale team bot directories
---

# CH-010 — Produce runnable Tank Royale team bot directories

## What

Give `robots-wrapper` a second production path: a team jar's `.team` descriptor (`team.members=`, a comma-separated list of member class names, duplicates allowed) drives production of one Tank Royale bot directory per member plus a team boot-entry directory naming them, so the Tank Royale booter groups them as one team at launch. Give `robocode-api`'s `BotPeer` droid detection: a member class implementing `robocode.Droid` gets a `Bot` subclass that also implements Tank Royale's `Droid` marker, since that is what the Bot API actually keys droid status on.

Team messaging keeps `ROUTE-009`'s existing numeric-id addressing unchanged — see "Why" below for why that is not a shortfall this change needs to close.

**Scope note (found during Implement, see `tasks.md`):** `TEAM-001`'s own criterion text requires "the team is no longer recorded as skipped," and un-skipping `compat-test`'s team division turns out to need real team-battle-staging plumbing in `compat_test.py`, not the one-line flip the original task list assumed. That is materially bigger and harder to verify than the rest of this change, so this change stops at the wrapper and peer groundwork; `TEAM-001`, `TEAM-002`, `TEAM-003` stay `@draft` and a follow-up change closes them with the harness plumbing and purpose-written conformance test robots.

## Why

`M-005` is next in `P-001`'s sequence: the last implementation milestone before the campaign's closing sweep (`M-006`), and the only greenfield one. `CAP-006`'s design was left deliberately thin on one point — whether Tank Royale's team model corresponds closely enough to classic's to map onto directly — and `AN-013` answered it: membership, droid semantics (`+20` energy, no scanner — Tank Royale's `Droid` doc comment matches classic's `Droid.java` word for word), messaging, and team-level scoring are all server-owned in both engines, the same shape at every point checked. `ADR-002` (recorded in this change's digest) closes that decision.

Classic addresses teammates by name (`sample.MyFirstLeader`); Tank Royale, by a per-battle numeric bot id, and exposes no channel — not even a scanned bot's own event — that ever reveals a real classic name to running robot code. This bridge already reports every scanned robot's `getName()` as its stringified Tank Royale id (`ScannedRobotEventMapper`, shipped and covered by existing capabilities, not new here). `TeamAndJuniorPeerRoutingTest` (`ROUTE-009`, landed under the already-`done` `M-007`) already tests `BotPeer.ITeamRobotPeer` against that same numeric-id addressing. This change keeps it: `TEAM-002` is proven against numeric-id addressing, consistent with how every other identity surface in this bridge already works, rather than inventing a team-specific exception the wire protocol cannot actually support (see `open-questions.md` for the investigation and correction that settled this).

Droids are the sharpest fidelity requirement (`TEAM-003`): a droid that receives scan events it should not gains information silently, wins more, and produces a battle in which nothing looks wrong to a score-based instrument. Both engines gate this identically (`Droid` marker, no scanner), so the risk is in the wrapper and peer wiring a droid's bot directory and connection correctly, not in a semantic gap between engines.

## Route

Full. This is greenfield capability code toward `TEAM-001`–`TEAM-003` (still `@draft`, closed by the follow-up above) and records `ADR-002` — a contract-shaping change, not a refactor of unchanged behaviour.

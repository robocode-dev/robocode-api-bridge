---
id: DES-006
type: design
status: draft
links: [CAP-006, ARCH-001, ARCH-002, AN-013, ADR-002]
title: Team robot support — design
provenance: verified
reversal-cost: high
---

# CAP-006 — design

`status: draft`: the mapping decision is made (`ADR-002`) and its groundwork is implemented, but no criterion has integration evidence yet — the team division still cannot run through a battle on either engine.

## What exists

`robots-wrapper`'s `Main.java` reads a jar's `.team` descriptor (`team.members=`, comma-separated, duplicates allowed) alongside its `.properties` entries, and emits a team boot-entry directory whose `<name>.json` carries `teamMembers`, naming each member's ordinary bot directory once per occurrence — the shape the Tank Royale booter's own `BootEntry`/`BotBooter` reads to group processes into a team and assign them `TEAM_ID`/`TEAM_NAME`/`TEAM_VERSION` at launch. `BotPeer.createBotImpl` constructs a `Bot` subclass that also implements Tank Royale's `Droid` marker when the wrapped robot implements `robocode.Droid`, since Tank Royale's own droid detection (`WebSocketHandler`) keys on that marker rather than any bot-info field.

Not yet wired: `compat-test`'s battle-staging has no team-roster path on either engine, so nothing runs these directories through an actual battle, and the team division still reports as skipped.

## The mapping decision

A Robocode team is several robots that start together, address each other by name, and are scored as a unit. `AN-013` checked how closely Tank Royale's own team concept corresponds: membership, droid semantics (`+20` energy, no scanner — identical wording to classic's own `Droid.java`), messaging, and team-level scoring are all server-owned in both engines, the same shape at every point checked. `ADR-002` records the resulting choice: map onto Tank Royale's native team model (one Tank Royale bot process per member, grouped by the booter) rather than reconstructing team semantics above individual bots.

One difference survives the mapping and does not need reconstruction to handle: classic addresses teammates by name, Tank Royale by a per-battle numeric bot id, and no channel in the wire protocol ever reveals a real classic name to running robot code (confirmed while investigating `CH-010`'s `TEAM-002` groundwork — even a scanned bot's own event carries no name field). This bridge already reports every scanned robot's name as its stringified Tank Royale id everywhere else, so team messaging keeps that same numeric-id addressing (`ROUTE-009`) rather than inventing a team-specific exception the protocol cannot support.

## What is already known to be in the way

Droids are the sharpest fidelity requirement here. A droid has no radar and receives no scan events, and getting that wrong makes the robot *better*: it gains information it should not have, wins more, and produces a battle in which nothing looks wrong. `TEAM-003` exists because that failure is silent in the direction the score-based instrument is least likely to question.

Un-skipping the team division needs team-battle-staging plumbing in `compat-test/compat_test.py` on both engines (a team roster is not a flat participant list the way melee's is), which is the remaining work `M-005` needs before any `TEAM-00x` criterion can carry integration evidence.

## Evidence plan

Classic's own test suite is thinner on teams than on events and physics, so the conformance tier will need purpose-written test robots here rather than ported ones. The team collection provides the population once the wrapper can produce bot directories at all.

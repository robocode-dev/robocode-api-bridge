---
id: CH-010
type: change
status: proposed
links: [P-001, M-005, CAP-006, TEAM-001, TEAM-002, TEAM-003, AN-013, ROUTE-009]
title: Produce runnable Tank Royale team bot directories, and route team messaging by real classic names
---

# CH-010 — Produce runnable Tank Royale team bot directories, and route team messaging by real classic names

## What

Give `robots-wrapper` a second production path: a team jar's `.team` descriptor (`team.members=`, a comma-separated list of member class names, duplicates allowed) drives production of one Tank Royale bot directory per member, each carrying the `TEAM_ID`/`TEAM_NAME` environment the Bot API's `EnvVars` reads at connect time so the server groups them as one team. Add the per-battle name-to-id table `robocode-api`'s `BotPeer.ITeamRobotPeer` implementation needs to route `TeamRobot`'s name-addressed calls (`getTeammates`, `isTeammate(String)`, `sendMessage(String, ...)`) against Tank Royale's numeric ids, replacing the numeric-string-parsing placeholder `ROUTE-009` currently tests. Un-skip the team division in `compat-test`. Move `TEAM-001`, `TEAM-002`, `TEAM-003` out of `@draft` with integration evidence from purpose-written test robots (classic's own test suite has none to port here).

## Why

`M-005` is next in `P-001`'s sequence: the last implementation milestone before the campaign's closing sweep (`M-006`), and the only greenfield one. `CAP-006`'s design was left deliberately thin on one point — whether Tank Royale's team model corresponds closely enough to classic's to map onto directly — and `AN-013` answered it: membership, droid semantics (`+20` energy, no scanner — Tank Royale's `Droid` doc comment matches classic's `Droid.java` word for word), messaging, and team-level scoring are all server-owned in both engines, the same shape at every point checked. `ADR-002` (recorded in this change's digest) closes that decision.

One real difference survives the mapping: classic addresses teammates by name (`sample.MyFirstLeader`, with a `(2)`-style suffix when a `.team` file repeats a class — classic's own `MyFirstTeam.team` does this with four `MyFirstDroid` instances), Tank Royale by a per-battle numeric bot id. `BotPeer` already implements `ITeamRobotPeer` against that gap, but only provisionally: `TeamAndJuniorPeerRoutingTest` (`ROUTE-009`, landed under the already-`done` `M-007`) documents that today a "name" is parsed as a raw integer string, because "the wrapper cannot yet produce team bot directories" for the peer to be exercised against real ones. A real team robot calling `sendMessage("sample.DroidBot", ...)` would throw under that placeholder. This change is what `ROUTE-009`'s own comment says is coming.

Droids are the sharpest fidelity requirement (`TEAM-003`): a droid that receives scan events it should not gains information silently, wins more, and produces a battle in which nothing looks wrong to a score-based instrument. Both engines gate this identically (`Droid` marker, no scanner), so the risk is in the wrapper wiring a droid's bot directory correctly, not in a semantic gap between engines.

## Route

Full. This closes acceptance criteria currently `@draft` (`TEAM-001`, `TEAM-002`, `TEAM-003`), changes `BotPeer`'s team-message routing behaviour that `ROUTE-009` already covers, and records `ADR-002` — all contract changes, not refactors of unchanged behaviour.

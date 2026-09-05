---
id: DES-006
type: design
status: active
links: [CAP-006, ARCH-001, ARCH-002, AN-013, ADR-002, IDR-008]
title: Team robot support — design
provenance: verified
reversal-cost: high
---

# CAP-006 — design

`status: active`: the mapping decision is implemented and the team division now runs through both engines. `TEAM-002` remains draft because literal classic sender-name parity is not observable through the Tank Royale protocol; the delivery and recipient-isolation behavior is still exercised by the focused probe.

## What exists

`robots-wrapper`'s `Main.java` reads a jar's `.team` descriptor (`team.members=`, comma-separated, duplicates allowed) alongside its `.properties` entries, and emits a team boot-entry directory whose `<name>.json` carries `teamMembers`, naming each member's independent bot directory once per occurrence — the shape the Tank Royale booter's own `BootEntry`/`BotBooter` reads to group processes into a team and assign them `TEAM_ID`/`TEAM_NAME`/`TEAM_VERSION` at launch. Repeated occurrences are copied under unique generated names so the booter never starts two processes against one script or log file. `BotPeer.createBotImpl` constructs a `Bot` subclass that also implements Tank Royale's `Droid` marker when the wrapped robot implements `robocode.Droid`, since Tank Royale's own droid detection (`WebSocketHandler`) keys on that marker rather than any bot-info field.

`compat-test` marks `teamrumble` as a grouped division. Classic receives the selected team jar through its normal repository expansion. Tank Royale receives one generated team entry per team instance; the harness duplicates each member directory under an independent name, rewrites the copied entry's `teamMembers`, patches every member boot script, and raises the runner's participant ceiling to the expanded member count. Log collection expands the team entry back to its member directories so errors and console evidence remain attributable to individual processes.

`BotPeer` dispatches Tank Royale `TeamMessageEvent` values through `MessageEventMapper` to the frozen `TeamRobot` callback, so both callback delivery and the existing polling surface expose the same classic `MessageEvent` shape. Simple messages use the Bot API directly; other `Serializable` messages travel in a bridge-owned Java-serialization envelope because the Bot API's Gson cannot reflect into some JDK-owned classes under strong module encapsulation. The conformance harness packages a bridge-owned team fixture against classic's API, runs it on each engine, and returns per-member consoles for one shared expectation.

## The mapping decision

A Robocode team is several robots that start together, address each other by name, and are scored as a unit. `AN-013` checked how closely Tank Royale's own team concept corresponds: membership, droid semantics (`+20` energy, no scanner — identical wording to classic's own `Droid.java`), messaging, and team-level scoring are all server-owned in both engines, the same shape at every point checked. `ADR-002` records the resulting choice: map onto Tank Royale's native team model (one Tank Royale bot process per member, grouped by the booter) rather than reconstructing team semantics above individual bots.

One difference survives the mapping and does not need reconstruction to handle: classic addresses teammates by name, Tank Royale by a per-battle numeric bot id, and no channel in the wire protocol ever reveals a real classic name to running robot code (confirmed while investigating `CH-010`'s `TEAM-002` groundwork — even a scanned bot's own event carries no name field). This bridge already reports every scanned robot's name as its stringified Tank Royale id everywhere else, so team messaging keeps that same numeric-id addressing (`ROUTE-009`) rather than inventing a team-specific exception the protocol cannot support.

## What is already known to be in the way

Droids are the sharpest fidelity requirement here. A droid has no radar and receives no scan events, and getting that wrong makes the robot *better*: it gains information it should not have, wins more, and produces a battle in which nothing looks wrong. `TEAM-003` checks the negative scan case and the positive teammate-information case on both engines.

Classic name-based teammate addressing remains the known fidelity boundary. The bridge follows `ADR-002` and passes Tank Royale's numeric teammate ids through the frozen string-based methods; a future protocol-supported name mapping would need a separate change and evidence decision.

## Evidence plan

Classic's own test suite is thinner on teams than on events and physics, so the conformance tier uses purpose-written test robots. `TeamSupportConformanceTest` runs the same roster and expectation on both engines: six member processes across two team entries, broadcast/direct message markers with sender data, and a droid scan-negative marker. The team collection provides the population for `M-006`; `CH-011` does not rewrite its read-only jars.

---
id: DES-006
type: design
status: active
links: [CAP-006, ARCH-001, ARCH-002, AN-013, ADR-002, IDR-008]
title: Team robot support — design
provenance: verified
---

# CAP-006 — design

Team behavior runs through both engines, including classic-name parity when paired with the Tank Royale Bot API and runner that carry the name map. `TEAM-002` is active with two-engine evidence; the overall capability remains draft until the supported default Bot API version includes the map.

## What exists

`robots-wrapper`'s `Main.java` reads a jar's `.team` descriptor (`team.members=`, comma-separated, duplicates allowed) alongside its `.properties` entries. When a classic team archive stores member robots in nested jars, the wrapper stages each member jar beside the generated bot directories before resolving its properties. It emits a team boot-entry directory whose `<name>.json` carries `teamMembers`, naming each member's independent bot directory once per occurrence — the shape the Tank Royale booter's own `BootEntry`/`BotBooter` reads to group processes into a team and assign them `TEAM_ID`/`TEAM_NAME`/`TEAM_VERSION` at launch. Repeated occurrences are copied under unique generated names so the booter never starts two processes against one script or log file. `BotPeer.createBotImpl` constructs a `Bot` subclass that also implements Tank Royale's `Droid` marker when the wrapped robot implements `robocode.Droid`, since Tank Royale's own droid detection (`WebSocketHandler`) keys on that marker rather than any bot-info field.

`compat-test` marks `teamrumble` as a grouped division. Classic receives the selected team jar through its normal repository expansion. Tank Royale receives one generated team entry per team instance; the harness duplicates each member directory under an independent name, rewrites the copied entry's `teamMembers`, patches every member boot script, and raises the runner's participant ceiling to the expanded member count. Log collection expands the team entry back to its member directories so errors and console evidence remain attributable to individual processes.

`BotPeer` dispatches Tank Royale `TeamMessageEvent` values through `MessageEventMapper` to the frozen `TeamRobot` callback, so both callback delivery and the existing polling surface expose the same classic `MessageEvent` shape. Simple messages use the Bot API directly; other `Serializable` messages travel in a bridge-owned Java-serialization envelope because the Bot API's Gson cannot reflect into some JDK-owned classes under strong module encapsulation. The conformance harness packages a bridge-owned team fixture against classic's API, runs it on each engine, and returns per-member consoles for one shared expectation.

## The mapping decision

A Robocode team is several robots that start together, address each other by name, and are scored as a unit. `AN-013` checked how closely Tank Royale's own team concept corresponds: membership, droid semantics (`+20` energy, no scanner — identical wording to classic's own `Droid.java`), messaging, and team-level scoring are all server-owned in both engines, the same shape at every point checked. `ADR-002` records the resulting choice: map onto Tank Royale's native team model (one Tank Royale bot process per member, grouped by the booter) rather than reconstructing team semantics above individual bots.

Classic team methods address robots by name, while Tank Royale routes by numeric bot IDs. `ADR-002` requires the bridge to restore full classic names, optional versions, and duplicate-instance suffixes without replacing Tank Royale's native team grouping. The wrapper supplies the member class name in `teamMemberName`; Tank Royale's server supplies the battle-wide mapping through the Bot API, including the duplicate suffix. The bridge uses it for its own name, teammate names, name-based checks and directed sends, and message-event senders.

## What is already known to be in the way

Droids are the sharpest fidelity requirement here. A droid has no radar and receives no scan events, and getting that wrong makes the robot *better*: it gains information it should not have, wins more, and produces a battle in which nothing looks wrong. `TEAM-003` checks the negative scan case and the positive teammate-information case on both engines.

The bridge discovers the newer `getBotName(int)` API reflectively so it remains source-compatible with the older default dependency. When that method is absent, the bridge retains its numeric-ID fallback. Full name parity therefore requires a Bot API and runner built from the Tank Royale change that adds the authoritative map; `TEAM-002` was verified against that matched local 1.4.0 pair.

## Evidence plan

Classic's own test suite is thinner on teams than on events and physics, so the conformance tier uses purpose-written test robots. `TeamSupportConformanceTest` runs the same roster and expectation on both engines: six member processes across two team entries, broadcast/direct message markers with sender data, and a droid scan-negative marker. The team collection provides the population for `M-006`; `CH-011` does not rewrite its read-only jars.
